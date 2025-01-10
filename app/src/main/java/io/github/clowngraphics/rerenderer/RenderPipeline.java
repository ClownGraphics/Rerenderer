package io.github.clowngraphics.rerenderer;

import io.github.alphameo.linear_algebra.vec.*;
import io.github.clowngraphics.rerenderer.math.affine_transform.GeneralTransformation;
import io.github.clowngraphics.rerenderer.model.Model;
import io.github.clowngraphics.rerenderer.model.camera.Camera;
import io.github.clowngraphics.rerenderer.model.transform.CameraTransform;
import io.github.clowngraphics.rerenderer.model.transform.ModelTransform;
import io.github.clowngraphics.rerenderer.model.transform.ScreenTransform;
import io.github.clowngraphics.rerenderer.render.*;
import io.github.clowngraphics.rerenderer.render.Polygon;
import io.github.clowngraphics.rerenderer.render.texture.Texture;
import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RenderPipeline {
    private final ZBuffer zBuffer;
    int screenWidth;
    int screenHeight;
    private final GraphicsContext ctx;
    private RenderType currentRenderType = RenderType.BOTH;

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

    public RenderType getCurrentRenderType() {
        return currentRenderType;
    }

    public void setCurrentRenderType(RenderType currentRenderType) {
        this.currentRenderType = currentRenderType;
    }

    public void renderScene(Scene scene) {
        final Camera selectedCamera = scene.getCurrentCamera();

        zBuffer.clear();
        ctx.setFill(Color.WHITE);
        ctx.fillRect(0, 0, getScreenWidth(), getScreenHeight());

        //TODO Тут будет что-то для освещения - @Fiecher

        for (Model model : scene.getModels()) {
            renderModel(selectedCamera, model);
        }

    }

    public void renderModel(Camera camera, Model model) {
        Rasterisation rasterisator = new Rasterisation(ctx.getPixelWriter(), zBuffer);
        Texture texture = model.getTexture();
        List<Vertex> vertices = model.getVertices();
        List<Polygon> polygons = model.getPolygons();

        ModelTransform modelTransform = model.getTransform();
        CameraTransform cameraTransform = camera.getCameraTransform();
        ScreenTransform screenTransform = camera.getScreenTransform();
        GeneralTransformation fin = screenTransform.combine(cameraTransform.combine(modelTransform));

        List<Vector4> vectorVertices = new ArrayList<>();
        List<Vector4> vectorNewVertices;
        for (Vertex vertex : vertices) {
            vectorVertices.add(vertex.getValues());
        }

        vectorNewVertices = fin.transform(vectorVertices);
        int w = getScreenWidth();
        int h = getScreenHeight();

        for (Polygon polygon : polygons) {
            List<Point3D> pointsList = new ArrayList<>();

            for (Integer index : polygon.getVertexIndices()) {
                Vector4 vertex = vectorNewVertices.get(index);

                for (int j = 0; j < 4; j++) {
                    vertex.set(j, vertex.get(j) / vertex.w());
                }
                pointsList.add(new Point3D(
                        vertex.x() * (w - 1) / 2 + (double) (w - 1) / 2,
                        vertex.y() * (1 - h) / 2 + (double) (h - 1) / 2,
                        vertex.z()
                ));
            }
            List<Integer> tvIndecies = new ArrayList<>();
            tvIndecies.add(polygon.getTextureVertexIndices().get(0));
            tvIndecies.add(polygon.getTextureVertexIndices().get(1));
            tvIndecies.add(polygon.getTextureVertexIndices().get(2));

            rasterisator.draw(pointsList.get(0), pointsList.get(1), pointsList.get(2), tvIndecies, texture, getCurrentRenderType());
        }

    }
}
