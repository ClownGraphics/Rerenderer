package io.github.clowngraphics.rerenderer.model.camera;

public class CameraProperties {
    private float fov;
    private float ar;
    private float f;
    private float n;

    public CameraProperties(float fov, float ar, float f, float n) {
        this.fov = fov;
        this.ar = ar;
        this.f = f;
        this.n = n;
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public float getAr() {
        return ar;
    }

    public void setAr(float ar) {
        this.ar = ar;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }

    public float getN() {
        return n;
    }

    public void setN(float n) {
        this.n = n;
    }
}
