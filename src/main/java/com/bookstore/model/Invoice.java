package com.bookstore.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private String invoiceId;
    private LocalDate date;
    private List<InvoiceItem> items;
    private double totalAmount;
    private InvoiceStatus status; 


    public Invoice(String invoiceId) {
        this.invoiceId = invoiceId;
        this.date = LocalDate.now();
        this.items = new ArrayList<>();
        this.status = InvoiceStatus.PENDING;
    }
    
    
    public String getInvoiceId() {
        return invoiceId;
    }


    public LocalDate getDate() {
        return date;
    }


    public List<InvoiceItem> getItems() {
        return items;
    }


    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }


    public InvoiceStatus getStatus() {
        return status;
    }
    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    
    public void displayInfo() {
        System.out.println("--- Invoice Information ---");
        System.out.println("ID: " + invoiceId);
        System.out.println("Date: " + date);
        System.out.println("Total amount: " + totalAmount);
        System.out.println("Status: " + status);
    }

    public String toString() {
        return invoiceId + " | " + date + " | " + totalAmount + " | " + status;
    }
}