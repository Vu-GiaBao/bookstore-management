package com.bookstore.service;

import com.bookstore.database.CustomerDAO;
import com.bookstore.model.Customer;

import java.util.*;

public class CustomerService {
    private final CustomerDAO customerDAO = new CustomerDAO();


    public void addCustomer(Customer c) {
        if (c == null) {
            System.out.println("Error! Customer is null.");
            return;
        }
        int rows = customerDAO.insert(c);
        if (rows > 0) {
            System.out.println("Added successfully!");
        } else {
            System.out.println("Add failed!");
        } 
    }


    public Customer searchByName(String name) {
        Customer c = customerDAO.findByName(name); 
        if (c != null) {
            System.out.println("Customer with name: " + name + " is: " + c);
        } else {
            System.out.println("Customer not found!");
        }
        return c;
    }


    public Customer searchById(int id) {
        Customer c = customerDAO.findByID(id);
        if (c != null) {
            System.out.println("Customer with id: " + id + " is: " + c);
        } else {
            System.out.println("Cook not found!");
        }
        return b;
    }


    public Customer searchByEmail(String email) {
        Customer c = customerDAO.findByEmail(email); 

        if (c != null) {
            System.out.println("Customer with email: " + email + " is: " + c);
        } else {
            System.out.println("Customer not found!");
        }
        return c;
    }


    public Customer searchByPhone(String phone) {
        Customer c = customerDAO.findByPhone(phone);
        if (c != null) {
            System.out.println("Customer with phone: " + phone + " is: " + c);
        } else {
            System.out.println("Customer not found!");
        }

        return c;
    }


    public void updateCustomer(int id, String newEmail, String newPhone) {
        Customer c = customerDAO.findByID(id);
        if (c == null) {
            System.out.println("Customer not found!");
            return;
        }
        if (newEmail != null && !newEmail.isBlank()) {
            c.setEmail(newEmail);
        }
        if (newPhone != null && !newPhone.isBlank()) {
            c.setPhone(newPhone);
        }
        int rows = customerDAO.update(c);
        if (rows > 0) {
            System.out.println("Updated successfully!");
        } else {
            System.out.println("Update failed!");
        }
    }


    public void removeCustomer(int id) {
        int rows = customerDAO.delete(id);
        if (rows > 0) System.out.println("Customer with ID: " + id + " is removed successfully!");
        else System.out.println("Remove failed (customer not found?)");
    }

    
    public void displayCustomer() {
        ArrayList<Customer> list = customerDAO.getAll();
        if (list == null || list.isEmpty()) {
            System.out.println("No customer found!");
            return;
        }
        for (Customer c : list) {
            System.out.println(c);
        }
    }
}