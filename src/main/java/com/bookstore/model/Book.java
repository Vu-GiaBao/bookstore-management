package com.bookstore.model;

import javafx.beans.property.*;

public class Book {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty author = new SimpleStringProperty();
    private DoubleProperty price = new SimpleDoubleProperty();

    public Book(int id, String title, String author, double price) {
        this.id.set(id);
        this.title.set(title);
        this.author.set(author);
        this.price.set(price);
    }

    public IntegerProperty idProperty() { return id; }
    public StringProperty titleProperty() { return title; }
    public StringProperty authorProperty() { return author; }
    public DoubleProperty priceProperty() { return price; }

    public int getId() { return id.get(); }
    public String getTitle() { return title.get(); }
    public String getAuthor() { return author.get(); }
    public double getPrice() { return price.get(); }

    public void setTitle(String t) { title.set(t); }
    public void setAuthor(String a) { author.set(a); }
    public void setPrice(double p) { price.set(p); }
}
