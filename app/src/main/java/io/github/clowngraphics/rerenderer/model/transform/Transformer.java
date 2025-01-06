package io.github.clowngraphics.rerenderer.model.transform;

import io.github.clowngraphics.rerenderer.model.Camera;
import io.github.clowngraphics.rerenderer.model.Model;

public class Transformer {
    private Model model;
    private Camera camera;

    public Transformer(Model model, Camera camera) {
        this.model = model;
        this.camera = camera;
    }
    //todo: а преобразования применяются только к вершинам, или ещё к нормалям, или как...
    public void transform(){

    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
