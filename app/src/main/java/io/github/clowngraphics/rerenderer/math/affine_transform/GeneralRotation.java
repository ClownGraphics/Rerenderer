package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4Math;
import io.github.alphameo.linear_algebra.mat.Matrix4;




public class GeneralRotation extends GeneralTransformation {
    private AxisRotation first;
    private AxisRotation second;
    private AxisRotation third;
    private RotationOrder rotationOrder;
    private AxisRotation rotationX;
    private AxisRotation rotationY;
    private AxisRotation rotationZ;

    public GeneralRotation(){
        this.rotationOrder = rotationOrder;
        rotationX= new AxisRotation(0, Axis.X);
        rotationY = new AxisRotation(0, Axis.Y);
        rotationZ = new AxisRotation(0, Axis.Z);
        switch (rotationOrder){
            case XYZ -> setRotations(rotationX, rotationY, rotationZ);
            case XZY -> setRotations(rotationX, rotationZ, rotationY);
            case YXZ -> setRotations(rotationY, rotationX, rotationZ);
            case YZX -> setRotations(rotationY, rotationZ, rotationX);
            case ZXY -> setRotations(rotationZ, rotationX, rotationY);
            case ZYX -> setRotations(rotationZ, rotationY, rotationX);
        }
        recalculateMatrix();
    }
    public GeneralRotation(float rx, float ry, float rz, RotationOrder rotationOrder){
        this.rotationOrder = rotationOrder;
        rotationX= new AxisRotation(rx, Axis.X);
        rotationY = new AxisRotation(ry, Axis.Y);
        rotationZ = new AxisRotation(rz, Axis.Z);
        switch (rotationOrder){
            case XYZ -> setRotations(rotationX, rotationY, rotationZ);
            case XZY -> setRotations(rotationX, rotationZ, rotationY);
            case YXZ -> setRotations(rotationY, rotationX, rotationZ);
            case YZX -> setRotations(rotationY, rotationZ, rotationX);
            case ZXY -> setRotations(rotationZ, rotationX, rotationY);
            case ZYX -> setRotations(rotationZ, rotationY, rotationX);
        }
        recalculateMatrix();
    }

    private void setRotations(AxisRotation first, AxisRotation second, AxisRotation third){
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public void rotate(float angle, Axis axis){
        switch (axis){
            case X -> rotationX.rotate(angle);
            case Y -> rotationY.rotate(angle);
            case Z -> rotationZ.rotate(angle);
        }
        recalculateMatrix();
    }

    public void rotate(float rx, float ry, float rz){
        rotationX.rotate(rx);
        rotationY.rotate(ry);
        rotationZ.rotate(rz);
        recalculateMatrix();
    }

    public void setAngle(float angle, Axis axis){
        switch (axis){
            case X -> rotationX.setAngle(angle);
            case Y -> rotationY.setAngle(angle);
            case Z -> rotationZ.setAngle(angle);
        }
        recalculateMatrix();
    }

    public void setAngle(float rx, float ry, float rz){
        rotationX.setAngle(rx);
        rotationY.setAngle(ry);
        rotationZ.setAngle(rz);
        recalculateMatrix();
    }
    private void recalculateMatrix(){
        setMatrix(Mat4Math.prod(Mat4Math.prod(first.getMatrix(), second.getMatrix()), third.getMatrix()));
    }
}
