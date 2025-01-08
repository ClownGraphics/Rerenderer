package io.github.clowngraphics.rerenderer.render.texture;

import io.github.alphameo.linear_algebra.vec.Vec2;
import io.github.alphameo.linear_algebra.vec.Vector2;
import io.github.clowngraphics.rerenderer.math.Barycentric;
import io.github.shimeoki.jshaper.obj.TextureVertex;

import java.util.ArrayList;
import java.util.List;

public class PolygonUVCoordinates {

    public static class UVCoordinate {
        private final float u;
        private final float v;

        public UVCoordinate(float u, float v) {
            this.u = normalizeCoordinate(u);
            this.v = normalizeCoordinate(v);
        }

        private float normalizeCoordinate(float coord) {
            return Math.max(0, Math.min(coord, 1));
        }

        public float getU() {
            return u;
        }

        public float getV() {
            return v;
        }
    }

    private final List<Vector2> uvCoordinates;

    public PolygonUVCoordinates(List<Vector2> uvCoordinates) {
        List<Vector2> normalizedUVs = new ArrayList<>();
        for (Vector2 uv : uvCoordinates) {
            normalizedUVs.add(new Vec2(normalizeCoordinate(uv.x()), normalizeCoordinate(uv.y())));
        }
        this.uvCoordinates = normalizedUVs;
    }

    private float normalizeCoordinate(float coord) {
        return Math.max(0, Math.min(coord, 1));
    }

    public static PolygonUVCoordinates fromUVCoordinates(List<UVCoordinate> uvCoordinates) {
        List<Vector2> tempCoordinates = new ArrayList<>();
        for (UVCoordinate uv : uvCoordinates) {
            tempCoordinates.add(new Vec2(uv.getU(), uv.getV()));
        }
        return new PolygonUVCoordinates(tempCoordinates);
    }

    public Vector2 barycentric(final Barycentric b) {
        if (uvCoordinates.size() != 3) {
            throw new UnsupportedOperationException("Barycentric interpolation is only supported for triangles");
        }

        float u = (float) (uvCoordinates.get(0).x() * b.getLambda1() +
                uvCoordinates.get(1).x() * b.getLambda2() +
                uvCoordinates.get(2).x() * b.getLambda3());
        float v = (float) (uvCoordinates.get(0).y() * b.getLambda1() +
                uvCoordinates.get(1).y() * b.getLambda2() +
                uvCoordinates.get(2).y() * b.getLambda3());

        return new Vec2(Math.max(0, Math.min(u, 1)), Math.max(0, Math.min(v, 1)));
    }

    public float getU(int index) {
        return uvCoordinates.get(index).x();
    }

    public float getV(int index) {
        return uvCoordinates.get(index).y();
    }

    public static PolygonUVCoordinates convertToUV(List<TextureVertex> textureVertices) {
        List<Vector2> uvList = new ArrayList<>();
        for (TextureVertex tv : textureVertices) {
            if (tv != null) {
                uvList.add(new Vec2(tv.u(), tv.v()));
            } else {
                uvList.add(new Vec2(0, 0)); // Default UV if missing
            }
        }
        return new PolygonUVCoordinates(uvList);
    }

    public PolygonUVCoordinates createSubUV(List<Integer> indices) {
        List<Vector2> subUVs = new ArrayList<>();
        for (int index : indices) {
            subUVs.add(uvCoordinates.get(index));
        }
        return new PolygonUVCoordinates(subUVs);
    }

    public static PolygonUVCoordinates fromUVList(List<UVCoordinate> uvCoordinates) {
        List<Vector2> vectorList = new ArrayList<>();
        for (UVCoordinate uv : uvCoordinates) {
            vectorList.add(new Vec2(uv.getU(), uv.getV()));
        }
        return new PolygonUVCoordinates(vectorList);
    }
}
