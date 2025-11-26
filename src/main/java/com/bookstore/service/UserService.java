package com.bookstore.service;

import com.bookstore.model.User;

import java.util.*;

public class UserService {
    private ArrayList<User> users = new ArrayList<>();


    public void addUser(User u) {
        users.add(u);
    }


    public User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.login(password)) {
                return u;   
            }
        }
        return null; 
    }


    public User searchByUserId(String userid) {
        for (User u : users) {
            if (u.getUserid().equals(userid)) {
                return u;
            }
        }
        return null;
    }


    public User searchByUserName(String username) {
        for (User u: users) {
            if (u.getUsername.equals(username)) {
                return u;
            }
        }
    }

    public void changeUsername(String userid, String password, String newUsername) {
        User u = searchByUserId(userid);
        if (u != null && u.login(password)) {
            u.setUsername(newUsername);
            System.out.println("Username updated successfully!");
        } else {
            System.out.println("Error! Username not found or Password incorrect. Try again!");
        }
    }


    public void changePassword(String userid, String oldPassword, String newPassword) {
        User u = searchByUserId(userid);
        if (u != null && u.login(oldPassword)) {
            u.setPassword(newPassword);
        } else {
            System.out.println("Error! Username not found or Password incorrect. Try again!");
        }
    }


    public void updateUser(String userid, String newRole, int newSalary) {
        User u = searchByUserId(userid);
        if (u != null) {
            if (newRole != null) u.setRole(newRole);
            if (newSalary > 0) u.setSalary(newSalary);
            System.out.println("Updated successfully!");
        }
    }


    public void removeUser(String userid) {
        users.removeI(u->u.getUserid() == userid);
        System.out.println("User with ID: " + userid + " is removed successfully");
    }

    public void displayUser() {
        for (User u: users) {
            System.out.println(u);
        }
    }

}