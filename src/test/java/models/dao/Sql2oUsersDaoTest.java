package models.dao;

import models.Users;
import models.Department;
import models.dao.Sql2oUsersDao;
import models.dao.Sql2oDepartmentDao;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import static org.junit.Assert.*;

public class Sql2oUsersDaoTest {
    private  Sql2oUsersDao usersDao;
    private  Sql2oDepartmentDao departmentDao;
    private  Connection conn;

    @BeforeClass
    public void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectionString, "mumo", "kyalelove");
        departmentDao = new Sql2oDepartmentDao(sql2o);
        usersDao = new Sql2oUsersDao(sql2o);
        conn = sql2o.open();
    }


    @After
    public void tearDown() throws Exception {
        conn.close();
    }
/*
    @Test
    public void addingUsersSetsId() throws Exception {
        Users testUsers = setupNewUsers();
        int originalUsersId = testUsers.getId();
        usersDao.add(testUsers);
        assertNotEquals(originalUsersId,testUsers.getId());
    }
*/
    @Test
    public void addedUsersAreReturnedFromGetAll() throws Exception {
        Users testusers = setupNewUsers();
        usersDao.add(testusers);
        assertEquals(0, usersDao.getAll().size());
    }

    @Test
    public void noUsersReturnsEmptyList() throws Exception {
        assertEquals(0, usersDao.getAll().size());
    }

    @Test
    public void deleteByIdDeletesCorrectUsers() throws Exception {
        Users users = setupNewUsers();
        usersDao.add(users);
        usersDao.deleteById(users.getId());
        assertEquals(0, usersDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        Users testUsers = setupNewUsers();
        Users otherUsers = setupNewUsers();
        usersDao.clearAll();
        assertEquals(0, usersDao.getAll().size());
    }

    @Test
    public void addUsersToDepartmentAddsTypeCorrectly() throws Exception {

        Department testDepartment = setupDepartment();

        departmentDao.add(testDepartment);

        Users testUsers = setupNewUsers();

        usersDao.add(testUsers);

        usersDao.addUsersToDepartment(testUsers, testDepartment);

        assertEquals(1, usersDao.getAllDepartmentsForAUsers(testUsers.getId()).size());
    }

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

    @Test
    public void deletingUsersAlsoUpdatesJoinTable() throws Exception {

        Department testDepartment = setupDepartment();

        departmentDao.add(testDepartment);

        Users testUsers = setupNewUsers();
        Users otherUsers = new Users("Jay", "214 NE Indore","97232", "503-402-9874", "jay@tee.com", "Editor", "Edit", "Health");

        usersDao.add(testUsers);
        usersDao.add(otherUsers);

        usersDao.addUsersToDepartment(testUsers, testDepartment);
        usersDao.addUsersToDepartment(otherUsers,testDepartment);

        usersDao.deleteById(testDepartment.getId());
        assertEquals(0, usersDao.getAllDepartmentsForAUsers(testUsers.getId()).size());
    }

   // @Test
    //public void updateCorrectlyUpdatesAllFields() throws Exception {
      //  Users testUsers = setupNewUsers();
        //usersDao.update(testUsers.getId(), "a", "b", "c", "d", "e", "f", "g", "h");
//        Users foundUsers = usersDao.findById(testUsers.getId());
  //      assertEquals("a", foundUsers.getName());
    //    assertEquals("b", foundUsers.getAddress());
 //       assertEquals("c", foundUsers.getZipcode());
 //       assertEquals("d", foundUsers.getPhone());
  //      assertEquals("e", foundUsers.getEmail());
  //      assertEquals("f", foundUsers.getPosition());
    //    assertEquals("g", foundUsers.getRole());
  //      assertEquals("h", foundUsers.getDepartment());

    //}

    // helpers

    public Users setupNewUsers(){
        return new Users("Jay", "214 NE Indore","97232", "503-402-9874", "jay@tee.com", "Editor", "Edit", "Health");
    }

    public Department setupDepartment (){
        return new Department("Health", "Developing Health Issues Segments");
    }



}
