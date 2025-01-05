package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4Math;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import io.github.alphameo.linear_algebra.vec.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author dd-buntar {@link https://github.com/dd-buntar}
 */
public interface Transformation {
    Matrix4 getMatrix();

    default Vector3 transform(Vector3 vec3){
        Vector4 result = Mat4Math.prod(getMatrix(), Vec3Math.toVec4(vec3));
        return new Vec3(result.x(), result.y(), result.z());
    }

    default List<Vector3> transform(List<Vector3> vector3List){
        List<Vector3> result = new ArrayList<>();
        for (Vector3 vector3: vector3List){
            result.add(transform(vector3));
        }
        return result;
    }
}
