package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import io.github.alphameo.linear_algebra.vec.Vec3;
import io.github.alphameo.linear_algebra.vec.Vector3;

public class ScalarProjection extends GeneralTransformation {

    private Vector3 vx;
    private Vector3 vy;
    private Vector3 vz;

    // TODO: В чем проблема - Миша
//    public ScalarProjection(){
//        new ScalarProjection(new Vec3(1, 0, 0), new Vec3(0, 1, 0), new Vec3(0, 0, 1));
//    }
    public ScalarProjection() {
        this.vx = new Vec3(1, 0, 0);
        this.vy = new Vec3(0, 1, 0);
        this.vz = new Vec3(0, 0, 1);
        recalculateMatrix();
    }

    public ScalarProjection(Vector3 vx, Vector3 vy, Vector3 vz) {
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        recalculateMatrix();
    }
    private void recalculateMatrix(){
        setMatrix(new Mat4(new float[][]{
                {vx.x(), vx.y(), vx.z(), 0},
                {vy.x(), vy.y(), vy.z(), 0},
                {vz.x(), vz.y(), vz.z(), 0},
                {0, 0, 0, 1}
        }));
    }
    public Vector3 getVx() {
        return vx;
    }

    public void setVx(Vector3 vx) {
        this.vx = vx;
        recalculateMatrix();
    }

    public Vector3 getVy() {
        return vy;
    }

    public void setVy(Vector3 vy) {
        this.vy = vy;
        recalculateMatrix();
    }

    public Vector3 getVz() {
        return vz;
    }

    public void setVz(Vector3 vz) {
        this.vz = vz;
        recalculateMatrix();
    }

}
