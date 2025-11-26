package com.bookstore.service;

import com.bookstore.model.Book;
import com.bookstore.model.Invoice;
import com.bookstore.model.InvoiceItem;
import com.bookstore.model.InvoiceStatus;

import java.util.UUID;

public class InvoiceService {
    public Invoice createInvoice() {
        String randomId = UUID.randomUUID().toString();  
        return new Invoice(randomId);
    }


    public void addItem(Invoice invoice, Book book, int quantity) {
        if (invoice.getStatus() == InvoiceStatus.PAID) {
            System.out.println("Error! Invoice is already paid.");
            return;
        }
        if (quantity <= 0) {
            System.out.println("Error! Quantity must be greater than 0.");
            return;
        }
        InvoiceItem item = new InvoiceItem(book, quantity);
        invoice.getItems().add(item);
        System.out.println("Added: " + book.getTitle() + " x" + quantity);
    }


    public void removeItem(Invoice invoice, Book book) {
        if (invoice.getStatus() == InvoiceStatus.PAID) {
            System.out.println("Error! Invoice is already paid.");
            return;
        }
        for (InvoiceItem item: invoice.getItems()) {
            item.removeIf(item->item.getBook() == book);
            System.out.println("Removed successfully!");
        }    
    }

    public void updateItem(Invoice invoice, String bookId, int newQuantity) {
        if (invoice.getStatus() == InvoiceStatus.PAID) {
            System.out.println("Error! Invoice is already paid.");
            return;
        }
              
    }

    public double calculateTotal(Invoice invoice) {
        double total = 0;
        for (InvoiceItem item : invoice.getItems()) {
            total += item.getPrice();
        }
        invoice.setTotalAmount(total);
        return total;
    }


    public void paymentStatus(Invoice invoice) {
        if (invoice.getItems().isEmpty()) {
            System.out.println("Error! Invoice is empty.");
            return;
        }
        if (invoice.getStatus() == InvoiceStatus.PAID) {
            System.out.println("Error! Invoice has been paid before.");
            return;
        }
        invoice.setStatus(InvoiceStatus.PAID);
        System.out.println("Paid successfully!");
    }


    public void displayInvoice(Invoice invoice) {
        System.out.println(invoice);
    }
}
