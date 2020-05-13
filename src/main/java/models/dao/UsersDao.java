package models.dao;

import models.Users;
import models.Department;

import java.util.List;

public interface UsersDao {
    //create
    void add(Users users);
    void addUsersToDepartment(Users users, Department department);

    //read
    List<Users> getAll();
    List<Department> getAllDepartmentsForAUsers(int id);
    Users findById(int id);

    //update
    void update(int id, String name, String address, String zipcode, String phone,String email, String position, String role, String department);


    //delete
    void deleteById(int id);
    void clearAll();
}