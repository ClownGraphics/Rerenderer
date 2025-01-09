package io.github.clowngraphics.rerenderer;


import io.github.alphameo.linear_algebra.vec.Vec3;
import io.github.clowngraphics.rerenderer.math.affine_transform.Axis;
import io.github.clowngraphics.rerenderer.model.Model;
import io.github.clowngraphics.rerenderer.model.camera.Camera;
import io.github.clowngraphics.rerenderer.model.camera.CameraProperties;
import io.github.clowngraphics.rerenderer.RenderPipeline;
import io.github.clowngraphics.rerenderer.render.Scene;
import io.github.clowngraphics.rerenderer.render.texture.MonotoneTexture;
import io.github.clowngraphics.rerenderer.render.texture.Texture;
import io.github.shimeoki.jshaper.obj.ModelReader;
import javafx.application.Application;
import javafx.event.EventHandler;

import javafx.scene.Group;
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
    public void start(Stage stage) throws Exception {
        stage.setTitle("Render Test");

        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        ModelReader mr = new ModelReader();
        //TODO Пофиксить проблему с кодировками -- @Fiecher/Миша
        String filename = getClass().getResource("cube.obj").getPath();
        Texture texture = new MonotoneTexture(Color.AQUA);
        Model model = new Model(mr.read(new File(filename)), texture);
        CameraProperties cp = new CameraProperties();
        Camera camera = new Camera(new Vec3(1,2,3), cp);
        camera.setTarget(new Vec3(0,0,0));

        // TODO: Camera.lookAt() - Миша

        RenderPipeline rpipe = new RenderPipeline(gc, width, height);

        Scene scene = new Scene();
        scene.setCamera(camera, 0);
        scene.setModels(model, 0);

        rpipe.renderScene(scene);

        root.getChildren().add(canvas);
        stage.setScene(new javafx.scene.Scene(root));
        stage.show();



        stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
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
                    Stage sb = (Stage) stage.getScene().getWindow();
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
                if (t.getCode() == KeyCode.SPACE){
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


                if (t.getCode() == KeyCode.DIGIT1) {

                    currScale[getAxisId(curr)] += change;

                    System.out.println(currScale[getAxisId(curr)]);
                    model.setScale(currScale[getAxisId(curr)], curr);
                    rpipe.renderScene(scene);
                }
                if (t.getCode() == KeyCode.DIGIT2) {

                    currAngle[getAxisId(curr)] += change;

                    System.out.println(currScale[getAxisId(curr)]);
                    model.setAngle(currAngle[getAxisId(curr)], curr);
                    rpipe.renderScene(scene);
                }
                if (t.getCode() == KeyCode.DIGIT3) {

                    currPos[getAxisId(curr)] += change;

                    System.out.println(currScale[getAxisId(curr)]);
                    model.setPosition(currPos[getAxisId(curr)], curr);
                    rpipe.renderScene(scene);
                }
            }
        });


    }
}
