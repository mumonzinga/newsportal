package models.dao;

import models.Users;
import models.Department;

import java.util.List;

public interface DepartmentDao {
    //create
    void add (Department department);
    void addDepartmentToUsers(Department department, Users users);

    //read
    List<Department> getAll();
    Department findById(int id);
    List<Users> getAllUsersByDepartment(int departmentId);

    //update


    //delete
    void deleteById(int id);
    void clearAll();
}

