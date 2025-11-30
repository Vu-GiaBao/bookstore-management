package com.bookstore.controller;

import com.bookstore.model.Customer;
import com.bookstore.service.CustomerService;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
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

public class CustomersController {

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> colId;

    @FXML
    private TableColumn<Customer, String> colName;

    @FXML
    private TableColumn<Customer, String> colEmail;

    @FXML
    private TableColumn<Customer, String> colPhone;

    @FXML
    private TableColumn<Customer, String> colAddress;

    @FXML
    private TableColumn<Customer, Void> colAction;

    @FXML
    private TextField searchField;

    private final CustomerService customerService = new CustomerService();
    private final ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
    colId.setCellValueFactory(cell ->
            new ReadOnlyObjectWrapper<>(cell.getValue().getId()));

    colName.setCellValueFactory(cell ->
            new ReadOnlyStringWrapper(cell.getValue().getName()));

    colEmail.setCellValueFactory(cell ->
            new ReadOnlyStringWrapper(cell.getValue().getEmail()));

    colPhone.setCellValueFactory(cell ->
            new ReadOnlyStringWrapper(cell.getValue().getPhone()));

    colAddress.setCellValueFactory(cell ->
            new ReadOnlyStringWrapper(cell.getValue().getAddress()));
    initActionButtons();
    loadCustomers();
    customerTable.setItems(customerList);
    }

    private void initActionButtons() {
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox pane = new HBox(10, editButton, deleteButton);

            {
                editButton.setOnAction(event -> {
                    Customer c = getTableView().getItems().get(getIndex());
                    openEdit(c);
                });

                deleteButton.setOnAction(event -> {
                    Customer c = getTableView().getItems().get(getIndex());
                    deleteCustomer(c);
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

    private void loadCustomers() {
        try {
            customerList.setAll(customerService.getAllCustomers());
        } catch (Exception e) {
            showError("Error Loading Customers", "Could not load customer data.");
            e.printStackTrace();
        }
    }

    private void deleteCustomer(Customer c) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Customer");
        alert.setHeaderText("Are you sure you want to delete this customer?");
        alert.setContentText(c.getName());

        var result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (customerService.deleteCustomer(c.getId())) {
                customerList.remove(c);
            } else {
                showError("Error Deleting Customer", "Could not delete customer from the database.");
            }
        }
    }

    private void openEdit(Customer customer) {
    try {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/bookstore/EditCustomerView.fxml")
        );

        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.initModality(Modality.APPLICATION_MODAL);

        EditCustomerController controller = loader.getController();
        controller.setCustomer(customer);

        stage.showAndWait();
        loadCustomers(); // refresh
        customerTable.refresh();

        } catch (Exception e) {
        e.printStackTrace();
        }
    }



    @FXML
    public void onAddCustomer() {
    try {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/bookstore/AddCustomerView.fxml")
        );

        Stage popup = new Stage();
        popup.setScene(new Scene(loader.load()));
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.showAndWait();

        onRefresh(); // load lại bảng sau khi thêm
        } catch (IOException e) {
        e.printStackTrace();
        showError("Error opening view", "Could not open the add customer view.");
        }
    }


    @FXML
    public void onSearch() {
        String keyword = searchField.getText();
        if (keyword == null || keyword.isBlank()) {
            loadCustomers();
        } else {
            customerList.setAll(
                customerService.getAllCustomers().stream()
                    .filter(c -> c.getName().toLowerCase().contains(keyword.toLowerCase()))
                    .toList()
            );
        }
    }

    @FXML
    public void onRefresh() {
        searchField.clear();
        loadCustomers();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
