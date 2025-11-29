package com.bookstore.model;

import javafx.beans.property.*;

public class Book {
    private int id;
    private String title;
    private String author;
    private double price;
    private int quantity;

    
    public Book(int id, String title, String author, double price, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }


    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }


    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
    public void displayInfo() {
        System.out.println("--- Book Information ---");
        System.out.println("ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Price: $" + price);
        System.out.println("Stock: " + quantity);
    }


    public String toString() {
        return id + " | " + title + " | " + author + " | $" + price + " | " + "Stock: " + quantity;
    }
}
