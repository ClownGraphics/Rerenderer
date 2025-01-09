package io.github.clowngraphics.rerenderer.model.camera;

import io.github.alphameo.linear_algebra.mat.Mat4Math;
import io.github.alphameo.linear_algebra.vec.*;
import io.github.clowngraphics.rerenderer.math.affine_transform.Axis;
import io.github.clowngraphics.rerenderer.math.affine_transform.GeneralRotation;
import io.github.clowngraphics.rerenderer.math.affine_transform.ScalarProjection;
import io.github.clowngraphics.rerenderer.math.affine_transform.Translation;
import io.github.clowngraphics.rerenderer.model.Object;
import io.github.clowngraphics.rerenderer.model.transform.CameraTransform;
import io.github.clowngraphics.rerenderer.model.transform.ModelTransform;
import io.github.clowngraphics.rerenderer.model.transform.ScreenTransform;


//todo: это что-то на криворуком, переделать
// - делать систему управления положения камерой и моделью наследуя от одного интерфейса видимо было ошибкой?
// - возможно преобразования камеры стоило применять самой камерой методом lookAt

public class Camera /*implements Object*/ {
    CameraTransform cameraTransform = new CameraTransform();

    //TODO: решить, как лучше получать преобразованную камеру:
    //  1) Статическим классом трансформатором
    //  2) Внутренними методами
    // - Миша
    /*ModelTransform orientation = new ModelTransform();*/
    ScreenTransform screenTransform = new ScreenTransform();
    CameraProperties properties;

    private Vector3 up = new Vec3(0, -1, 0);
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
        assert getTarget() != getEye();

        // Vec3Math.sub(getTarget(), getEye()) оказывается перезаписывает getTarget()
//        zAxis = Vec3Math.sub(getTarget(), getEye());
//        zAxis = new Vec3(t.x() - e.x(), t.y() - e.y(), t.z() - e.z());

        Vector3 t = getTarget();
        Vector3 e = getEye();
        zAxis = Vec3Math.subtracted(getTarget(), getEye());
        xAxis = Vec3Math.cross(getUp(), zAxis);
        yAxis = Vec3Math.cross(zAxis, xAxis);
        xAxis = Vec3Math.normalize(xAxis);
        yAxis = Vec3Math.normalize(yAxis);
        zAxis = Vec3Math.normalize(zAxis);

        //это просто ужас, но я сам виноват в этом
        cameraTransform.getTranslation().translate(-getEye().x(), Axis.X);
        cameraTransform.getTranslation().translate(-getEye().y(), Axis.Y);
        cameraTransform.getTranslation().translate(-getEye().z(), Axis.Z);

        cameraTransform.getScalarProjection().setVx(xAxis);
        cameraTransform.getScalarProjection().setVy(yAxis);
        cameraTransform.getScalarProjection().setVz(zAxis);
        cameraTransform.recalculateMatrix();
    }
    //todo: переделать передвижение камеры

    public void translate(float dt, Axis axis) {
        Translation translation = new Translation();
        translation.translate(dt, axis);
        setEye(translation.transform(eye));
    }

    public void moveTo(float x, float y, float z) {
        setEye(new Vec3(x, y, z));
    }

    //todo: camera rotation
    public void rotateUp() {

    }

    public void rotateLeft() {

    }

    public void rotateDown() {

    }

    public void rotateRight() {

    }

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

    public Vector3 getUp() {
        return up;
    }

    public void setUp(Vector3 up) {
        this.up = up;
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


    /*Взаимодействие с интерфейсом Object:
     *   храним вектора eye, up и target
     *   при вращении/перемещении камеры они не изменяются, меняются применяемые преобразования
     *   если установить eye/up/target напрямую, то соответствующее им преобразование обнуляются*/
//    public Vector3 getEye() {
//        return orientation.transform(eye);
//    }
//
//    public void setEye(Vector3 eye) {
//        orientation.setTranslation(new Translation());
//        this.eye = eye;
//        updateVectors();
//    }
//
//    public Vector3 getTarget() {
//        return orientation.transform(target);
//    }
//
//    public void setTarget(Vector3 target) {
//        orientation.setAngle(0, Axis.X);
//        this.target = target;
//        updateVectors();
//    }
//
//    public Vector3 getUp() {
//        return orientation.transform(up);
//    }
//
//    public void setUp(Vector3 up) {
//        orientation.setAngle(0, Axis.Y);
//        this.up = up;
//        updateVectors();
//    }
//
//    @Override
//    public void scale(float s, Axis axis) {
//        return;
//    }
//
//    @Override
//    public ModelTransform getTransform() {
//        return orientation;
//    }
}
