package io.github.clowngraphics.rerenderer;


import io.github.alphameo.linear_algebra.vec.Vec3;
import io.github.clowngraphics.rerenderer.math.affine_transform.Axis;
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
import javafx.scene.paint.Color;
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
        //TODO Пофиксить проблему с кодировками -- @Fiecher/Миша
        String filename = getClass().getResource("WrapSkull.obj").getPath();

        Model model = new Model(mr.read(new File(filename)));
        CameraProperties cp = new CameraProperties();
        Camera camera = new Camera(new Vec3(15,0,0), cp);
        camera.setTarget(new Vec3(0,0,0));

        // TODO: Camera.lookAt() - Миша

        RenderPipeline rpipe = new RenderPipeline(gc, width, height);

        rpipe.renderModel(camera, model);

        root.getChildren().add(canvas);
        scene.setScene(new Scene(root));
        scene.show();



        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            Axis curr = Axis.X;
            int mode = 1;
            float amount = 0.1f;
            float[] currScale = new float[]{1, 1, 1};
            float[] currAngle = new float[3];
            float[] currPos = new float[3];
            private int getAxisId(Axis axis){
                switch (axis){
                    case X-> {
                        return 0;
                    }
                    case Y-> {
                        return 1;
                    }
                    case Z-> {
                        return 2;
                    }
                }
                return 0;
            }
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ESCAPE) {
                    Stage sb = (Stage) scene.getScene().getWindow();
                    sb.close();
                }
                if (t.getCode() == KeyCode.X){
                    curr = Axis.X;
                }
                if (t.getCode() == KeyCode.Y){
                    curr = Axis.Y;
                }
                if (t.getCode() == KeyCode.Z){
                    curr = Axis.Z;
                }
                if (t.getCode() == KeyCode.ENTER){
                    mode = -mode;
                }
                if(t.getCode() == KeyCode.DIGIT4){
                    amount += 0.05f;
                    System.out.println(amount);
                }
                if(t.getCode() == KeyCode.DIGIT5){
                    amount -= 0.05f;
                    System.out.println(amount);
                }
                float change = amount * mode;
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, width, height);
                gc.setFill(Color.BLACK);


                if (t.getCode() == KeyCode.DIGIT1) {
                    currScale[getAxisId(curr)] += change;
                    System.out.println(currScale[getAxisId(curr)]);
                    model.setScale(currScale[getAxisId(curr)], curr);
                }
                if (t.getCode() == KeyCode.DIGIT2) {
                    currAngle[getAxisId(curr)] += change;
                    System.out.println(currScale[getAxisId(curr)]);
                    model.setAngle(currAngle[getAxisId(curr)], curr);
                }
                if (t.getCode() == KeyCode.DIGIT3) {
                    currPos[getAxisId(curr)] += change;
                    System.out.println(currScale[getAxisId(curr)]);
                    model.setPosition(currPos[getAxisId(curr)], curr);
                }

                handleCamera(camera, t);
                rpipe.renderModel(camera, model);
            }

        });


    }

    private void handleCamera(Camera camera, KeyEvent t) {
        if (t.getCode() == KeyCode.W) {
            camera.translate(0.1f, Axis.Z);
        }
        if (t.getCode() == KeyCode.S) {
            camera.translate(-0.1f, Axis.Z);
        }
        if (t.getCode() == KeyCode.A) {
            camera.translate(0.1f, Axis.X);
        }
        if (t.getCode() == KeyCode.D) {
            camera.translate(-0.1f, Axis.X);
        }
    }
}
