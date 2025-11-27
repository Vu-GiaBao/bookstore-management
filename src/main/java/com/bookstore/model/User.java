package com.bookstore.model;

public class User {
    private String userid;
    private String username;
    private String password;
    private String role;  
    private int salary;


    public User(String userid, String username, String password, String role, int salary) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.role = role;
        this.salary = salary;
    }


    public String getUserid() { 
        return userid; 
    }

    public String getUsername() { 
        return username; 
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() { 
        return role; 
    }
    public void setRole(String role) {
        this.role = role;
    }

    public int getSalary() { 
        return salary; 
    }
    public void setSalary(int salary) {
        this.salary = salary;
    }


    public boolean login(String password) {
        return this.password.equals(password);
    }

    public void logout() {
        System.out.println(username + " has been logged out.");
    }


    public void changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
            System.out.println("New Password is updated!");
        } else {
            System.out.println("Incorrect old password. Try again!");
        }
    }

    public void updateRole(String newRole) {
        this.role = newRole;
        System.out.println("Role updated to: " + newRole);
    }


    public void displayInfo() {
        System.out.println("--- " + role + " Information ---");
        System.out.println("ID: " + userid);
        System.out.println("Name: " + username);
        System.out.println("Salary: $" + salary);
    }


    public String toString() {
        return userid + " | " + username + " | " + role + " | $" + salary;
    }
}