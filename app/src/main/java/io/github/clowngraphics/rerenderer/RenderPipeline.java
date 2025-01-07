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

    private final GraphicsContext ctx;


    public RenderPipeline(final GraphicsContext ctx) {
        this.ctx = Objects.requireNonNull(ctx);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();
        this.zBuffer = new ZBuffer(screenWidth, screenHeight);
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

        List<Vertex> newVertices = new ArrayList<>();

        for (Vertex vertex : model.getVertices()) {
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

        drawModel(newVertices, model);

    }

    private void drawModel(List<Vertex> vertices, Model model) {
        TriangleRasterisator rasterisator = new TriangleRasterisator(ctx.getPixelWriter());
        Texture texture = model.getTexture();
        List<Polygon> polygons = model.getPolygons();


        for (Polygon polygon : polygons) {
            int index1 = polygon.getVertexIndices().get(0);
            int index2 = polygon.getVertexIndices().get(1);
            int index3 = polygon.getVertexIndices().get(2);

            Vertex v1 = vertices.get(index1);
            Vertex v2 = vertices.get(index2);
            Vertex v3 = vertices.get(index3);

            Point2D p1 = new Point2D(v1.getX(), v1.getY());
            Point2D p2 = new Point2D(v2.getX(), v2.getY());
            Point2D p3 = new Point2D(v3.getX(), v3.getY());

            rasterisator.draw(p1, p2, p3, texture);
        }
    }


    private void lookAt(Camera camera) {

    }


}
