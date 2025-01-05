package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4Math;
import io.github.alphameo.linear_algebra.mat.Matrix4;




public class GeneralRotation implements Transformation {
    private final Matrix4 transformationMatrix;
    private AxisRotation first;
    private AxisRotation second;
    private AxisRotation third;
    private RotationOrder rotationOrder;

    public GeneralRotation(){
        this.rotationOrder = rotationOrder;
        AxisRotation x= new AxisRotation(0, Axis.X);
        AxisRotation y= new AxisRotation(0, Axis.Y);
        AxisRotation z= new AxisRotation(0, Axis.Z);
        switch (rotationOrder){
            case XYZ -> setRotations(x, y, z);
            case XZY -> setRotations(x, z, y);
            case YXZ -> setRotations(y, x, z);
            case YZX -> setRotations(y, z, x);
            case ZXY -> setRotations(z, x, y);
            case ZYX -> setRotations(z, y, x);
        }
        transformationMatrix = Mat4Math.prod(Mat4Math.prod(first.getMatrix(), second.getMatrix()), third.getMatrix());
    }
    public GeneralRotation(float rx, float ry, float rz, RotationOrder rotationOrder){
        this.rotationOrder = rotationOrder;
        AxisRotation x= new AxisRotation(rx, Axis.X);
        AxisRotation y= new AxisRotation(ry, Axis.Y);
        AxisRotation z= new AxisRotation(rz, Axis.Z);
        switch (rotationOrder){
            case XYZ -> setRotations(x, y, z);
            case XZY -> setRotations(x, z, y);
            case YXZ -> setRotations(y, x, z);
            case YZX -> setRotations(y, z, x);
            case ZXY -> setRotations(z, x, y);
            case ZYX -> setRotations(z, y, x);
        }
        transformationMatrix = Mat4Math.prod(Mat4Math.prod(first.getMatrix(), second.getMatrix()), third.getMatrix());
    }

    private void setRotations(AxisRotation first, AxisRotation second, AxisRotation third){
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public void rotate(float angle, Axis axis){

    }

    @Override
    public Matrix4 getMatrix() {
        return transformationMatrix;
    }
}
