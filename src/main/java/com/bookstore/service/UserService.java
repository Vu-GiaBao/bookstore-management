package com.bookstore.service;

import com.bookstore.database.UserDAO;
import com.bookstore.model.User;
import java.util.List;

public class UserService {

    private UserDAO userDAO = new UserDAO();

     public List<User> getAllUsers() {
        return userDAO.getAll();
    }
    
    public List<User> getAll() {
        return userDAO.getAll();
    }

    public User getById(int id) {
        return userDAO.getById(id);
    }
}
