package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import io.github.alphameo.linear_algebra.vec.Vector3;

public class ScalarProjection implements Transformation {

    private Matrix4 transformationMatrix;

    public ScalarProjection(Vector3 vx, Vector3 vy, Vector3 vz) {
        transformationMatrix = new Mat4(new float[][]{
                {vx.x(), vx.y(), vx.z(), 0},
                {vy.x(), vy.y(), vy.z(), 0},
                {vz.x(), vz.y(), vz.z(), 0},
                {0, 0, 0, 1}
        });
    }

    @Override
    public Matrix4 getMatrix() {
        return transformationMatrix;
    }
}
