package io.github.clowngraphics.rerenderer.model.camera;

import io.github.alphameo.linear_algebra.vec.Vec;
import io.github.alphameo.linear_algebra.vec.Vec3;
import io.github.alphameo.linear_algebra.vec.Vec3Math;
import io.github.alphameo.linear_algebra.vec.Vector3;
import io.github.clowngraphics.rerenderer.math.affine_transform.Axis;
import io.github.clowngraphics.rerenderer.math.affine_transform.ScalarProjection;
import io.github.clowngraphics.rerenderer.model.Object;
import io.github.clowngraphics.rerenderer.model.transform.CameraTransform;
import io.github.clowngraphics.rerenderer.model.transform.ModelTransform;
import io.github.clowngraphics.rerenderer.model.transform.ScreenTransform;


//todo: это что-то на криворуком, переделать
// - делать систему управления положения камерой и моделью наследуя от одного интерфейса видимо было ошибкой?
// - возможно преобразования камеры стоило применять самой камерой методом lookAt
public class Camera implements Object {
    CameraTransform cameraTransform = new CameraTransform();

    //TODO: решить, как лучше получать преобразованную камеру:
    //  1) Статическим классом трансформатором
    //  2) Внутренними методами
    // - Миша
    ModelTransform orientation;
    ScreenTransform screenTransform = new ScreenTransform();
    CameraProperties properties;

    private Vector3 up = new Vec3(0, 0, 1);
    private Vector3 eye;
    private Vector3 target = new Vec3(0, 0, 0);
    private Vector3 xAxis;
    private Vector3 yAxis;
    private Vector3 zAxis;


    public Camera(Vector3 eye, CameraProperties properties) {
        this.eye = eye;
        this.properties = properties;
        updateVectors();
    }

    public Camera(Vector3 eye, Vector3 target, CameraProperties properties) {
        this.eye = eye;
        this.target = target;
        this.properties = properties;
        updateVectors();
    }

    public Camera(Vector3 up, Vector3 eye, Vector3 target, CameraProperties properties) {
        this.up = up;
        this.eye = eye;
        this.target = target;
        this.properties = properties;
        updateVectors();
    }

    private void updateVectors() {
        zAxis = Vec3Math.sub(target, eye);
        xAxis = Vec3Math.cross(up, zAxis);
        yAxis = Vec3Math.cross(zAxis, xAxis);
        xAxis = Vec3Math.normalize(xAxis);
        yAxis = Vec3Math.normalize(yAxis);
        zAxis = Vec3Math.normalize(zAxis);

        //это просто ужас, но я сам виноват в этом
        cameraTransform.getTranslation().translate(-eye.x(), Axis.X);
        cameraTransform.getTranslation().translate(-eye.y(), Axis.Y);
        cameraTransform.getTranslation().translate(-eye.z(), Axis.Z);

        cameraTransform.getScalarProjection().setVx(xAxis);
        cameraTransform.getScalarProjection().setVy(yAxis);
        cameraTransform.getScalarProjection().setVz(zAxis);
        cameraTransform.recalculateMatrix();
    }
    //todo: переделать передвижение камеры
    public Vector3 getEye() {
        return eye;
    }

    public void setEye(Vector3 eye) {
        this.eye = eye;
        updateVectors();
    }

    public Vector3 getTarget() {
        return target;
    }

    public void setTarget(Vector3 target) {
        this.target = target;
        updateVectors();
    }
    //todo: lookAt()
//    public void lookAt(){}
    public CameraProperties getProperties() {
        return properties;
    }

    public CameraTransform getCameraTransform() {
        return cameraTransform;
    }

    public void setCameraTransform(CameraTransform cameraTransform) {
        this.cameraTransform = cameraTransform;
    }

    public ScreenTransform getScreenTransform() {
        return screenTransform;
    }

    public void setScreenTransform(ScreenTransform screenTransform) {
        this.screenTransform = screenTransform;
    }



    @Override
    public void scale(float s, Axis axis) {
        return;
    }

    @Override
    public ModelTransform getTransform() {
        return orientation;
    }
}
