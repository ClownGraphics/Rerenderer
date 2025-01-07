package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;


public class AxisRotation extends GeneralTransformation{
    private final Axis axis;

    public AxisRotation(Axis axis, Axis axis1) {
        this.axis = axis;
        switch (axis){
            case X -> setMatrix(rotationX(0));
            case Y -> setMatrix(rotationY(0));
            case Z -> setMatrix(rotationZ(0));
            default -> setMatrix(null);
        }
    }

    public AxisRotation(float angle, Axis axis) {
        this.axis = axis;
        switch (axis){
            case X -> setMatrix(rotationX(angle));
            case Y -> setMatrix(rotationY(angle));
            case Z -> setMatrix(rotationZ(angle));
            default -> setMatrix(null);
        }
    }


    private Mat4 rotationX(float angle){
        float cosA = (float) Math.cos(angle);
        float sinA = (float) Math.sin(angle);
        return new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, cosA, sinA, 0},
                {0, -sinA, cosA, 0},
                {0, 0, 0, 1}
        });
    }
    private Mat4 rotationY(float angle){
        float cosA = (float) Math.cos(angle);
        float sinA = (float) Math.sin(angle);
        return new Mat4(new float[][]{
                {cosA, 0, sinA, 0},
                {0, 1, 0, 0},
                {-sinA, 0, cosA, 0},
                {0, 0, 0, 1}
        });
    }
    private Mat4 rotationZ(float angle){
        float cosA = (float) Math.cos(angle);
        float sinA = (float) Math.sin(angle);
        return new Mat4(new float[][]{
                {cosA, sinA, 0, 0},
                {-sinA, cosA, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
    }

    public void rotate(float angle){
        switch (axis){
            case X -> getMatrix().add(rotationX(angle));
            case Y -> getMatrix().add(rotationY(angle));
            case Z -> getMatrix().add(rotationZ(angle));
        }
    }
    public void setAngle(float angle){
        getMatrix().mult(0);
        switch (axis){
            case X -> getMatrix().add(rotationX(angle));
            case Y -> getMatrix().add(rotationY(angle));
            case Z -> getMatrix().add(rotationZ(angle));
        }
    }


}
