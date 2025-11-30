package com.bookstore.model;

public class InvoiceItem {

    private Book book;
    private int quantity;

    public InvoiceItem(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
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
