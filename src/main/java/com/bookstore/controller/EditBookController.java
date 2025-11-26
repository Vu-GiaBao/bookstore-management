package com.bookstore.controller;

import com.bookstore.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditBookController {

    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField priceField;

    private Book book;

    public void setBook(Book book) {
        this.book = book;
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        priceField.setText(Double.toString(book.getPrice()));
    }

    @FXML
    public void onSave() {
        book.setTitle(titleField.getText());
        book.setAuthor(authorField.getText());
        book.setPrice(Double.parseDouble(priceField.getText()));

        titleField.getScene().getWindow().hide();
    }

    @FXML
    public void onCancel() {
        titleField.getScene().getWindow().hide();
    }
}
