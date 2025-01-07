package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;

public class Translation implements Transformation{
    private final Matrix4 transformationMatrix;


    public Translation() {
        this.transformationMatrix = new Mat4(new float[][] {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
    }

    public Translation(float dx, float dy, float dz) {
        this.transformationMatrix = new Mat4(new float[][] {
                {1, 0, 0, dx},
                {0, 1, 0, dy},
                {0, 0, 1, dz},
                {0, 0, 0, 1}
        });
    }
    public void translate(float dt, Axis axis){
        switch (axis){
            case X -> translateX(dt);
            case Y -> translateY(dt);
            case Z -> translateZ(dt);
        }
    }
    
    public void translateX(float dx){
        transformationMatrix.set(0,0, dx);
    }

    public void translateY(float dy){
        transformationMatrix.set(1,1, dy);
    }
    public void translateZ(float dz){
        transformationMatrix.set(2,2, dz);
    }
    @Override
    public Matrix4 getMatrix() {
        return transformationMatrix;
    }
}
