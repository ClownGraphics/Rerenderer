package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import io.github.alphameo.linear_algebra.vec.Vec3;
import io.github.alphameo.linear_algebra.vec.Vec4;
import io.github.alphameo.linear_algebra.vec.Vector3;
import io.github.alphameo.linear_algebra.vec.Vector4;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class TransformTest {
    Transformation doNothing = new Transformation() {
        @Override
        public Matrix4 getMatrix() {
            return Mat4.unitMat();
        }
    };
    @Test
    void vector3TransformTest() {
        Vector3 vector3 = new Vec3();
        assertInstanceOf(Vector3.class, doNothing.transform(vector3));
        Vector3 result = doNothing.transform(vector3);
        assertEquals(vector3, result);
    }

    @Test
    void vector4TransformTest() {
        Vector4 vector4 = new Vec4();
        assertInstanceOf(Vector4.class, doNothing.transform(vector4));
        Vector4 result = doNothing.transform(vector4);
        assertEquals(vector4, result);
    }

    @Test
    void vector4ListTransformTest() {
        List<Vector4> vector4List = new ArrayList<>();
        vector4List.add(new Vec4());
        vector4List.add(new Vec4());
        vector4List.add(new Vec4());

        assertInstanceOf(List.class, doNothing.transform(vector4List));
        assertInstanceOf(Vector4.class, doNothing.transform(vector4List).get(0));
        assertEquals(vector4List, doNothing.transform(vector4List));
    }
}
