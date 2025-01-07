package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;

public class Scale extends GeneralTransformation {

    public Scale() {
        super(new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        }));
    }

    public Scale(float sx, float sy, float sz) {
        super(new Mat4(new float[][]{
                {sx, 0, 0, 0},
                {0, sy, 0, 0},
                {0, 0, sz, 0},
                {0, 0, 0, 1}
        }));
    }

    public void scale(float s, Axis axis){
        switch (axis){
            case X -> scaleX(s);
            case Y -> scaleY(s);
            case Z -> scaleZ(s);
        }
    }

    public void scaleX(float sx) {
        getMatrix().set(0, 0, sx);
    }

    public void scaleY(float sy) {
        getMatrix().set(1, 1, sy);
    }

    public void scaleZ(float sz) {
        getMatrix().set(2, 2, sz);
    }
}
