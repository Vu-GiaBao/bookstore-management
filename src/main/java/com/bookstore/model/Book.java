package com.bookstore.model;

import javafx.beans.property.*;

public class Book {
    private final IntegerProperty id;
    private final StringProperty title;
    private final StringProperty author;
    private final DoubleProperty price;
    private final IntegerProperty quantity;

    public Book(int id, String title, String author, double price, int quantity) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getAuthor() {
        return author.get();
    }

    public StringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public void displayInfo() {
        System.out.println("--- Book Information ---");
        System.out.println("ID: " + getId());
        System.out.println("Title: " + getTitle());
        System.out.println("Author: " + getAuthor());
        System.out.println("Price: $" + getPrice());
        System.out.println("Stock: " + getQuantity());
    }

    public String toString() {
        return getId() + " | " + getTitle() + " | " + getAuthor() + " | $" + getPrice() + " | " + "Stock: " + getQuantity();
    }
}
