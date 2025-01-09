package io.github.clowngraphics.rerenderer.window.converters;

import io.github.alphameo.linear_algebra.vec.Vec2;
import io.github.alphameo.linear_algebra.vec.Vec3;
import io.github.alphameo.linear_algebra.vec.Vector2;
import io.github.alphameo.linear_algebra.vec.Vector3;
import io.github.clowngraphics.rerenderer.render.Polygon;
import io.github.clowngraphics.rerenderer.render.Vertex;

import java.util.*;

public final class ObjectFile {

    private final List<Vertex> vertices;
    private final List<Integer> vertexIndices;
    private final List<Vector2> textureVertices;
    private final List<Integer> textureVertexIndices;
    private final List<Vector3> normals;
    private final List<Integer> normalIndices;
    private final List<Polygon> polygons;

    public ObjectFile(
            List<Vertex> vertices,
            List<Integer> vertexIndices,
            List<Vector2> textureVertices,
            List<Integer> textureVertexIndices,
            List<Vector3> normals,
            List<Integer> normalIndices,
            List<Polygon> polygons) {
        this.vertices = Objects.requireNonNull(vertices);
        this.vertexIndices = Objects.requireNonNull(vertexIndices);
        this.textureVertices = Objects.requireNonNull(textureVertices);
        this.textureVertexIndices = Objects.requireNonNull(textureVertexIndices);
        this.normals = Objects.requireNonNull(normals);
        this.normalIndices = Objects.requireNonNull(normalIndices);
        this.polygons = Objects.requireNonNull(polygons);
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Vector2> getTextureVertices() {
        return textureVertices;
    }

    public List<Vector3> getNormals() {
        return normals;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public List<Integer> getVertexIndices() {
        return vertexIndices;
    }

    public List<Integer> getTextureVertexIndices() {
        return textureVertexIndices;
    }

    public List<Integer> getNormalIndices() {
        return normalIndices;
    }
}
