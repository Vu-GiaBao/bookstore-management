package com.bookstore.model;

public class InvoiceItem {

    private Book book;
    private int quantity;
    private double price;

    public InvoiceItem(Book book, int quantity, double price) {
    this.book = book;
    this.quantity = quantity;
    this.price = price;
    }

     public InvoiceItem(Book book, int quantity) {
        this(book, quantity, book.getPrice());
    }

    public Book getBook() {
        return book;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubtotal() {
        return quantity * book.getPrice();
    }
}
