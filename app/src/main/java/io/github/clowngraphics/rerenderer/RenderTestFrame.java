package io.github.clowngraphics.rerenderer;

import io.github.clowngraphics.rerenderer.render.TriangleRasterisator;
import io.github.clowngraphics.rerenderer.render.color.MonotoneTexture;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class RenderTestFrame extends Application {

    private Point2D p1;
    private Point2D p2;
    private Point2D p3;

    public void showTriangle(GraphicsContext gc){
        gc.clearRect(0, 0, 1000, 1000);
        TriangleRasterisator tr = new TriangleRasterisator(gc.getPixelWriter());

        Random rnd = new Random();
        MonotoneTexture mt = new MonotoneTexture(Color.BLACK);


        p1 = new Point2D(rnd.nextInt(1000), rnd.nextInt(1000));
        p2 = new Point2D(rnd.nextInt(1000), rnd.nextInt(1000));
        p3 = new Point2D(rnd.nextInt(1000), rnd.nextInt(1000));
        tr.draw(p1, p2, p3, mt);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage scene) throws Exception {
        scene.setTitle("Render Test");

        Group root = new Group();
        Canvas canvas = new Canvas(1000, 1000);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        showTriangle(gc);

        root.getChildren().add(canvas);
        scene.setScene(new Scene(root));
        scene.show();

        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ESCAPE) {
                    Stage sb = (Stage) scene.getScene().getWindow();
                    sb.close();
                }
            }
        });
    }
}
