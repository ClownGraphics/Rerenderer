package io.github.clowngraphics.rerenderer.render.texture;

import io.github.alphameo.linear_algebra.vec.Vec2;
import io.github.alphameo.linear_algebra.vec.Vector2;
import io.github.clowngraphics.rerenderer.math.Barycentric;
import io.github.shimeoki.jshaper.obj.TextureVertex;

public class TriangleUVCoordinates {

    private final Vector2 uv1;

    private final Vector2 uv2;

    private final Vector2 uv3;


    public TriangleUVCoordinates(Vector2 uv1, Vector2 uv2, Vector2 uv3) {
        if (!checkCoordinateRange(uv1.x(), uv1.y())) {
            throw new IllegalArgumentException("Coordinates for uv1 are not normalized");
        }
        this.uv1 = uv1;

        if (!checkCoordinateRange(uv2.x(), uv2.y())) {
            throw new IllegalArgumentException("Coordinates for uv2 are not normalized");
        }
        this.uv2 = uv2;

        if (!checkCoordinateRange(uv3.x(), uv3.y())) {
            throw new IllegalArgumentException("Coordinates for uv3 are not normalized");
        }
        this.uv3 = uv3;
    }

    private boolean checkCoordinateRange(float x, float y) {
        return x >= 0 && x <= 1 && y >= 0 && y <= 1;
    }

    public Vector2 barycentric(final Barycentric b) {
        float u = (float) (getU1() * b.getLambda1() + getU2() * b.getLambda2() + getU3() * b.getLambda3());
        float v = (float) (getV1() * b.getLambda1() + getV2() * b.getLambda2() + getV3() * b.getLambda3());

        return new Vec2(Math.max(0, Math.min(u, 1)), Math.max(0, Math.min(v, 1)));
    }

    public float getU1() {
        return uv1.x();
    }

    public float getV1() {
        return uv1.y();
    }

    public float getU2() {
        return uv2.x();
    }

    public float getV2() {
        return uv2.y();
    }

    public float getU3() {
        return uv3.x();
    }

    public float getV3() {
        return uv3.y();
    }

    public TriangleUVCoordinates convertToUV(TextureVertex tv1, TextureVertex tv2, TextureVertex tv3){
        return new TriangleUVCoordinates(new Vec2(tv1.u(),tv1.u()), new Vec2(tv2.u(),tv2.u()), new Vec2(tv3.u(),tv3.u()));
    }
}
