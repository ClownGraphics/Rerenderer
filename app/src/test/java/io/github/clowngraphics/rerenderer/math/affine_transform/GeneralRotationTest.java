package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Mat4Math;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GeneralRotationTest {
    @Test
    void rotateXTest() {
        GeneralRotation generalRotation = new GeneralRotation();
        Matrix4 assertion;
        Matrix4 before = generalRotation.getMatrix();
        assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        });
        assertEquals(assertion, before);

        generalRotation.rotate((float) Math.PI / 2, Axis.X);
        Matrix4 after = generalRotation.getMatrix();
        assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 0, 1, 0},
                {0, -1, 0, 0},
                {0, 0, 0, 1},
        });
        assertEquals(assertion, after);
    }

    @Test
    void rotateYTest() {
        GeneralRotation generalRotation = new GeneralRotation();
        Matrix4 assertion;
        Matrix4 before = generalRotation.getMatrix();
        assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        });
        assertEquals(assertion, before);

        generalRotation.rotate((float) Math.PI / 2, Axis.Y);
        Matrix4 after = generalRotation.getMatrix();
        assertion = new Mat4(new float[][]{
                {0, 0, 1, 0},
                {0, 1, 0, 0},
                {-1, 0, 0, 0},
                {0, 0, 0, 1},
        });
        assertEquals(assertion, after);
    }

    @Test
    void rotateZTest() {
        GeneralRotation generalRotation = new GeneralRotation();
        Matrix4 assertion;
        Matrix4 before = generalRotation.getMatrix();
        assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        });
        assertEquals(assertion, before);

        generalRotation.rotate((float) Math.PI / 2, Axis.Z);
        Matrix4 after = generalRotation.getMatrix();
        assertion = new Mat4(new float[][]{
                {0, 1, 0, 0},
                {-1, 0, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        });
        assertEquals(assertion, after);
    }


    @Test
    void setAngleXTest() {
        GeneralRotation generalRotation = new GeneralRotation();
        Matrix4 assertion;
        Matrix4 before = generalRotation.getMatrix();
        assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        });
        assertEquals(assertion, before);

        generalRotation.setAngle((float) Math.PI / 2, Axis.X);
        Matrix4 after = generalRotation.getMatrix();
        assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 0, 1, 0},
                {0, -1, 0, 0},
                {0, 0, 0, 1},
        });
        assertEquals(assertion, after);
    }

    @Test
    void setAngleYTest() {
        GeneralRotation generalRotation = new GeneralRotation();
        Matrix4 assertion;
        Matrix4 before = generalRotation.getMatrix();
        assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        });
        assertEquals(assertion, before);

        generalRotation.setAngle((float) Math.PI / 2, Axis.Y);
        Matrix4 after = generalRotation.getMatrix();
        assertion = new Mat4(new float[][]{
                {0, 0, 1, 0},
                {0, 1, 0, 0},
                {-1, 0, 0, 0},
                {0, 0, 0, 1},
        });
        assertEquals(assertion, after);
    }

    @Test
    void setAngleZTest() {
        GeneralRotation generalRotation = new GeneralRotation();
        Matrix4 assertion;
        Matrix4 before = generalRotation.getMatrix();
        assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        });
        assertEquals(assertion, before);

        generalRotation.setAngle((float) Math.PI / 2, Axis.Z);
        Matrix4 after = generalRotation.getMatrix();
        assertion = new Mat4(new float[][]{
                {0, 1, 0, 0},
                {-1, 0, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        });
        assertEquals(assertion, after);
    }

    @Test
    void zeroRotationXTest() {
        GeneralRotation generalRotation = new GeneralRotation();
        generalRotation.rotate(0, Axis.X);
        Matrix4 assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        });
        assertEquals(assertion, generalRotation.getMatrix());
    }

    @Test
    void zeroRotationYTest() {
        GeneralRotation generalRotation = new GeneralRotation();
        generalRotation.rotate(0, Axis.Y);
        Matrix4 assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        });
        assertEquals(assertion, generalRotation.getMatrix());
    }
    @Test
    void zeroRotationZTest() {
        GeneralRotation generalRotation = new GeneralRotation();
        generalRotation.rotate(0, Axis.Z);
        Matrix4 assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        });
        assertEquals(assertion, generalRotation.getMatrix());
    }

    @Test
    void sequentialRotationsTest() {
        GeneralRotation generalRotation = new GeneralRotation();
        generalRotation.rotate((float) (Math.PI / 2), Axis.X);
        generalRotation.rotate((float) (Math.PI / 2), Axis.Y);

        AxisRotation xRotation = new AxisRotation(Axis.X);
        xRotation.rotate((float) (Math.PI / 2));

        AxisRotation yRotation = new AxisRotation(Axis.Y);
        yRotation.rotate((float) (Math.PI / 2));

        Matrix4 xyRotation = Mat4Math.prod(yRotation.getMatrix(), xRotation.getMatrix());
        Matrix4 yxRotation = Mat4Math.prod(xRotation.getMatrix(), yRotation.getMatrix());
        assertEquals(xyRotation, generalRotation.getMatrix());
        assertNotEquals(yxRotation, generalRotation.getMatrix());
    }

    @Test
    void rotationOrderTest() {
        GeneralRotation generalRotation = new GeneralRotation(RotationOrder.YXZ);
        generalRotation.rotate((float) (Math.PI / 2), Axis.X);
        generalRotation.rotate((float) (Math.PI / 2), Axis.Y);

        AxisRotation xRotation = new AxisRotation(Axis.X);
        xRotation.rotate((float) (Math.PI / 2));

        AxisRotation yRotation = new AxisRotation(Axis.Y);
        yRotation.rotate((float) (Math.PI / 2));

        Matrix4 xyRotation = Mat4Math.prod(yRotation.getMatrix(), xRotation.getMatrix());
        Matrix4 yxRotation = Mat4Math.prod(xRotation.getMatrix(), yRotation.getMatrix());
        assertEquals(yxRotation, generalRotation.getMatrix());
        assertNotEquals(xyRotation, generalRotation.getMatrix());
    }
}
