package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
