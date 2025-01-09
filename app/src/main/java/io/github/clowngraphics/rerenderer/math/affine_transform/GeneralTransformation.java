package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Mat4Math;
import io.github.alphameo.linear_algebra.mat.Matrix4;

import java.util.ArrayList;
import java.util.List;

public class GeneralTransformation implements Transformation{
    private Mat4 transformationMatrix;

    public GeneralTransformation() {
    }

    public GeneralTransformation(Matrix4 transformationMatrix) {
        this.transformationMatrix = (Mat4) transformationMatrix;
    }

    public GeneralTransformation combine(GeneralTransformation other){
        GeneralTransformation result =  new GeneralTransformation();
        result.setMatrix(Mat4Math.prod(getMatrix(), other.getMatrix()));
        return result;
    }
    @Override
    public Mat4 getMatrix() {
        return transformationMatrix;
    }

    protected void setMatrix(Matrix4 matrix) {
        transformationMatrix = (Mat4) matrix;
    }
}
