package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScaleTest {
    @Test
    void unitScaleTest() {
        Scale scale = new Scale();
        Matrix4 assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
        scale.scaleX(1);
        scale.scaleY(1);
        scale.scaleZ(1);
        assertEquals(assertion, scale.getMatrix());
    }

    @Test
    void scaleXTest() {
        Scale scale = new Scale();
        int amount = 5;
        Matrix4 assertion = new Mat4(new float[][]{
                {amount, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
        scale.scale(amount, Axis.X);
        assertEquals(assertion, scale.getMatrix());
    }

    @Test
    void scaleYTest() {
        Scale scale = new Scale();
        int amount = 5;
        Matrix4 assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, amount, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
        scale.scale(amount, Axis.Y);
        assertEquals(assertion, scale.getMatrix());
    }

    @Test
    void scaleZTest() {
        Scale scale = new Scale();
        int amount = 5;
        Matrix4 assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, amount, 0},
                {0, 0, 0, 1}
        });
        scale.scale(amount, Axis.Z);
        assertEquals(assertion, scale.getMatrix());
    }

    @Test
    void negativeScaleTest() {
        Scale scale = new Scale();
        Matrix4 assertion = new Mat4(new float[][]{
                {-1, 0, 0, 0},
                {0, -1, 0, 0},
                {0, 0, -1, 0},
                {0, 0, 0, 1}
        });
        scale.scaleX(-1);
        scale.scaleY(-1);
        scale.scaleZ(-1);
        assertEquals(assertion, scale.getMatrix());
    }

    @Test
    void scaleXMethodsEquivalenceTest() {
        Scale scale;
        int amount = 5;

        scale = new Scale();
        scale.scaleX(amount);
        Matrix4 scale1 = scale.getMatrix();

        scale = new Scale();
        scale.scale(amount, Axis.X);
        Matrix4 scale2 = scale.getMatrix();
        assertEquals(scale1, scale2);
    }
    @Test
    void scaleYMethodsEquivalenceTest() {
        Scale scale;
        int amount = 5;

        scale = new Scale();
        scale.scaleY(amount);
        Matrix4 scale1 = scale.getMatrix();

        scale = new Scale();
        scale.scale(amount, Axis.Y);
        Matrix4 scale2 = scale.getMatrix();
        assertEquals(scale1, scale2);
    }
    @Test
    void scaleZMethodsEquivalenceTest() {
        Scale scale;
        int amount = 5;

        scale = new Scale();
        scale.scaleZ(amount);
        Matrix4 scale1 = scale.getMatrix();

        scale = new Scale();
        scale.scale(amount, Axis.Z);
        Matrix4 scale2 = scale.getMatrix();
        assertEquals(scale1, scale2);
    }

    @Test
    void zeroScaleTest() {
        Scale scale = new Scale();
        Matrix4 assertion = new Mat4(new float[][]{
                {-1, 0, 0, 0},
                {0, -1, 0, 0},
                {0, 0, -1, 0},
                {0, 0, 0, 1}
        });
        assertThrows(IllegalArgumentException.class,
                () -> scale.scaleX(0),
                "Scaling by zero is prohibited.");
        assertThrows(IllegalArgumentException.class,
                () -> scale.scaleY(0),
                "Scaling by zero is prohibited.");
        assertThrows(IllegalArgumentException.class,
                () -> scale.scaleZ(0),
                "Scaling by zero is prohibited.");
    }
}
