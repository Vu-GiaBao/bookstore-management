package com.bookstore.service;

import com.bookstore.database.UserDAO;
import com.bookstore.model.User;

import java.util.*;

public class UserService {
    private final UserDAO userDAO = new UserDAO();


   public void addUser(User u) {
        if (u == null) {
            System.out.println("Error! User is null.");
            return;
        }
        int rows = userDAO.insert(u);
        if (rows > 0) {
            System.out.println("Added successfully!");
        } else {
            System.out.println("Add failed!");
        } 
    }


    public User login(String username, String password) {
        if (username == null || username.isBlank() || password == null) {
            return null;
        }
        User u = userDAO.findByUsername(username);
        if (u != null && u.login(password)) {
            return u;
        }
        return null;
    }


    public User searchByUserId(String userid) {
        if (userid == null || userid.isBlank()) {
            return null;
        }
        return userDAO.findByID(userid); 
    }


    public User searchByUserName(String username) {
        if (username == null || username.isBlank()) {
            return null;
        }
        return userDAO.findByUsername(username); 
    }


    public void changeUsername(String userid, String password, String newUsername) {
        if (newUsername == null || newUsername.isBlank()) {
            System.out.println("Error! New username is empty.");
            return;
        }
        User u = userDAO.findByID(userid);
        if (u != null && u.login(password)) {
            u.setUsername(newUsername);
            int rows = userDAO.update(u); 
            if (rows > 0) {
                System.out.println("Username updated successfully!");
            } else {
                System.out.println("Username update failed!");
            }
        } else {
            System.out.println("Error! User not found or Password incorrect. Try again!");
        }
    }


    public void changePassword(String userid, String oldPassword, String newPassword) {
        if (newPassword == null || newPassword.isBlank()) {
            System.out.println("Error! New password is empty.");
            return;
        }
        User u = userDAO.findByID(userid);
        if (u != null && u.login(oldPassword)) {
            int rows = userDAO.updatePassword(userid, newPassword);
            if (rows > 0) {
                System.out.println("Password updated successfully!");
            } else {
                System.out.println("Password update failed!");
            }
        } else {
            System.out.println("Error! User not found or Password incorrect. Try again!");
        }
    }


    public void updateUser(String userid, String newRole, int newSalary) {
        User u = userDAO.findByID(userid);
        if (u == null) {
            System.out.println("User not found!");
            return;
        }
        if (newRole != null && !newRole.isBlank()) {
            u.setRole(newRole);
        }
        if (newSalary > 0) {
            u.setSalary(newSalary);
        }
        int rows = userDAO.update(u); 
        if (rows > 0) {
            System.out.println("Updated successfully!");
        } else {
            System.out.println("Update failed!");
        }         
    }


    public void removeUser(String userid) {
        int rows = userDAO.delete(userid); 
        if (rows > 0) {
            System.out.println("User with ID: " + userid + " is removed successfully");
        } else {
            System.out.println("Remove failed (user not found?)");
        }
    }


    public void displayUse() {
        ArrayList<Customer> list = userDAO.getAll();
        if (list == null || list.isEmpty()) {
            System.out.println("No user found!");
            return;
        }
        for (Customer c : list) {
            System.out.println(c);
        }
    }
}