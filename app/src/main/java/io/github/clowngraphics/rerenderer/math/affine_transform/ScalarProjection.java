package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import io.github.alphameo.linear_algebra.vec.Vector3;

public class ScalarProjection implements Transformation {

    private Matrix4 transformationMatrix;
    private Vector3 vx;
    private Vector3 vy;
    private Vector3 vz;

    public ScalarProjection(Vector3 vx, Vector3 vy, Vector3 vz) {
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        recalculateMatrix();
    }
    private void recalculateMatrix(){
        transformationMatrix = new Mat4(new float[][]{
                {vx.x(), vx.y(), vx.z(), 0},
                {vy.x(), vy.y(), vy.z(), 0},
                {vz.x(), vz.y(), vz.z(), 0},
                {0, 0, 0, 1}
        });
    }
    @Override
    public Matrix4 getMatrix() {
        return transformationMatrix;
    }
    public Vector3 getVx() {
        return vx;
    }

    public void setVx(Vector3 vx) {
        this.vx = vx;
    }

    public Vector3 getVy() {
        return vy;
    }

    public void setVy(Vector3 vy) {
        this.vy = vy;
    }

    public Vector3 getVz() {
        return vz;
    }

    public void setVz(Vector3 vz) {
        this.vz = vz;
    }

}
