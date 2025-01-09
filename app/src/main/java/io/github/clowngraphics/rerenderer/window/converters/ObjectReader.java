package io.github.clowngraphics.rerenderer.window.converters;

import io.github.alphameo.linear_algebra.vec.Vec2;
import io.github.alphameo.linear_algebra.vec.Vec3;
import io.github.alphameo.linear_algebra.vec.Vec4;
import io.github.clowngraphics.rerenderer.render.Polygon;
import io.github.clowngraphics.rerenderer.render.Vertex;

import java.io.*;
import java.util.*;

import java.io.*;
import java.util.*;

public class ObjectReader {

    public static ObjectFile readObjFile(File objFile) throws IOException {
        List<Vertex> vertices = new ArrayList<>();
        List<Integer> vertexIndices = new ArrayList<>();
        List<Vec2> textureVertices = new ArrayList<>();
        List<Integer> textureVertexIndices = new ArrayList<>();
        List<Vec3> normals = new ArrayList<>();
        List<Integer> normalIndices = new ArrayList<>();
        List<Polygon> polygons = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(objFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("v ")) {
                    vertices.add(parseVertex(line));
                } else if (line.startsWith("vt ")) {
                    textureVertices.add(parseTextureVertex(line));
                } else if (line.startsWith("vn ")) {
                    normals.add(parseNormal(line));
                } else if (line.startsWith("f ")) {
                    parsePolygon(line, vertexIndices, textureVertexIndices, normalIndices, polygons);
                }
            }
        }

        return new ObjectFile(vertices, vertexIndices, textureVertices, textureVertexIndices, normals, normalIndices, polygons);
    }

    private static Vertex parseVertex(String line) {
        String[] parts = line.split(" ");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid vertex line: " + line);
        }

        float x = Float.parseFloat(parts[1]);
        float y = Float.parseFloat(parts[2]);
        float z = Float.parseFloat(parts[3]);

        return new Vertex(new Vec4(x, y, z, 1));
    }

    private static Vec2 parseTextureVertex(String line) {
        String[] parts = line.split(" ");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid texture vertex line: " + line);
        }

        float u = Float.parseFloat(parts[1]);
        float v = Float.parseFloat(parts[2]);

        return new Vec2(u, v);
    }

    private static Vec3 parseNormal(String line) {
        String[] parts = line.split(" ");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid normal line: " + line);
        }

        float x = Float.parseFloat(parts[1]);
        float y = Float.parseFloat(parts[2]);
        float z = Float.parseFloat(parts[3]);

        return new Vec3(x, y, z);
    }

    private static void parsePolygon(String line, List<Integer> vertexIndices, List<Integer> textureVertexIndices, List<Integer> normalIndices, List<Polygon> polygons) {
        String[] parts = line.split(" ");
        List<Vertex> polygonVertices = new ArrayList<>();

        for (int i = 1; i < parts.length; i++) {
            String[] indices = parts[i].split("/");
            int vertexIndex = Integer.parseInt(indices[0]) - 1;
            vertexIndices.add(vertexIndex);

            int textureIndex = indices.length > 1 && !indices[1].isEmpty() ? Integer.parseInt(indices[1]) - 1 : -1;
            if (textureIndex >= 0) {
                textureVertexIndices.add(textureIndex);
            }

            int normalIndex = indices.length > 2 ? Integer.parseInt(indices[2]) - 1 : -1;
            if (normalIndex >= 0) {
                normalIndices.add(normalIndex);
            }


        }


         polygons.add(new Polygon(vertexIndices, textureVertexIndices, normalIndices));
    }
}

