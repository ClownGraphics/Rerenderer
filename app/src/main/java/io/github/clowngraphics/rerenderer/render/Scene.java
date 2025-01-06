package io.github.clowngraphics.rerenderer.render;


import io.github.clowngraphics.rerenderer.model.camera.Camera;
import io.github.clowngraphics.rerenderer.model.Model;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private Camera camera;

    private final Illumination illumination = new Illumination(0);

    private final List<Model> models = new ArrayList<>();

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public List<Model> getModels() {
        return models;
    }
}
