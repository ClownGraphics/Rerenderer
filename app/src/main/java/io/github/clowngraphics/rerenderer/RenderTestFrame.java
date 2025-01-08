package io.github.clowngraphics.rerenderer;

import io.github.alphameo.linear_algebra.vec.Vec2;
import io.github.clowngraphics.rerenderer.render.TriangleRasterisator;
import io.github.clowngraphics.rerenderer.render.texture.ImageTexture;
import io.github.clowngraphics.rerenderer.render.texture.PolygonUVCoordinates;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    int width = 500;
    int height = 500;

    public void showTriangle(GraphicsContext gc){

        gc.clearRect(0, 0, width, height);
        TriangleRasterisator tr = new TriangleRasterisator(gc.getPixelWriter());

        Random rnd = new Random();
        try {
            // Указываем путь к изображению
            File imageFile = new File("C:\\Users\\Stepan\\Downloads\\spampton.jpg");
            p1 = new Point2D(100, 400);
            p2 = new Point2D(0, 500);
            p3 = new Point2D(500,500);
            // Загружаем изображение в объект BufferedImage
            BufferedImage image = ImageIO.read(imageFile);
            PolygonUVCoordinates polygonUvCoordinates = new PolygonUVCoordinates(new Vec2(
                    (float) p1.getX()/width,(float) p1.getY()/height),
                    new Vec2((float) p2.getX()/width, (float) p2.getY()/height),
                    new Vec2((float) p3.getX()/width,(float) p3.getY()/height));

            ImageTexture mt = ImageTexture.setFromFile(imageFile, polygonUvCoordinates);
//            MonotoneTexture mt = new MonotoneTexture(Color.BLANCHEDALMOND);

            System.out.println(mt.get(0,0));
//            tr.draw(p1, p2, p3, mt);

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
        Canvas canvas = new Canvas(width, height);
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
