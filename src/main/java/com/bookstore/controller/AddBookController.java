package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddBookController {

    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField priceField;

    private BookService service = BookService.getInstance();

    @FXML
    public void onAdd() {
        Book book = new Book(
                service.generateId(),
                titleField.getText(),
                authorField.getText(),
                Double.parseDouble(priceField.getText())
        );

        service.addBook(book);
        titleField.getScene().getWindow().hide();
    }

    @FXML
    public void onCancel() {
        titleField.getScene().getWindow().hide();
    }
}
