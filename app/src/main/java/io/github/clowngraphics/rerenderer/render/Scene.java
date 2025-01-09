package io.github.clowngraphics.rerenderer.render;


import io.github.clowngraphics.rerenderer.model.camera.Camera;
import io.github.clowngraphics.rerenderer.model.Model;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private List<Camera> cameras = new ArrayList<>();
    private List<Model> models = new ArrayList<>();

    private int currentCameraIndex = 0;

    private final Illumination illumination = new Illumination(0);

    public Camera getCamera(int index) {
        return cameras.get(index);
    }

    public Camera getCurrentCamera() {
        return cameras.get(currentCameraIndex);
    }

    public void setCurrentCameraIndex(int currentCameraIndex) {
        this.currentCameraIndex = currentCameraIndex;
    }

    public void setCamera(Camera camera, int index) {
        this.cameras.set(index, camera);
    }

    public void setModels(Model model, int index) {
        this.models.set(index, model);
    }

    public List<Model> getModels() {
        return models;
    }
}
