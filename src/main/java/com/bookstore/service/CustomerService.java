package com.bookstore.service;

import com.bookstore.model.Customer;

import java.util.*;

public class CustomerService {
    private ArrayList<Customer> customers = new ArrayList<>();


    public void addCustomer(Customer c) {
        customers.add(c);
    }


    public Customer searchByName(String name) {
        for (Customer c : customers) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }


    public Customer searchById(int id) {
        for (Customer c : customers) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }


    public Customer searchByEmail(String email) {
        for (Customer c: customers) {
            if(c.getEmail().equals(email)) {
                return c;
            }
        }
        return null;
    }


    public Customer searchByPhone(String phone) {
        for (Customer c: customers) {
            if(c.getPhone().equals(phone)) {
                return c;
            }
        }
        return null;
    }


    public void updateCustomer(int id, String newEmail, String newPhone) {
        Customer c = searchById(id);
        if (c != null) {
            if (newEmail != null) c.setEmail(newEmail);
            if (newPhone != null) c.setPhone(newPhone);
            System.out.println("Updated successfully!");
        }
    }


    public void removeCustomer(int id) {
        customers.removeIf(c->c.getId() == id);
        System.out.println("Customer with ID: " + id + " is removed successfully!");
    }

    
    public void displayAll() {
        for (Customer c : customers) {
            System.out.println(c);
        }
    }
}