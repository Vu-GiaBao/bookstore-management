package com.bookstore.controller;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class MainController {

    @FXML
    private AnchorPane contentArea;

    @FXML
    public void initialize() {
        openDashboard(); 
    }

    @FXML
    private void openDashboard() {
        loadView("/com/bookstore/DashboardView.fxml");
    }
    
    @FXML
    private void onDashboardClick() {
        showDashboard();
    }

    @FXML
    private void openBooks() {
        loadView("/com/bookstore/BooksView.fxml");
    }

    @FXML
    private void openCustomers() {
        loadView("/com/bookstore/CustomersView.fxml");
    }

    @FXML
    private void openInvoices() {
        loadView("/com/bookstore/InvoicesView.fxml");
    }

    private void loadView(String path) {
        try {
            Node view = FXMLLoader.load(getClass().getResource(path));
            contentArea.getChildren().setAll(view);
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDashboard() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bookstore/view/DashboardView.fxml"));
        Parent root = loader.load();

        DashboardController controller = loader.getController();
        controller.refreshDashboard(); 
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
