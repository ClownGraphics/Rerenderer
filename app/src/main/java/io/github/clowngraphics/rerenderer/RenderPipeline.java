package io.github.clowngraphics.rerenderer;

import io.github.alphameo.linear_algebra.mat.Mat4Math;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import io.github.alphameo.linear_algebra.vec.Vector3;
import io.github.alphameo.linear_algebra.vec.Vector4;
import io.github.clowngraphics.rerenderer.math.affine_transform.GeneralTransformation;
import io.github.clowngraphics.rerenderer.model.Model;
import io.github.clowngraphics.rerenderer.model.camera.Camera;
import io.github.clowngraphics.rerenderer.model.transform.CameraTransform;
import io.github.clowngraphics.rerenderer.model.transform.ModelTransform;
import io.github.clowngraphics.rerenderer.model.transform.ScreenTransform;
import io.github.clowngraphics.rerenderer.render.*;
import io.github.clowngraphics.rerenderer.render.Polygon;
import io.github.clowngraphics.rerenderer.render.texture.Texture;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
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
    private RenderType currentRenderType = RenderType.WIREFRAME;

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

        //TODO Тут будет что-то для освещения - @Fiecher

        for (Model model : scene.getModels()) {
            renderModel(selectedCamera, model);
        }

    }

    public void renderModel(Camera camera, Model model) {

        TriangleRasterisator rasterisator = new TriangleRasterisator(ctx.getPixelWriter(), zBuffer);
        Texture texture = model.getTexture();
        List<Vertex> vertices = model.getVertices();

        ModelTransform modelTransform = model.getTransform();
        CameraTransform cameraTransform = camera.getCameraTransform();
        ScreenTransform screenTransform = camera.getScreenTransform();
        GeneralTransformation fin = screenTransform.combine(cameraTransform.combine(modelTransform));

        List<Vector4> vectorVertices = new ArrayList<>();
        List<Vector4> vectorNewVertices;

        for(Vertex vertex: vertices){
            vectorVertices.add(vertex.getValues());
        }
        vectorNewVertices = fin.transform(vectorVertices);
//        float w = screenTransform.getMatrix().get(2, 3);

        for(Vector4 vertex: vectorNewVertices){
            for(int i = 0; i < 4; i++){
                vertex.set(i, vertex.get(i) / vertex.w());
            }
            Point3D points[] = new Point3D[3];
            for (int i = 0; i < 3; i++) {
                points[i] = new Point3D(vectorNewVertices.get(i).x()*getScreenWidth(), vectorNewVertices.get(i).y()*getScreenHeight(), vectorNewVertices.get(i).z());
//                System.out.println(points[i].getX() + " " + points[i].getY());
            }

            rasterisator.draw(points[0], points[1], points[2], texture, getCurrentRenderType());
        }

    }

    private void lookAt(Camera camera) {

    }


}
