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

    public void setTranslation(float t, Axis axis) {
        switch (axis) {
            case X -> setTranslationX(t);
            case Y -> setTranslationY(t);
            case Z -> setTranslationZ(t);
        }
    }

    public void setTranslationX(float x) {
        getMatrix().set(0, 3, x);
    }

    public void setTranslationY(float y) {
        getMatrix().set(1, 3, y);
    }

    public void setTranslationZ(float z) {
        getMatrix().set(2, 3, z);
    }

    public void translateX(float dx) {
        getMatrix().set(0, 3, getMatrix().get(0, 3) + dx);
    }

    public void translateY(float dy) {
        getMatrix().set(1, 3, getMatrix().get(1, 3) + dy);
    }

    public void translateZ(float dz) {
        getMatrix().set(2, 3, getMatrix().get(2, 3) + dz);
    }

}
