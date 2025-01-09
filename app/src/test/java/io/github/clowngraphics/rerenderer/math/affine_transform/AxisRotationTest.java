package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class AxisRotationTest {
    @Test
    void rotateXTest() {
        AxisRotation axisRotation = new AxisRotation(Axis.X);
        Matrix4 assertion;
        Matrix4 before = axisRotation.getMatrix();
        assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        });
        Assertions.assertEquals(assertion, before);

        axisRotation.rotate((float) Math.PI/2);
        Matrix4 after = axisRotation.getMatrix();
        assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 0, 1, 0},
                {0, -1, 0, 0},
                {0, 0, 0, 1},
        });
        Assertions.assertEquals(assertion, after);
    }

    @Test
    void rotateYTest() {
        AxisRotation axisRotation = new AxisRotation(Axis.Y);
        Matrix4 assertion;
        Matrix4 before = axisRotation.getMatrix();
        assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        });
        Assertions.assertEquals(assertion, before);

        axisRotation.rotate((float) Math.PI/2);
        Matrix4 after = axisRotation.getMatrix();
        assertion = new Mat4(new float[][]{
                {0, 0, 1, 0},
                {0, 1, 0, 0},
                {-1, 0, 0, 0},
                {0, 0, 0, 1},
        });
        Assertions.assertEquals(assertion, after);
    }
    @Test
    void rotateZTest() {
        AxisRotation axisRotation = new AxisRotation(Axis.Z);
        Matrix4 assertion;
        Matrix4 before = axisRotation.getMatrix();
        assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        });
        Assertions.assertEquals(assertion, before);

        axisRotation.rotate((float) Math.PI/2);
        Matrix4 after = axisRotation.getMatrix();
        assertion = new Mat4(new float[][]{
                {0, 1, 0, 0},
                {-1, 0, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        });
        Assertions.assertEquals(assertion, after);
    }
}
