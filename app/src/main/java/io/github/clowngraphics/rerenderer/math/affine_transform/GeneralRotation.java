package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Matrix4;

public class GeneralRotation implements Transformation{
    private Matrix4 transformationMatrix;

    @Override
    public Matrix4 getMatrix() {
        return transformationMatrix;
    }
}
