package models.dao;

import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oNewsDao implements NewsDao{
    private final Sql2o sql2o;
    public Sql2oNewsDao(Sql2o sql2o) { this.sql2o = sql2o; }

    @Override
    public void add(News news) {
        String sql = "INSERT INTO news (writtenby, rating, content, department, departmentid, createdat) VALUES (:writtenBy, :rating, :content,:department, :departmentId, :createdat)"; //if you change your model, be sure to update here as well!
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(news)
                    .executeUpdate()
                    .getKey();
            news.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<News> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM news")
                    .executeAndFetch(News.class);
        }
    }

    @Override
    public List<News> getAllNewsByDepartment(int departmentId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM news WHERE departmentid = :departmentId")
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(News.class);
        }
    }

    @Override
    public List<News> getAllNewsByDepartmentSortedNewestToOldest(int departmentId) {
        List<News> unsortedNews = getAllNewsByDepartment(departmentId);
        List<News> sortedNews = new ArrayList<>();
        int i = 1;
        for (News news : unsortedNews){
            int comparisonResult;
            if (i == unsortedNews.size()) { //we need to do some funky business here to avoid an arrayindex exception and handle the last element properly
                if (news.compareTo(unsortedNews.get(i-1)) == -1){
                    sortedNews.add(0, unsortedNews.get(i-1));
                }
                break;
            }

            else {
                if (news.compareTo(unsortedNews.get(i)) == -1) { //first object was made earlier than second object
                    sortedNews.add(0, unsortedNews.get(i));
                    i++;
                } else if (news.compareTo(unsortedNews.get(i)) == 0) {//probably should have a tie breaker here as they are the same.
                    sortedNews.add(0, unsortedNews.get(i));
                    i++;
                } else {
                    sortedNews.add(0, unsortedNews.get(i)); //push the first object to the list as it is newer than the second object.
                    i++;
                }
            }
        }
        return sortedNews;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from news WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from news";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}