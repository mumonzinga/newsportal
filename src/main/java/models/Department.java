package models;

import java.util.List;
import java.util.Objects;

public class Department {
    private String name;
    private String description;
    private Object users;
    private int id;

    public Department(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department department = (Department) o;
        return id == department.id &&
                Objects.equals(name, department.name)&&
                Objects.equals(description, department.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id);
    }


    public void saveUsers(Users testUsers) {
    }

    public  Object getUsers() {
        return users;
    }
}