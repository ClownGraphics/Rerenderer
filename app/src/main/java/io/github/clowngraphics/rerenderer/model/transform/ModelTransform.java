package io.github.clowngraphics.rerenderer.model.transform;

import io.github.alphameo.linear_algebra.mat.Mat4Math;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import io.github.clowngraphics.rerenderer.math.affine_transform.*;
import io.github.clowngraphics.rerenderer.model.Model;

public class ModelTransform implements Transformation {
    Matrix4 transformationMatrix;
    GeneralRotation generalRotation;
    Translation translation;
    Scale scale;

    public void rotate(float angle, Axis axis){
        generalRotation.rotate(angle, axis);
        recalculateMatrix();
    }
    public void scale(float ds, Axis axis){
        scale.scale(ds, axis);
        recalculateMatrix();
    }
    public void translate(float dt, Axis axis){
        translation.translate(dt, axis);
        recalculateMatrix();
    }
    private void recalculateMatrix(){
        transformationMatrix = Mat4Math.prod(Mat4Math.prod(translation.getMatrix(), generalRotation.getMatrix()), scale.getMatrix());
    }
    @Override
    public Matrix4 getMatrix() {
        return transformationMatrix;
    }
}
