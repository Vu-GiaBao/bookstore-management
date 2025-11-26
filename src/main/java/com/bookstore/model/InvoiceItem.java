package com.bookstore.model;

public class InvoiceItem {
    private Book book;
    private int quantity;
    private double price;


    public InvoiceItem(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
        this.price = book.getPrice() * quantity;  
    }


    public Book getBook() {
        return book;
    }


    public int getQuantity() {
        return quantity;
    }


    public double getPrice() {
        return price;
    }

    public void displayInfo() {
        System.out.println("--- Invoice Item Information ---");
        System.out.println("Book: " + book);
        System.out.println("Stock: " + quantity);
        System.out.println("Price: $" + price);
    }


    public String toString() {
        return book.getTitle() + "x" + quantity + " --> $" + price;
    }
}
