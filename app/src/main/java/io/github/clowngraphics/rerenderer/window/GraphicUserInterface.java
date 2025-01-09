package io.github.clowngraphics.rerenderer.window;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GraphicUserInterface extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/io.github.clowngraphics.rerenderer.window/gui.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);


        primaryStage.setTitle("3D viewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
