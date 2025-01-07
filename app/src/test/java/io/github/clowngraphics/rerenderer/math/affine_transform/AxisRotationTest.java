package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import org.junit.jupiter.*;
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
        Assertions.assertEquals(before, assertion);

        axisRotation.rotate((float) Math.PI);
        Matrix4 after = axisRotation.getMatrix();
        assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 0, 1, 0},
                {0, -1, 0, 0},
                {0, 0, 0, 1},
        });
        Assertions.assertEquals(after, assertion);
    }
}
