package io.github.clowngraphics.rerenderer;


import io.github.alphameo.linear_algebra.vec.Vec3;
import io.github.clowngraphics.rerenderer.model.Model;
import io.github.clowngraphics.rerenderer.model.camera.Camera;
import io.github.clowngraphics.rerenderer.model.camera.CameraProperties;
import io.github.clowngraphics.rerenderer.RenderPipeline;
import io.github.shimeoki.jshaper.obj.ModelReader;
import javafx.application.Application;
import javafx.event.EventHandler;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


import java.io.File;


public class RenderTest extends Application{

    int width = 500;
    int height = 500;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage scene) throws Exception {
        scene.setTitle("Render Test");

        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        ModelReader mr = new ModelReader();


        Model model = new Model(mr.read(new File("C:\\Users\\Stepan\\Downloads\\caracal_cube.obj")));
        CameraProperties cp = new CameraProperties();
        Camera camera = new Camera(new Vec3(0,100,100), cp);
//        camera.setTarget(new Vec3(0,0,0));
        // TODO: Camera.lookAt() - Миша

        RenderPipeline rpipe = new RenderPipeline(gc, width, height);

        rpipe.renderModel(camera, model);

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
