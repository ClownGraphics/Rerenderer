package io.github.clowngraphics.rerenderer.render;


import io.github.clowngraphics.rerenderer.model.camera.Camera;
import io.github.clowngraphics.rerenderer.model.Model;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private List<Camera> cameras = new ArrayList<>();

    private int currentCameraIndex = 0;

    private final Illumination illumination = new Illumination(0);

    private final List<Model> models = new ArrayList<>();

    public Camera getCamera(int index) {
        return cameras.get(index);
    }

    public Camera getCurrentCamera(){ return cameras.get(currentCameraIndex);}

    public void setCurrentCameraIndex(int currentCameraIndex) {
        this.currentCameraIndex = currentCameraIndex;
    }

    public void setCamera(Camera camera, int index) {
        this.cameras.set(index, camera);
    }

    public List<Model> getModels() {
        return models;
    }
}
