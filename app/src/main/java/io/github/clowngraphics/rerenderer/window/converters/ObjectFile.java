package io.github.clowngraphics.rerenderer.window.converters;

import io.github.alphameo.linear_algebra.vec.Vec2;
import io.github.alphameo.linear_algebra.vec.Vec3;
import io.github.clowngraphics.rerenderer.render.Polygon;
import io.github.clowngraphics.rerenderer.render.Vertex;

import java.util.*;

public final class ObjectFile {

    private final List<Vertex> vertices;
    private final List<Vec2> textureVertices;
    private final List<Vec3> normals;
    private final List<Polygon> polygons;

    public ObjectFile(
            List<Vertex> vertices,
            List<Vec2> textureVertices,
            List<Vec3> normals,
            List<Polygon> polygons) {

        this.vertices = Objects.requireNonNull(vertices);
        this.textureVertices = Objects.requireNonNull(textureVertices);
        this.normals = Objects.requireNonNull(normals);
        this.polygons = Objects.requireNonNull(polygons);
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Vec2> getTextureVertices() {
        return textureVertices;
    }

    public List<Vec3> getNormals() {
        return normals;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }
}
