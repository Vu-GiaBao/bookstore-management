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
    private int customerId;
    private int userId;

    // Constructor dùng UUID
    public Invoice(String invoiceId) {
        this.invoiceId = invoiceId;
        this.date = LocalDate.now();
        this.items = new ArrayList<>();
        this.status = InvoiceStatus.PENDING;
    }

    // Full constructor (cho DAO)
    public Invoice(String invoiceId, LocalDate date, double totalAmount,
                   InvoiceStatus status, int customerId, int userId) {
        this.invoiceId = invoiceId;
        this.date = date;
        this.totalAmount = totalAmount;
        this.status = status;
        this.customerId = customerId;
        this.userId = userId;
        this.items = new ArrayList<>();
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }
}
