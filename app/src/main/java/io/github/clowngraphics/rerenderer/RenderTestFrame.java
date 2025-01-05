package io.github.clowngraphics.rerenderer;

import io.github.clowngraphics.rerenderer.render.TriangleRasterisator;
import io.github.clowngraphics.rerenderer.render.texture.ImageTexture;
import io.github.clowngraphics.rerenderer.render.texture.MonotoneTexture;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class RenderTestFrame extends Application {

    private Point2D p1;
    private Point2D p2;
    private Point2D p3;

    public void showTriangle(GraphicsContext gc){
        gc.clearRect(0, 0, 1000, 1000);
        TriangleRasterisator tr = new TriangleRasterisator(gc.getPixelWriter());

        Random rnd = new Random();
        try {
            // Указываем путь к изображению
            File imageFile = new File("C:\\Users\\user\\Downloads\\grad.jpg");

            // Загружаем изображение в объект BufferedImage
            BufferedImage image = ImageIO.read(imageFile);

            ImageTexture mt = ImageTexture.setFromFile(imageFile);
//            MonotoneTexture mt = new MonotoneTexture(Color.BLANCHEDALMOND);
            p1 = new Point2D(100, 100);
            p2 = new Point2D(0, 500);
            p3 = new Point2D(900,1000);
            System.out.println(mt.get(0,0));
            tr.draw(p1, p2, p3, mt);

        } catch (IOException e) {
            System.err.println("Ошибка при загрузке изображения: " + e.getMessage());
        }



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

        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.SPACE) {
                    root.getChildren().clear();
                    showTriangle(gc);
                    root.getChildren().add(canvas);
                }
            }
        });
    }
}
