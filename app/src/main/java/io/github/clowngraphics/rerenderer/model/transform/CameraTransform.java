package io.github.clowngraphics.rerenderer.model.transform;

import io.github.alphameo.linear_algebra.mat.Mat4Math;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import io.github.clowngraphics.rerenderer.math.affine_transform.GeneralTransformation;
import io.github.clowngraphics.rerenderer.math.affine_transform.ScalarProjection;
import io.github.clowngraphics.rerenderer.math.affine_transform.Transformation;
import io.github.clowngraphics.rerenderer.math.affine_transform.Translation;

public class CameraTransform implements Transformation {
    private Matrix4 transformationMatrix;
    private ScalarProjection scalarProjection;
    private Translation translation;


    public CameraTransform() {
        this.scalarProjection = new ScalarProjection();
        this.translation = new Translation();
        recalculateMatrix();
    }

    public CameraTransform(ScalarProjection scalarProjection, Translation translation) {
        this.scalarProjection = scalarProjection;
        this.translation = translation;
        recalculateMatrix();
    }

    private void recalculateMatrix(){
        transformationMatrix = Mat4Math.prod(scalarProjection.getMatrix(), translation.getMatrix());
    }

    public ScalarProjection getScalarProjection() {
        return scalarProjection;
    }

    public void setScalarProjection(ScalarProjection scalarProjection) {
        this.scalarProjection = scalarProjection;
        recalculateMatrix();
    }

    public Translation getTranslation() {
        return translation;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
        recalculateMatrix();
    }

    @Override
    public Matrix4 getMatrix() {
        return transformationMatrix;
    }
}
