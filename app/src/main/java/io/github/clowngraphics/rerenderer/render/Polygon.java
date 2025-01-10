package io.github.clowngraphics.rerenderer.render;

import io.github.alphameo.linear_algebra.vec.*;
import io.github.shimeoki.jshaper.ObjFile;
import io.github.shimeoki.jshaper.obj.Face;
import io.github.shimeoki.jshaper.obj.Triplet;

import java.util.ArrayList;
import java.util.List;

public class Polygon {


    private List<Integer> vertexIndices;
    private List<Integer> textureVertexIndices;
    private List<Integer> normalIndices;


    public Polygon(List<Integer> vertexIndices, List<Integer> textureVertexIndices, List<Integer> normalIndices) {
        this.vertexIndices = vertexIndices;
        this.textureVertexIndices = textureVertexIndices;
        this.normalIndices = normalIndices;
    }


    public List<Integer> getVertexIndices() {
        return vertexIndices;
    }

    public void setVertexIndices(List<Integer> vertexIndices) {
        assert vertexIndices.size() >= 3;
        this.vertexIndices = vertexIndices;
    }

    public void setTextureVertexIndices(List<Integer> textureVertexIndices) {
        this.textureVertexIndices = textureVertexIndices;
    }

    public void setNormalIndices(List<Integer> normalIndices) {
        this.normalIndices = normalIndices;
    }

    public List<Integer> getTextureVertexIndices() {
        return textureVertexIndices;
    }

    public List<Integer> getNormalIndices() {
        return normalIndices;
    }

    //TODO Сделать просчет нормали полигона
    public Vector3 computeNormal() {
//        if (vertices.size() < 3) {
//            throw new IllegalStateException("Polygon must have at least 3 vertices to compute a normal");
//        }
//
//        Vector4 normal = new Vec4(0, 0, 0, 0);
//
//        for (int i = 0; i < vertices.size(); i++) {
//            Vertex current = vertices.get(i);
//            Vertex next = vertices.get((i + 1) % vertices.size());
//
//            Vector4 currentPosition = current.getValues();
//            Vector4 nextPosition = next.getValues();
//            //todo: sub -> subtracted?
//            normal = Vec4Math.added(normal, Vec4Math.sub(currentPosition, nextPosition));
//        }
//
//
//        return normalize(new Vec3(normal.x(), normal.y(), normal.z()));
        return null;
    }

    private Vector3 normalize(Vec3 v) {
        double length = Math.sqrt(v.x() * v.x() + v.y() * v.y() + v.z() * v.z());
        if (length == 0) {
            throw new IllegalStateException("Cannot normalize a zero-length vector");
        }
        return new Vec3((float) (v.x() / length), (float) (v.y() / length), (float) (v.z() / length));
    }

    public static Polygon convertPolygonFromJShaper(ObjFile obj, int index) {
        List<Integer> vertexIndices = new ArrayList<>();
        List<Integer> textureVertexIndices = new ArrayList<>();
        List<Integer> normalIndices = new ArrayList<>();

        for (Triplet t : obj.elements().faces().get(index).triplets()) {
            vertexIndices.add(obj.vertexData().vertices().indexOf(t.vertex()));
            textureVertexIndices.add(obj.vertexData().textureVertices().indexOf(t.vertexNormal()));
            normalIndices.add(obj.vertexData().vertexNormals().indexOf(t.textureVertex()));
        }

        return new Polygon(vertexIndices, textureVertexIndices, normalIndices);
    }


    public static List<Polygon> convertPolygonsFromJShaper(ObjFile obj) {

        List<Face> faces = obj.elements().faces();
        List<Polygon> newPolygons = new ArrayList<>();

        int index = 0;
        for (Face face : faces) {

            newPolygons.add(convertPolygonFromJShaper(obj, index));
            index++;
//
//            // Getting vertices
//            List<io.github.shimeoki.jshaper.obj.Vertex> oldVertices = new ArrayList<>();
//            List<TextureVertex> textureVertices = new ArrayList<>();
//
//            face.triplets().forEach(triplet -> {
//                oldVertices.add(triplet.vertex());
//                textureVertices.add(triplet.textureVertex());
//            });
//
//            // Getting UVs
//            List<PolygonUVCoordinates.UVCoordinate> uvCoordinates = new ArrayList<>();
//            for (TextureVertex textureVertex : textureVertices) {
//                if (textureVertex != null) {
//                    uvCoordinates.add(new PolygonUVCoordinates.UVCoordinate(
//                            textureVertex.u(),
//                            textureVertex.v()
//                    ));
//                } else {
//                    uvCoordinates.add(new PolygonUVCoordinates.UVCoordinate(0, 0)); // Default UV if missing
//                }
//            }
//
//            PolygonUVCoordinates polygonUVCoordinates = PolygonUVCoordinates.fromUVCoordinates(uvCoordinates);
//
//            // Getting vertex indices
//            List<Integer> vertexIndices = new ArrayList<>();
//            for (io.github.shimeoki.jshaper.obj.Vertex vertex : oldVertices) {
//                vertexIndices.add(obj.vertexData().vertices().indexOf(vertex));
//            }
//
//            // Create polygon
//            newPolygons.add(new Polygon(Vertex.convertVerticesFromJShaper(oldVertices),
//                    polygonUVCoordinates,
//                    vertexIndices));
        }

        return newPolygons;
    }

}
