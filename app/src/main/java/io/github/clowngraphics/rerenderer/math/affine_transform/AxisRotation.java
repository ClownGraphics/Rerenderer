package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;


public class AxisRotation implements Transformation{
    private final Mat4 transformationMatrix;
    private final Axis axis;

    public AxisRotation(float angle, Axis axis) {
        this.axis = axis;
        switch (axis){
            case X -> this.transformationMatrix = rotationX(angle);
            case Y -> this.transformationMatrix = rotationY(angle);
            case Z -> this.transformationMatrix = rotationZ(angle);
            default -> this.transformationMatrix = null;
        }
    }
    public enum Axis{
        X, Y, Z
    }

    private Mat4 rotationX(float angle){
        float cosA = (float) Math.cos(angle);
        float sinA = (float) Math.sin(angle);
        return new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
    }
    private Mat4 rotationY(float angle){
        float cosA = (float) Math.cos(angle);
        float sinA = (float) Math.sin(angle);
        return new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
    }
    private Mat4 rotationZ(float angle){
        float cosA = (float) Math.cos(angle);
        float sinA = (float) Math.sin(angle);
        return new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
    }

    public void rotate(float angle){
        switch (axis){
            case X -> this.transformationMatrix.add(rotationX(angle));
            case Y -> this.transformationMatrix.add(rotationY(angle));
            case Z -> this.transformationMatrix.add(rotationZ(angle));
        }
    }
    public void setAngle(float angle){
        this.transformationMatrix.mult(0);
        switch (axis){
            case X -> this.transformationMatrix.add(rotationX(angle));
            case Y -> this.transformationMatrix.add(rotationY(angle));
            case Z -> this.transformationMatrix.add(rotationZ(angle));
        }
    }

    @Override
    public Matrix4 getMatrix() {
        return transformationMatrix;
    }

}
