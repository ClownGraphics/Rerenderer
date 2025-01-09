package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TranslationTest {
    @Test
    void zeroTranslationTest() {
        Translation translate = new Translation();
        Matrix4 assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
        translate.translateX(0);
        translate.translateY(0);
        translate.translateZ(0);
        assertEquals(assertion, translate.getMatrix());
    }

    @Test
    void translateXTest() {
        Translation translate = new Translation();
        int amount = 5;
        Matrix4 assertion = new Mat4(new float[][]{
                {1, 0, 0, amount},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
        translate.translate(amount, Axis.X);
        assertEquals(assertion, translate.getMatrix());
    }

    @Test
    void translateYTest() {
        Translation translate = new Translation();
        int amount = 5;
        Matrix4 assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, amount},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
        translate.translate(amount, Axis.Y);
        assertEquals(assertion, translate.getMatrix());
    }

    @Test
    void translateZTest() {
        Translation translate = new Translation();
        int amount = 5;
        Matrix4 assertion = new Mat4(new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, amount},
                {0, 0, 0, 1}
        });
        translate.translate(amount, Axis.Z);
        assertEquals(assertion, translate.getMatrix());
    }

    @Test
    void negativeTranslationTest() {
        Translation translate = new Translation();
        Matrix4 assertion = new Mat4(new float[][]{
                {1, 0, 0, -1},
                {0, 1, 0, -1},
                {0, 0, 1, -1},
                {0, 0, 0, 1}
        });
        translate.translateX(-1);
        translate.translateY(-1);
        translate.translateZ(-1);
        assertEquals(assertion, translate.getMatrix());
    }

    @Test
    void translateXMethodsEquivalenceTest() {
        Translation translate;
        int amount = 5;

        translate = new Translation();
        translate.translateX(amount);
        Matrix4 translate1 = translate.getMatrix();

        translate = new Translation();
        translate.translate(amount, Axis.X);
        Matrix4 translate2 = translate.getMatrix();
        assertEquals(translate1, translate2);
    }
    @Test
    void translateYMethodsEquivalenceTest() {
        Translation translate;
        int amount = 5;

        translate = new Translation();
        translate.translateY(amount);
        Matrix4 translate1 = translate.getMatrix();

        translate = new Translation();
        translate.translate(amount, Axis.Y);
        Matrix4 translate2 = translate.getMatrix();
        assertEquals(translate1, translate2);
    }
    @Test
    void translateZMethodsEquivalenceTest() {
        Translation translate;
        int amount = 5;

        translate = new Translation();
        translate.translateZ(amount);
        Matrix4 translate1 = translate.getMatrix();

        translate = new Translation();
        translate.translate(amount, Axis.Z);
        Matrix4 translate2 = translate.getMatrix();
        assertEquals(translate1, translate2);
    }
    
}
