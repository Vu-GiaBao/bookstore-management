package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditBookController {

    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField priceField;
    @FXML private TextField quantityField;

    private Book book;
    private final BookService bookService = new BookService();

    public void setBook(Book book) {
        this.book = book;
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        priceField.setText(String.valueOf(book.getPrice()));
        quantityField.setText(String.valueOf(book.getQuantity()));
    }

    @FXML
    private void onSave() {
        String title = titleField.getText();
        String author = authorField.getText();
        String priceText = priceField.getText();
        String quantityText = quantityField.getText();

        if (title.isEmpty() || author.isEmpty() ||
            priceText.isEmpty() || quantityText.isEmpty()) {
            showError("Invalid Input", "All fields are required.");
            return;
        }

        double price;
        int quantity;
        try {
            price = Double.parseDouble(priceText);
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            showError("Invalid Input", "Price and quantity must be numbers.");
            return;
        }

        book.setTitle(title);
        book.setAuthor(author);
        book.setPrice(price);
        book.setQuantity(quantity);

        if (bookService.updateBook(book)) {
            showInfo("Book Updated", "The book has been updated successfully.");
            closeWindow();
        } else {
            showError("Error Updating Book",
                    "Could not update the book in the database.");
        }
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
