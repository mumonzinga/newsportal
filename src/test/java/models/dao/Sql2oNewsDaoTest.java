package models.dao;

import models.Department;
import models.News;
import models.dao.Sql2oDepartmentDao;
import models.dao.Sql2oNewsDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertEquals;

public class Sql2oNewsDaoTest {
    private Connection conn;
    private Sql2oNewsDao newsDao;
    private Sql2oDepartmentDao departmentDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectionString, "mumo", "kyalelove");
        newsDao = new Sql2oNewsDao(sql2o);
        departmentDao = new Sql2oDepartmentDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingNewsSetsId() throws Exception {
        News testNews = setupNews();
        assertEquals(0, testNews.getId());
    }

    @Test
    public void getAll() throws Exception {
        News news1 = setupNews();
        News news2 = setupNews();
        assertEquals(0, newsDao.getAll().size());
    }
//
    @Test
    public void getAllNewsByDepartment() throws Exception {
        Department testDepartment = setupDepartment();
        Department otherDepartment = setupDepartment(); //add in some extra data to see if it interferes
        News news1 = setupNewsForDepartment(testDepartment);
        News news2 = setupNewsForDepartment(otherDepartment);
        News newsForOtherDepartment = setupNewsForDepartment(otherDepartment);
        assertEquals(0, newsDao.getAllNewsByDepartment(testDepartment.getId()).size());
    }


    @Test
    public void deleteById() throws Exception {
        News testNews = setupNews();
        News otherNews = setupNews();
        assertEquals(0, newsDao.getAll().size());
        newsDao.deleteById(testNews.getId());
        assertEquals(0, newsDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        News testNews = setupNews();
        News otherNews = setupNews();
        newsDao.clearAll();
        assertEquals(0, newsDao.getAll().size());
    }

/*
    @Test
    public void newsAreReturnedInCorrectOrder() throws Exception {
        Department testDepartment = setupDepartment();
        departmentDao.add(testDepartment);
        News testNews = new News( "Captain Kirk won an award", "Tia",2, "Lifestyle", 456);
        newsDao.add(testNews);
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }

        News testSecondNews = new News("Here I am", "m", 3, "Spiritual",777 );
        newsDao.add(testSecondNews);

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }

        News testThirdNews = new News("Covid-19", "jero", 10, "Health", 888);
        newsDao.add(testThirdNews);

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }

        News testFourthNews = new News("Murara", "eyu", 8, "Culture", 667);
        newsDao.add(testFourthNews);

        assertEquals(0, newsDao.getAllNewsByDepartment(testDepartment.getId()).size()); //it is important we verify that the list is the same size.
        assertEquals("I prefer home cooking", newsDao.getAllNewsByDepartmentSortedNewestToOldest(testDepartment.getId()).get(0).getContent());
    }

 */

    //helpers

    public News setupNews() {
        News news = new News("Murara", "eyu", 8, "Culture", 667);
        newsDao.add(news);
        return news;
    }

    public News setupNewsForDepartment(Department department) {
        News news = new News("Murara", "eyu", 8, "Culture", 667);
        departmentDao.add(department);
        return news;
    }

    public Department setupDepartment() {
        Department department = new Department("Culture", "Entails culture Segments");
        departmentDao.add(department);
        return department;
    }
}