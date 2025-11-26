package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.*;

public class BooksController {

    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book, Integer> colId;
    @FXML private TableColumn<Book, String> colTitle;
    @FXML private TableColumn<Book, String> colAuthor;
    @FXML private TableColumn<Book, Double> colPrice;
    @FXML private TableColumn<Book, Void> colAction;
    @FXML private TextField searchField;

    private BookService service = BookService.getInstance();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(sample -> sample.getValue().idProperty().asObject());
        colTitle.setCellValueFactory(sample -> sample.getValue().titleProperty());
        colAuthor.setCellValueFactory(sample -> sample.getValue().authorProperty());
        colPrice.setCellValueFactory(sample -> sample.getValue().priceProperty().asObject());

        initActionButtons();
        bookTable.setItems(service.getBooks());
    }

    private void initActionButtons() {
        colAction.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            private final Button delBtn = new Button("Delete");
            private final HBox box = new HBox(10, editBtn, delBtn);

            {
                editBtn.setOnAction(e -> openEdit(getTableView().getItems().get(getIndex())));
                delBtn.setOnAction(e -> {
                    Book book = getTableView().getItems().get(getIndex());
                    service.deleteBook(book);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else setGraphic(box);
            }
        });
    }

    public void openEdit(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bookstore/EditBookView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);

            EditBookController controller = loader.getController();
            controller.setBook(book);

            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void onAddBook() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bookstore/AddBookView.fxml"));
            Stage popup = new Stage();
            popup.setScene(new Scene(loader.load()));
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.show();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    public void onSearch() {
        String keyword = searchField.getText().toLowerCase();
        ObservableList<Book> filtered = service.search(keyword);
        bookTable.setItems(filtered);
    }

    @FXML
    public void onRefresh() {
        bookTable.setItems(service.getBooks());
    }
}
