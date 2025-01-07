package io.github.clowngraphics.rerenderer;

import io.github.alphameo.linear_algebra.mat.Mat4Math;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import io.github.clowngraphics.rerenderer.model.Model;
import io.github.clowngraphics.rerenderer.model.camera.Camera;
import io.github.clowngraphics.rerenderer.render.Polygon;
import io.github.clowngraphics.rerenderer.render.Scene;
import io.github.clowngraphics.rerenderer.render.TriangleRasterisator;
import io.github.clowngraphics.rerenderer.render.Vertex;
import io.github.clowngraphics.rerenderer.render.ZBuffer;
import io.github.clowngraphics.rerenderer.render.texture.Texture;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RenderPipeline {
    private final ZBuffer zBuffer;
    int screenWidth;
    int screenHeight;
    private final GraphicsContext ctx;

    public RenderPipeline(final GraphicsContext ctx, int screenWidth, int screenHeight) {
        this.ctx = Objects.requireNonNull(ctx);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.zBuffer = new ZBuffer(screenWidth, screenHeight);
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void renderScene(Scene scene) {
        final Camera selectedCamera = scene.getCurrentCamera();

        zBuffer.clear();

        //TODO Тут будет что-то для освещения - @Fiecher

        for (Model model : scene.getModels()) {
            renderModel(selectedCamera, model);
        }

    }

    public void renderModel(Camera camera, Model model) {

        TriangleRasterisator rasterisator = new TriangleRasterisator(ctx.getPixelWriter());
        Texture texture = model.getTexture();
        List<Polygon> polygons = model.getPolygons();


        for (Polygon polygon : polygons) {
            List<Vertex> newVertices = new ArrayList<>();
            for (Vertex vertex : polygon.getVertices()) {
                // model matrix
                Matrix4 modelM = model.getTransform().getMatrix();
                // view matrix
                Matrix4 viewM = camera.getCameraTransform().getMatrix();
                // projection matrix
                Matrix4 projectionM = camera.getScreenTransform().getMatrix();

                Matrix4 finalM = Mat4Math.prod(projectionM, Mat4Math.prod(viewM, modelM));

                Vertex newVertex = new Vertex(Mat4Math.prod(finalM, vertex.getValues()));
                newVertices.add(newVertex);
            }
            Point2D points[] = new Point2D[3];
            for (int i = 0; i < 3; i++) {
                points[i] = new Point2D(newVertices.get(i).getX() * getScreenWidth(), newVertices.get(i).getY() * getScreenHeight());
//                System.out.println(points[i].getX() + " " + points[i].getY());
            }

            rasterisator.draw(points[0], points[1], points[2], texture);
        }

    }


    private void lookAt(Camera camera) {

    }


}
