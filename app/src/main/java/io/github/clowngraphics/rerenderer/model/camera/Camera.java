package io.github.clowngraphics.rerenderer.model.camera;

import io.github.alphameo.linear_algebra.vec.Vec;
import io.github.alphameo.linear_algebra.vec.Vec3;
import io.github.alphameo.linear_algebra.vec.Vec3Math;
import io.github.alphameo.linear_algebra.vec.Vector3;
import io.github.clowngraphics.rerenderer.math.affine_transform.ScalarProjection;
import io.github.clowngraphics.rerenderer.model.transform.CameraTransform;
import io.github.clowngraphics.rerenderer.model.transform.ScreenTransform;

public class Camera {
    CameraTransform cameraTransform;
    ScreenTransform screenTransform;

    private Vector3 up = new Vec3(0,1, 0);
    private Vector3 eye;
    private Vector3 target = new Vec3(0,0, 1);
    private Vector3 xAxis;
    private Vector3 yAxis;
    private Vector3 zAxis;
    private float fov;
    private float ar;
    private float f;
    private float n;

    public Camera(Vector3 eye) {
        this.eye = eye;
        updateVectors();
    }

    public Camera(Vector3 eye, Vector3 target) {
        this.eye = eye;
        this.target = target;
        updateVectors();
    }

    public Camera(Vector3 up, Vector3 eye, Vector3 target) {
        this.up = up;
        this.eye = eye;
        this.target = target;
        updateVectors();
    }

    private void updateVectors(){
        zAxis = Vec3Math.sub(target, eye);
        xAxis = Vec3Math.cross(up, zAxis);
        yAxis = Vec3Math.cross(zAxis, xAxis);
        xAxis = Vec3Math.normalize(xAxis);
        yAxis = Vec3Math.normalize(yAxis);
        zAxis = Vec3Math.normalize(zAxis);

    }
}
