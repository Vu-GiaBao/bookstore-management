package com.bookstore.controller;

import com.bookstore.model.Customer;
import com.bookstore.service.CustomerService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditCustomerController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;

    private CustomerService customerService = new CustomerService();
    private Customer customer;


    public void setCustomer(Customer customer) {
        this.customer = customer;
        nameField.setText(customer.getName());
        emailField.setText(customer.getEmail());
        phoneField.setText(customer.getPhone());
        addressField.setText(customer.getAddress());
    }

    @FXML
    private void onSave() {
        customer.setName(nameField.getText());
        customer.setEmail(emailField.getText());
        customer.setPhone(phoneField.getText());
        customer.setAddress(addressField.getText());

        customerService.updateCustomer(customer);
        closeWindow();
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
