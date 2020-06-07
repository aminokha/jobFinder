package com.example.jobfinder.Model;

public class Users {
    public String name;
    public String password;
    public String phone;
    public String inStaff;

    public Users() {
    }
    public Users(String name, String password, String inStaff) {
        this.name = name;
        this.password = password;
        this.inStaff = inStaff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInStaff() {
        return inStaff;
    }

    public void setInStaff(String inStaff) {
        this.inStaff = inStaff;
    }
}
