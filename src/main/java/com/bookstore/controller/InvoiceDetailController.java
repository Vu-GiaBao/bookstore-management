package com.bookstore.controller;

import com.bookstore.model.Invoice;
import com.bookstore.model.Customer;
import com.bookstore.service.CustomerService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InvoiceDetailController {

    @FXML private Label lblId;
    @FXML private Label lblCustomer;
    @FXML private Label lblDate;
    @FXML private Label lblTotal;
    @FXML private Label lblStatus;

    private Invoice invoice;

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
        loadData();
    }

    private void loadData() {
        if (invoice == null) return;

        lblId.setText(invoice.getInvoiceId());
        lblDate.setText(invoice.getDate().toString());
        lblTotal.setText(String.valueOf(invoice.getTotalAmount()));
        lblStatus.setText(invoice.getStatus().name());

        CustomerService customerService = new CustomerService();
        Customer customer = customerService.findCustomerById(invoice.getCustomerId());

        if (customer != null) {
            lblCustomer.setText(customer.getName());
        } else {
            lblCustomer.setText("Unknown");
        }
    }

    @FXML
    private void onClose() {
        Stage stage = (Stage) lblId.getScene().getWindow();
        stage.close();
    }
}
