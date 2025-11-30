package com.bookstore.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import com.bookstore.model.Customer;
import com.bookstore.service.CustomerService;

public class AddCustomerController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;

    private final CustomerService customerService = new CustomerService();

    @FXML
    private void onSave() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            showError("Invalid Input", "All fields are required.");
            return;
        }

        Customer c = new Customer(0, name, email, phone, address);
        int rows = customerService.addCustomer(c);

        if (rows > 0) {
            showInfo("Success", "Customer added successfully!");
            closeWindow();
        } else {
            showError("Error", "Failed to add customer.");
        }
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private void showError(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
