package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;

public class Scale implements Transformation{
    private Matrix4 transformationMatrix;

    public Scale(float sx, float sy, float sz) {
        this.transformationMatrix = new Mat4(new float[][] {
                {sx, 0, 0, 0},
                {0, sy, 0, 0},
                {0, 0, sz, 0},
                {0, 0, 0, 1}
        });
    }
    public void scaleX(float sx){
        transformationMatrix.set(0,0, sx);
    }

    public void scaleY(float sy){
        transformationMatrix.set(1,1, sy);
    }
    public void scaleZ(float sz){
        transformationMatrix.set(2,2, sz);
    }
    @Override
    public Matrix4 getMatrix() {
        return transformationMatrix;
    }
}
