package  models;

import java.util.Objects;

public class Users{
    private String name;
    private String address;
    private String zipcode;
    private String phone;
    private String email;
    private String  position;
    private String role;
    private String department;
    private int id;

    public Users(String name,String address, String zipcode, String phone, String email, String position, String role, String department) {
        this.name = name;
        this.address = address;
        this.zipcode = zipcode;
        this.phone = phone;
        this.email = email;
        this.position = position;
        this.role = role;
        this.department = department;


    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
    public String getPosition() {
        return position;
    }

    public String getRole() {
        return role;
    }

    public String getDepartment() {
        return department;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setEmail(String email) {
        this.email = email;
    }







    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users)) return false;
        Users that = (Users) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(zipcode, that.zipcode) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(email, that.email)&&
                Objects.equals(position, that.position) &&
                Objects.equals(role, that.role) &&
                Objects.equals(department, that.department)&&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, zipcode, phone, email, position, role, department, id);
    }
}
