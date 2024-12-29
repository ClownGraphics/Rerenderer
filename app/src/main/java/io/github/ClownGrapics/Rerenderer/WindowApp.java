package io.github.ClownGrapics.Rerenderer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.checkerframework.common.reflection.qual.GetClass;

import java.io.IOException;

public class WindowApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        stage.setTitle("3D viewer");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
