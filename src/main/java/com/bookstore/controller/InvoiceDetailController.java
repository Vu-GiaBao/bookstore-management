package com.bookstore.controller;

import com.bookstore.model.Invoice;
import com.bookstore.model.InvoiceItem;
import com.bookstore.model.InvoiceStatus;
import com.bookstore.database.InvoiceItemDAO;
import com.bookstore.model.Customer;
import com.bookstore.service.CustomerService;
import com.bookstore.service.InvoiceService;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class InvoiceDetailController {

    // Labels hiển thị thông tin Invoice
    @FXML private Label lblId;
    @FXML private Label lblCustomer;
    @FXML private Label lblDate;
    @FXML private Label lblTotal;
    @FXML private Label lblStatus;

    @FXML private TableView<InvoiceItem> tblItems;
    @FXML private TableColumn<InvoiceItem, String> colBook;
    @FXML private TableColumn<InvoiceItem, Integer> colQty;
    @FXML private TableColumn<InvoiceItem, Double> colPrice;
    @FXML private TableColumn<InvoiceItem, Double> colSubtotal;

    @FXML private Button btnPay;

    private Invoice invoice;

    @FXML
    public void initialize() {
        colBook.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getBook().getTitle()
                )
        );

        colQty.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getQuantity()
                ).asObject()
        );

        colPrice.setCellValueFactory(data ->
            new SimpleDoubleProperty(
                data.getValue().getBook().getPrice()
            ).asObject()
        );

        colSubtotal.setCellValueFactory(data ->
            new SimpleDoubleProperty(
                data.getValue().getSubtotal()
            ).asObject()
        );
    }


    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
        loadData();
    }

    private void loadData() {
        if (invoice == null) return;

        lblId.setText(invoice.getInvoiceId());
        lblDate.setText(invoice.getDate().toString());
        lblTotal.setText(String.valueOf(invoice.getTotalAmount()));
        lblStatus.setText(invoice.getStatus().name());

        CustomerService cs = new CustomerService();
        Customer customer = cs.findCustomerById(invoice.getCustomerId());
        lblCustomer.setText(customer != null ? customer.getName() : "Unknown");

        InvoiceItemDAO itemDAO = new InvoiceItemDAO();
        List<InvoiceItem> items = itemDAO.getByInvoiceId(invoice.getInvoiceId());
        tblItems.setItems(FXCollections.observableArrayList(items));

        if (invoice.getStatus() != InvoiceStatus.PENDING) {
            btnPay.setDisable(true);
        }
    }

    @FXML
    private void onPay() {
        try {
            InvoiceService service = new InvoiceService();
            service.updateStatus(invoice.getInvoiceId(), InvoiceStatus.PAID);

            invoice.setStatus(InvoiceStatus.PAID);

            lblStatus.setText("PAID");
            btnPay.setDisable(true);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Payment Success");
            alert.setHeaderText("Invoice has been marked as PAID.");
            alert.showAndWait();

        } catch (Exception e) {
            showError("Error", "Failed to mark invoice as paid.\n" + e.getMessage());
        }
    }

    @FXML
    private void onClose() {
        Stage stage = (Stage) lblId.getScene().getWindow();
        stage.close();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
