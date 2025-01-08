package io.github.clowngraphics.rerenderer.window;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuItem;

public class MainController {

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private MenuItem deleteMenuItem;

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {

        closeMenuItem.setOnAction(event -> handleClose());
        deleteMenuItem.setOnAction(event -> handleDelete());
        aboutMenuItem.setOnAction(event -> handleAbout());
    }

    private void handleClose() {

        System.out.println("Close menu clicked!");
        System.exit(0);
    }

    private void handleDelete() {

        System.out.println("Delete menu clicked!");
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void handleAbout() {
        System.out.println("About menu clicked!");

    }
}

