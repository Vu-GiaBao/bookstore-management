package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

public class BooksController {

    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, Integer> colId;
    @FXML
    private TableColumn<Book, String> colTitle;
    @FXML
    private TableColumn<Book, String> colAuthor;
    @FXML
    private TableColumn<Book, Double> colPrice;
    @FXML
    private TableColumn<Book, Integer> colQuantity;
    @FXML
    private TableColumn<Book, Void> colAction;
    @FXML
    private TextField searchField;

    private final BookService bookService = new BookService();
    private final ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colTitle.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        colAuthor.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        colPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        colQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        initActionButtons();
        loadBooks();
        bookTable.setItems(bookList);
    }

    private void initActionButtons() {
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox pane = new HBox(10, editButton, deleteButton);

            {
                editButton.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    openEdit(book);
                });
                deleteButton.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    deleteBook(book);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        });
    }

    private void loadBooks() {
        try {
            bookList.setAll(bookService.getAllBooks());
        } catch (Exception e) {
            showError("Error loading books", "Could not load books from the database.");
            System.err.println (e.getMessage());
        }

    }

    private void deleteBook(Book book) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Book");
        alert.setHeaderText("Are you sure you want to delete this book?");
        alert.setContentText(book.getTitle());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (bookService.deleteBook(book.getId())) {
                bookList.remove(book);
            } else {
                showError("Error deleting book", "Could not delete the book from the database.");
            }
        }
    }

    public void openEdit(Book book) {
    try {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/bookstore/EditBookView.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.initModality(Modality.APPLICATION_MODAL);

        EditBookController controller = loader.getController();
        controller.setBook(book);

        stage.showAndWait();
        onRefresh();
    } catch (IOException e) {
        System.err.println(e);
        e.printStackTrace();
        showError("Error opening view", "Could not open the edit book view.");
    }
    }

    @FXML
    public void onAddBook() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/bookstore/AddBookView.fxml"));
            Stage popup = new Stage();
            popup.setScene(new Scene(loader.load()));
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.showAndWait();
            onRefresh();
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
            showError("Error opening view", "Could not open the add book view.");
        }
    }

    @FXML
    public void onSearch() {
        String keyword = searchField.getText();
        if (keyword == null || keyword.isBlank()) {
            loadBooks();
        } else {
            bookList.setAll(bookService.searchBooks(keyword));
        }
    }

    @FXML
    public void onRefresh() {
        searchField.clear();
        loadBooks();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
