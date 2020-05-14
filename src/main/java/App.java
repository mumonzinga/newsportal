import com.google.gson.Gson;
import exceptions.ApiException;
import models.Department;
import models.News;
import models.Users;
import models.dao.Sql2oDepartmentDao;
import models.dao.Sql2oNewsDao;
import models.dao.Sql2oUsersDao;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        Sql2oUsersDao usersDao;
        Sql2oDepartmentDao departmentDao;
        Sql2oNewsDao newsDao;
        Connection conn;
        Gson gson = new Gson();

        staticFileLocation("/public");
        String connectionString = "jdbc:postgresql://localhost:5432/newsportal";
        Sql2o sql2o = new Sql2o(connectionString, "mumo", "kyalelove");

        departmentDao = new Sql2oDepartmentDao(sql2o);
        usersDao = new Sql2oUsersDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        conn = sql2o.open();

        notFound((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Custom 404\"}";
        });


        //CREATE
        post("/departments/:departmentId/users/usersId", "application/json", (req, res) -> {
            res.type("application/json");

            int departmentId = Integer.parseInt(req.params("departmentId"));
            int usersId = Integer.parseInt(req.params("usersId"));
            Department department = departmentDao.findById(departmentId);
            Users users = usersDao.findById(usersId);


            if (department != null && users != null) {
                //both exist and can be associated
                usersDao.addUsersToDepartment(users, department);
                res.status(201);
                return gson.toJson(String.format("Department '%s' and Users '%s' have been associated", users.getName(), department.getName()));
            } else {
                throw new ApiException(404, String.format("Department or Users does not exist"));
            }

        });

        get("/departments/:id/users", "application/json", (req, res) -> {
            res.type("application/json");
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            else if (departmentDao.getAllUsersByDepartment(departmentId).size()==0){
                return "{\"message\":\"I'm sorry, but no users are listed for this department.\"}";
            }
            else {
                return gson.toJson(departmentDao.getAllUsersByDepartment(departmentId));
            }
        });

        get("/users/:id/departments", "application/json", (req, res) -> {
            res.type("application/json");
            int usersId = Integer.parseInt(req.params("id"));
            Users usersToFind = usersDao.findById(usersId);
            if (usersToFind == null){
                throw new ApiException(404, String.format("No users with the id: \"%s\" exists", req.params("id")));
            }
            else if (usersDao.getAllDepartmentsForAUsers(usersId).size()==0){
                return "{\"message\":\"I'm sorry, but no departments are listed for this users.\"}";
            }
            else {
                return gson.toJson(usersDao.getAllDepartmentsForAUsers(usersId));
            }
        });


        post("/departments/:departmentId/news/new", "application/json", (req, res) -> {
            res.type("application/json");
            int departmentId = Integer.parseInt(req.params("departmentId"));
            News news = gson.fromJson(req.body(), News.class);
            news.setCreatedat(); //I am new!
            news.setFormattedCreatedAt();
            news.setDepartmentId(departmentId); //we need to set this separately because it comes from our route, not our JSON input.
            newsDao.add(news);
            res.status(201);
            return gson.toJson(news);
        });

        post("/users/new", "application/json", (req, res) -> {

            Users users = gson.fromJson(req.body(), Users.class);
            usersDao.add(users);
            res.status(201);
            return  gson.toJson(users);
        });

        //READ
        get("/departments", "application/json", (req, res) -> {
            res.type("application/json");
            System.out.println(departmentDao.getAll());

            if(departmentDao.getAll().size() > 0){
                return gson.toJson(departmentDao.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no departments are currently listed in the database.\"}";
            }

        });

        get("/departments/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(departmentToFind);
        });

        get("/departments/:id/news", "application/json", (req, res) -> {
            res.type("application/json");
            int departmentId = Integer.parseInt(req.params("id"));

            Department departmentToFind = departmentDao.findById(departmentId);
            List<News> allNews;

            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }

            allNews = newsDao.getAllNewsByDepartment(departmentId);

            return gson.toJson(allNews);
        });

        get("/departments/:id/sortedNews", "application/json", (req, res) -> { //// TODO: 1/18/18 generalize this route so that it can be used to return either sorted news or unsorted ones.
            res.type("application/json");
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            List<News> allNews;
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            allNews = newsDao.getAllNewsByDepartmentSortedNewestToOldest(departmentId);
            return gson.toJson(allNews);
        });

        get("/users", "application/json", (req, res) -> {
            res.type("application/json");

            return gson.toJson(usersDao.getAll());
        });


        //CREATE
        post("/departments/new", "application/json", (req, res) -> {
            res.type("application/json");

            Department department = gson.fromJson(req.body(), Department.class);
            departmentDao.add(department);
            res.status(201);
            return gson.toJson(department);
        });

        //FILTERS
        exception(ApiException.class, (exception, req, res) -> {
            ApiException err = exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });


        after((req, res) ->{
            res.type("application/json");
        });

    }
}
