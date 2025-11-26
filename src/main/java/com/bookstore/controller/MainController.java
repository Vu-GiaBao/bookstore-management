package com.bookstore.controller;

import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class MainController {

    @FXML
    private AnchorPane contentArea;

    @FXML
    public void initialize() {
        openDashboard(); // mở sẵn dashboard khi chạy app
    }

    @FXML
    private void openDashboard() {
        loadView("/com/bookstore/DashboardView.fxml");
    }

    @FXML
    private void openBooks() {
        loadView("/com/bookstore/BooksView.fxml");
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
}
