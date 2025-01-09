package io.github.clowngraphics.rerenderer.render;

import io.github.alphameo.linear_algebra.vec.*;
import io.github.clowngraphics.rerenderer.render.texture.PolygonUVCoordinates;
import io.github.shimeoki.jshaper.ObjFile;
import io.github.shimeoki.jshaper.obj.Face;
import io.github.shimeoki.jshaper.obj.TextureVertex;

import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private List<Vertex> vertices;
    private List<Integer> vertexIndices;
    private PolygonUVCoordinates polygonUVCoordinates;
    private Vector3 normal;



    public Polygon(List<Vertex> vertices, PolygonUVCoordinates polygonUVCoordinates, List<Integer> vertexIndices) {
        this.vertices = vertices;
        this.polygonUVCoordinates = polygonUVCoordinates;
        this.normal = computeNormal();
        this.vertexIndices = vertexIndices;
    }

    public Polygon() {
        // TODO Что делать с UV? -- @Fiecher
        vertices = new ArrayList<>();
        vertexIndices = new ArrayList<>();
    }

    public Polygon(List<Vertex> vertices, PolygonUVCoordinates polygonUVCoordinates) {
        this.vertices = vertices;
        this.polygonUVCoordinates = polygonUVCoordinates;
        this.normal = computeNormal();
        this.vertexIndices = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++){
            vertexIndices.add(i);
        }
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Integer> getVertexIndices() {
        return vertexIndices;
    }

    public void setVertexIndices(List<Integer> vertexIndices) {
        assert vertexIndices.size() >= 3;
        this.vertexIndices = vertexIndices;
    }


    public PolygonUVCoordinates getPolygonUVCoordinates() {
        return polygonUVCoordinates;
    }

    public void setPolygonUVCoordinates(PolygonUVCoordinates polygonUVCoordinates) {
        this.polygonUVCoordinates = polygonUVCoordinates;
    }

    public Vector3 getNormal() {
        return normal;
    }

    public Vector3 computeNormal() {
        if (vertices.size() < 3) {
            throw new IllegalStateException("Polygon must have at least 3 vertices to compute a normal");
        }

        Vector4 normal = new Vec4(0, 0, 0, 0);

        for (int i = 0; i < vertices.size(); i++) {
            Vertex current = vertices.get(i);
            Vertex next = vertices.get((i + 1) % vertices.size());

            Vector4 currentPosition = current.getValues();
            Vector4 nextPosition = next.getValues();
            //todo: sub -> subtracted?
            normal = Vec4Math.added(normal, Vec4Math.sub(currentPosition, nextPosition));
        }


        return normalize(new Vec3(normal.x(), normal.y(), normal.z()));
    }

    private Vector3 normalize(Vec3 v) {
        double length = Math.sqrt(v.x() * v.x() + v.y() * v.y() + v.z() * v.z());
        if (length == 0) {
            throw new IllegalStateException("Cannot normalize a zero-length vector");
        }
        return new Vec3((float) (v.x() / length),(float) (v.y() / length),(float) (v.z() / length));
    }


    public static List<Polygon> convertPolygonsFromJShaper(ObjFile obj) {

        List<Face> faces = obj.elements().faces();
        List<Polygon> newPolygons = new ArrayList<>();

        for (Face face : faces) {
            // Getting vertices
            List<io.github.shimeoki.jshaper.obj.Vertex> oldVertices = new ArrayList<>();
            List<TextureVertex> textureVertices = new ArrayList<>();

            face.triplets().forEach(triplet -> {
                oldVertices.add(triplet.vertex());
                textureVertices.add(triplet.textureVertex());
            });

            // Getting UVs
            List<PolygonUVCoordinates.UVCoordinate> uvCoordinates = new ArrayList<>();
            for (TextureVertex textureVertex : textureVertices) {
                if (textureVertex != null) {
                    uvCoordinates.add(new PolygonUVCoordinates.UVCoordinate(
                            textureVertex.u(),
                            textureVertex.v()
                    ));
                } else {
                    uvCoordinates.add(new PolygonUVCoordinates.UVCoordinate(0, 0)); // Default UV if missing
                }
            }

            PolygonUVCoordinates polygonUVCoordinates = PolygonUVCoordinates.fromUVCoordinates(uvCoordinates);

            // Getting vertex indices
            List<Integer> vertexIndices = new ArrayList<>();
            for (io.github.shimeoki.jshaper.obj.Vertex vertex : oldVertices) {
                vertexIndices.add(obj.vertexData().vertices().indexOf(vertex));
            }

            // Create polygon
            newPolygons.add(new Polygon(Vertex.convertVerticesFromJShaper(oldVertices),
                    polygonUVCoordinates,
                    vertexIndices));
        }

        return newPolygons;
    }


    public static Polygon copy(Polygon polygon){
        return new Polygon(polygon.getVertices(),polygon.getPolygonUVCoordinates(), polygon.getVertexIndices());
    }

}
