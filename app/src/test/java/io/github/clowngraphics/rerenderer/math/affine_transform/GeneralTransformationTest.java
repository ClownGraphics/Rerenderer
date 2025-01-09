package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Mat4Math;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class GeneralTransformationTest {
    @Test
    void combineTest() {
        Scale scale = new Scale();
        GeneralRotation rotation = new GeneralRotation();
        Translation translation = new Translation();
        GeneralTransformation transformation = new GeneralTransformation();

        translation.translateX(100);
        scale.scaleX(10);
        rotation.rotate(20, Axis.X);
        scale.scaleX(2);
        scale.scaleY(3);
        rotation.rotate(0, Axis.Y);

        transformation = translation.combine(rotation).combine(scale);

        Matrix4 result = Mat4Math.prod(rotation.getMatrix(), scale.getMatrix());
        result = Mat4Math.prod(translation.getMatrix(), result);
        assertEquals(result, transformation.getMatrix());
    }
}
