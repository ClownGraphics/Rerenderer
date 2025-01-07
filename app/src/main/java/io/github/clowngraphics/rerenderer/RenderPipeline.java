package io.github.clowngraphics.rerenderer;

import io.github.alphameo.linear_algebra.mat.Matrix4;
import io.github.clowngraphics.rerenderer.model.Model;
import io.github.clowngraphics.rerenderer.model.camera.Camera;
import io.github.clowngraphics.rerenderer.render.Scene;
import io.github.clowngraphics.rerenderer.render.Vertex;
import io.github.clowngraphics.rerenderer.render.ZBuffer;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RenderPipeline {
    private final ZBuffer zBuffer;

    private final GraphicsContext ctx;


    public RenderPipeline(final GraphicsContext ctx) {
        this.ctx = Objects.requireNonNull(ctx);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();
        this.zBuffer = new ZBuffer(screenWidth, screenHeight);
    }

    public void renderScene(Scene scene){
        final Camera selectedCamera = scene.getCurrentCamera();

        for (Model model : scene.getModels()){
            renderModel(selectedCamera, model);
        }


        zBuffer.clear();
    }

    public void renderModel(Camera camera, Model model){

        List<Vertex> newVertices = new ArrayList<>();

        for (Vertex vertices : model.getVertices()){
            // model matrix
            Matrix4 modelM = model.getModelMatrix();
            // view matrix
            Matrix4 viewM = camera.getCameraTransform().getMatrix();
            // projection matrix
            Matrix4 projectionM = camera.getScreenTransform().getMatrix();

            Matrix4

            Vertex newVertex =
            newVertices.add()
        }

    }

    private void lookAt(Camera camera){

    }

}
