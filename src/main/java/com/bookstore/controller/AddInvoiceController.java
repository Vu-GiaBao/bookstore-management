package com.bookstore.controller;

import com.bookstore.model.*;
import com.bookstore.service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class AddInvoiceController {

    @FXML private ComboBox<Customer> customerBox;
    @FXML private ComboBox<User> userBox;
    @FXML private ComboBox<Book> bookBox;
    @FXML private TextField quantityField;
    @FXML private TableView<InvoiceItem> itemTable;

    @FXML private TableColumn<InvoiceItem, String> colBook;
    @FXML private TableColumn<InvoiceItem, Integer> colQty;
    @FXML private TableColumn<InvoiceItem, Double> colPrice;
    @FXML private TableColumn<InvoiceItem, Double> colSubtotal;

    private ObservableList<InvoiceItem> items = FXCollections.observableArrayList();

    private CustomerService customerService = new CustomerService();
    private UserService userService = new UserService();
    private BookService bookService = new BookService();
    private InvoiceService invoiceService = new InvoiceService();

    @FXML
    public void initialize() {
        customerBox.getItems().addAll(customerService.getAll());
        userBox.getItems().addAll(userService.getAllUsers());
        bookBox.getItems().addAll(bookService.getAllBooks());

        itemTable.setItems(items);

        colBook.setCellValueFactory(cell ->
            new javafx.beans.property.SimpleStringProperty(cell.getValue().getBook().getTitle())
        );

        colQty.setCellValueFactory(cell ->
            new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getQuantity()).asObject()
        );

        colPrice.setCellValueFactory(cell ->
            new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getBook().getPrice()).asObject()
        );

        colSubtotal.setCellValueFactory(cell ->
            new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getSubtotal()).asObject()
        );
    }

    @FXML
    private void onAddItem() {
        Book book = bookBox.getValue();
        String qtyText = quantityField.getText();

        if (book == null || qtyText.isEmpty()) {
            showAlert("Missing data", "Please choose a book and enter quantity.");
            return;
        }

        try {
            int qty = Integer.parseInt(qtyText);
            if (qty <= 0) {
                showAlert("Invalid quantity", "Quantity must be positive.");
                return;
            }

            if (qty > book.getQuantity()) {
                showAlert("Not enough stock", "Not enough books in stock.");
                return;
            }

            items.add(new InvoiceItem(book, qty));
            quantityField.clear();

        } catch (NumberFormatException e) {
            showAlert("Invalid input", "Please enter a valid number.");
        }
    }

    @FXML
    private void onSave() {

        Customer c = customerBox.getValue();
        User u = userBox.getValue();

        if (c == null || u == null || items.isEmpty()) {
            showAlert("Missing Data", "Please select customer, user, and add items.");
            return;
        }

        // ✔ tạo hóa đơn bằng constructor đúng
        Invoice invoice = new Invoice(UUID.randomUUID().toString());

        invoice.setCustomerId(c.getId());
        invoice.setUserId(u.getId());
        invoice.setDate(LocalDate.now());
        invoice.setStatus(InvoiceStatus.PENDING);
        invoice.setItems(new ArrayList<>(items));

        double total = items.stream()
                .mapToDouble(InvoiceItem::getSubtotal)
                .sum();
        invoice.setTotalAmount(total);

        invoiceService.save(invoice);

        closeWindow();
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) customerBox.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.setTitle(title);
        alert.show();
    }
}
