package com.bookstore.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.bookstore.service.BookService;
import com.bookstore.service.CustomerService;
import com.bookstore.service.InvoiceService;

public class DashboardController {

    @FXML
    private Label lblBooks;

    @FXML
    private Label lblInvoices;

    @FXML
    private Label lblCustomers;

    private BookService bookService = new BookService();
    private InvoiceService invoiceService = new InvoiceService();
    private CustomerService customerService = new CustomerService();

    @FXML
    public void initialize() {
        loadDashboardCounts();
    }

    private void loadDashboardCounts() {
        int totalBooks = bookService.getTotalBooks();
        int totalInvoices = invoiceService.getTotalInvoices();
        int totalCustomers = customerService.getTotalCustomers();

        lblBooks.setText(String.valueOf(totalBooks));
        lblInvoices.setText(String.valueOf(totalInvoices));
        lblCustomers.setText(String.valueOf(totalCustomers));
    }
}
