package com.bookstore.model;

public class Customer extends Person {    
    private int id;


    public Customer(int id, String name, String email, String phone) {
        super(name, email, phone);         
        this.id = id;
    }

    
    public int getId() { 
        return id; 
    }
    public void setId(int id) { 
        this.id = id;
    }


    public void displayInfo() {
        System.out.println("--- Customer Information ---");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
    }


    public String toString() {
        return id + " | " + name + " | " + email + " | " + phone;
    }
}