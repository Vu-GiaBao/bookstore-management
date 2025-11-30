package com.bookstore.model;

public class Customer extends Person {    
    private int id;
    private String address;

    public Customer(int id, String name, String email, String phone, String address) {
        super(name, email, phone);         
        this.id = id;
        this.address = address;
    }

    
    public int getId() { 
        return id; 
    }
    public void setId(int id) { 
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void displayInfo() {
        System.out.println("--- Customer Information ---");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Address " + address);
    }


    public String toString() {
        return id + " | " + name + " | " + email + "  | " + phone + " | " + address;
    }
}