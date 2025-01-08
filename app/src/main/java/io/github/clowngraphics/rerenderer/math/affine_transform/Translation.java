package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;

public class Translation extends GeneralTransformation {

    public Translation() {
        super(new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        }));
    }

    public Translation(float dx, float dy, float dz) {
        super(new Mat4(new float[][]{
                {1, 0, 0, dx},
                {0, 1, 0, dy},
                {0, 0, 1, dz},
                {0, 0, 0, 1}
        }));
    }

    public void translate(float dt, Axis axis) {
        switch (axis) {
            case X -> translateX(dt);
            case Y -> translateY(dt);
            case Z -> translateZ(dt);
        }
    }

    public void translateX(float dx) {
        getMatrix().set(0, 3, dx);
    }

    public void translateY(float dy) {
        getMatrix().set(1, 3, dy);
    }

    public void translateZ(float dz) {
        getMatrix().set(2, 3, dz);
    }

}
