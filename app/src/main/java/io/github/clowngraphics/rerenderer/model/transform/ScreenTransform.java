package io.github.clowngraphics.rerenderer.model.transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import io.github.clowngraphics.rerenderer.math.affine_transform.GeneralTransformation;
import io.github.clowngraphics.rerenderer.math.affine_transform.Transformation;
import io.github.clowngraphics.rerenderer.model.camera.CameraProperties;

public class ScreenTransform extends GeneralTransformation {
    private CameraProperties properties;

    public ScreenTransform(CameraProperties cameraProperties) {
        properties = cameraProperties;
        recalculateMatrix();
    }

    public ScreenTransform() {
        properties = new CameraProperties();
        recalculateMatrix();
    }


    public void setProperties(CameraProperties properties) {
        this.properties = properties;
        recalculateMatrix();
    }
    private void recalculateMatrix(){
        float fov = properties.getFov();
        float ar = properties.getAr();
        float f = properties.getF();
        float n = properties.getN();
        float tanFov = (float) Math.tan(fov);
        float invTanFov = 1 / tanFov;
        setMatrix(new Mat4(new float[][]{
                {invTanFov, 0, 0, 0},
                {0, invTanFov / ar, 0, 0},
                {0, 0, (f + n) / (f - n), (2 * f * n) / (n - f)},
                {0, 0, 1, 0}
        }));
    }
}
