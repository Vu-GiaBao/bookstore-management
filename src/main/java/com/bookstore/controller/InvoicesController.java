package com.bookstore.controller;

import com.bookstore.model.Invoice;
import com.bookstore.model.Customer;
import com.bookstore.service.InvoiceService;
import com.bookstore.service.CustomerService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;


public class InvoicesController {

    @FXML private TextField searchField;
    @FXML private TableView<Invoice> invoiceTable;
    @FXML private TableColumn<Invoice, String> colId;
    @FXML private TableColumn<Invoice, String> colCustomer;
    @FXML private TableColumn<Invoice, Double> colTotal;
    @FXML private TableColumn<Invoice, String> colDate;
    @FXML private TableColumn<Invoice, Void> colAction;

    private final InvoiceService invoiceService = new InvoiceService();
    private final CustomerService customerService = new CustomerService();
    private final ObservableList<Invoice> invoiceList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        colId.setCellValueFactory(cell ->
            new javafx.beans.property.SimpleStringProperty(cell.getValue().getInvoiceId())
        );

        colCustomer.setCellValueFactory(cell -> {
            int customerId = cell.getValue().getCustomerId();
            Customer c = customerService.findCustomerById(customerId);
            String name = (c != null) ? c.getName() : "Unknown";
            return new javafx.beans.property.SimpleStringProperty(name);
        });

        colTotal.setCellValueFactory(cell ->
            new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getTotalAmount()).asObject()
        );

        colDate.setCellValueFactory(cell ->
            new javafx.beans.property.SimpleStringProperty(cell.getValue().getDate().toString())
        );

        initActionButtons();

        loadInvoices();
    }

 
    private void loadInvoices() {
        invoiceList.setAll(invoiceService.getAll());
        invoiceTable.setItems(invoiceList);
    }


    private void initActionButtons() {

        colAction.setCellFactory(param -> new TableCell<>() {

            private final Button viewButton = new Button("View");
            private final Button deleteButton = new Button("Delete");
            private final HBox pane = new HBox(10, viewButton, deleteButton);

            {
                viewButton.setOnAction(e -> {
                    Invoice invoice = getTableView().getItems().get(getIndex());
                    openInvoiceDetail(invoice);
                });

                deleteButton.setOnAction(e -> {
                    Invoice invoice = getTableView().getItems().get(getIndex());
                    deleteInvoice(invoice);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }


    private void deleteInvoice(Invoice invoice) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete invoice " + invoice.getInvoiceId() + " ?", ButtonType.YES, ButtonType.NO);

        confirm.showAndWait();

        if (confirm.getResult() == ButtonType.YES) {
            try {
                invoiceService.delete(invoice.getInvoiceId());
                loadInvoices();
            } catch (Exception e) {
                showError("Error", "Could not delete invoice: " + e.getMessage());
            }
        }
    }


    @FXML
    private void onRefresh() {
        loadInvoices();
    }


    @FXML
    private void onSearch() {
        String keyword = searchField.getText();
        invoiceList.setAll(invoiceService.searchInvoice(keyword));
    }

    @FXML
    private void onAddInvoice() {
    try {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/bookstore/AddInvoiceView.fxml")
        );
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Add Invoice");
        stage.setScene(new Scene(root));
        stage.show();

        } catch (Exception e) {
        e.printStackTrace();
        showError("Could not open Add Invoice view.", e.getMessage());
        }
    }

    private void openInvoiceDetail(Invoice invoice) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bookstore/InvoiceDetailView.fxml"));
            Parent view = loader.load();

            // Set data into detail controller
            Object controller = loader.getController();
            try {
                controller.getClass()
                        .getMethod("setInvoice", Invoice.class)
                        .invoke(controller, invoice);
            } catch (Exception ignored) {}

            Stage stage = new Stage();
            stage.setScene(new Scene(view));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Invoice Details");
            stage.setResizable(false);
            stage.showAndWait();

        } catch (Exception e) {
            showError("Error", "Could not open invoice detail view.\n" + e.getMessage());
        }
    }


    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
