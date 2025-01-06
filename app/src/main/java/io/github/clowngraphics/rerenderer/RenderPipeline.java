package io.github.clowngraphics.rerenderer;

import io.github.clowngraphics.rerenderer.render.Scene;
import io.github.clowngraphics.rerenderer.render.ZBuffer;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
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
        final Camera selectedCamera = scene.getCamera();

        zBuffer.clear();
    }

    private void lookAt(Camera camera){

    }

}
