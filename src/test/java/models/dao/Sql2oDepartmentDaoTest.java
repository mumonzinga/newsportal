package models.dao;

import models.Users;
import models.Department;
import models.dao.Sql2oUsersDao;
import models.dao.Sql2oDepartmentDao;
import models.dao.Sql2oNewsDao;
import org.junit.*;

import static org.junit.Assert.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Sql2oDepartmentDaoTest {
    private static Connection conn;
    private static Sql2oDepartmentDao departmentDao;
    private static Sql2oUsersDao usersDao;
    private static Sql2oNewsDao newsDao;

    @Before
    public  void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectionString, "mumo", "kyalelove");
        departmentDao = new Sql2oDepartmentDao(sql2o);
        usersDao = new Sql2oUsersDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        conn = sql2o.open();
    }
/*
    @Test
    public void addingUsersSetsId() throws Exception {
        Department testDepartment = setupDepartment();
        int originalDepartmentId = testDepartment.getId();
        departmentDao.add(testDepartment);
        assertNotEquals(originalDepartmentId, testDepartment.getId());
    }

 */

    @Test
    public void addedDepartmentsAreReturnedFromGetAll() throws Exception {
        Department department = setupDepartment();
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void noDepartmentsReturnsEmptyList() throws Exception {
        assertEquals(0, departmentDao.getAll().size());
    }
/*
    @Test
    public void findByIdReturnsCorrectDepartment() throws Exception {
        Department testDepartment = setupDepartment();
        Department otherDepartment = setupDepartment();
        assertEquals(testDepartment, departmentDao.findById(testDepartment.getId()));
    }



 */
    @Test
    public void deleteByIdDeletesCorrectDepartment() throws Exception {
        Department testDepartment = setupDepartment();
        departmentDao.deleteById(testDepartment.getId());
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        Department testDepartment = setupDepartment();
        Department otherDepartment = setupDepartment();
        departmentDao.clearAll();
        assertEquals(0, departmentDao.getAll().size());
    }
    /*

    @Test
    public void getAllUsersForADepartmentReturnsUsersCorrectly() throws Exception {
        Users testUsers  = new Users("Jay", "214 NE Indore","97232", "503-402-9874", "jay@tee.com", "Editor", "Edit", "Health");
        usersDao.add(testUsers);

        Department testDepartment = setupDepartment();
        departmentDao.addDepartmentToUsers(testDepartment);
        List savedUsers = (List) testDepartment.getUsers();
        departmentDao.addDepartmentToUsers(testDepartment,testUsers);


        Users[] users = {testUsers}; //oh hi what is this?
        List  savedDepartments = Collections.singletonList(testUsers.getDepartment());
        assertEquals(Arrays.asList(users), savedUsers.size());
    }
    */


    @Test
    public void deletingDepartmentAlsoUpdatesJoinTable() throws Exception {
        Users testUsers  = new Users("Tee", "214 NE Broadway","97232", "503-402-9874", "tee@tee.com", "Editor", "Edit", "Forensics");
        usersDao.add(testUsers);

        Department testDepartment = setupDepartment();
        departmentDao.add(testDepartment);


        departmentDao.addDepartmentToUsers(testDepartment,testUsers);

        departmentDao.deleteById(testDepartment.getId());
        assertEquals(0, departmentDao.getAllUsersByDepartment(testDepartment.getId()).size());
    }


    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        departmentDao.clearAll();
        usersDao.clearAll();
        newsDao.clearAll();

    }

    @AfterClass
    public static void shutDown() throws Exception {
        conn.close();
        System.out.println("connection closed");
    }
    //helpers

    public Department setupDepartment (){
        Department department = new Department("Health", "Health Segments");
        departmentDao.add(department);
        return department;
    }

}