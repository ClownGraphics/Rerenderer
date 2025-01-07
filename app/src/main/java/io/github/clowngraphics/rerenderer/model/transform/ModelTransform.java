package io.github.clowngraphics.rerenderer.model.transform;

import io.github.alphameo.linear_algebra.mat.Mat4Math;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import io.github.clowngraphics.rerenderer.math.affine_transform.*;
import io.github.clowngraphics.rerenderer.model.Model;

public class ModelTransform implements Transformation {
    private Matrix4 transformationMatrix;
    private GeneralRotation generalRotation;
    private Translation translation;
    private Scale scale;

    public ModelTransform(GeneralRotation generalRotation, Translation translation, Scale scale) {
        this.generalRotation = generalRotation;
        this.translation = translation;
        this.scale = scale;
    }

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

    public GeneralRotation getGeneralRotation() {
        return generalRotation;
    }

    public void setGeneralRotation(GeneralRotation generalRotation) {
        this.generalRotation = generalRotation;
        recalculateMatrix();
    }

    public Translation getTranslation() {
        return translation;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
        recalculateMatrix();
    }

    public Scale getScale() {
        return scale;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
        recalculateMatrix();
    }

    @Override
    public Matrix4 getMatrix() {
        return transformationMatrix;
    }
}
