package io.github.clowngraphics.rerenderer.window.converters;

import io.github.alphameo.linear_algebra.vec.Vec3;
import io.github.alphameo.linear_algebra.vec.Vec4;
import io.github.alphameo.linear_algebra.vec.Vector3;
import io.github.alphameo.linear_algebra.vec.Vector4;
import io.github.clowngraphics.rerenderer.model.Model;
import io.github.clowngraphics.rerenderer.render.Polygon;
import io.github.clowngraphics.rerenderer.render.Vertex;
import io.github.clowngraphics.rerenderer.render.texture.Texture;
import io.github.shimeoki.jshaper.ObjFile;

import java.io.*;
import java.util.*;


public class ObjectWriter {

    public static Model loadModelFromFile(File objFile, Texture texture) throws IOException {
        List<String> lines = readLinesFromFile(objFile);

        List<Vector4> vertices = new ArrayList<>();
        List<Polygon> polygons = new ArrayList<>();

        for (String line : lines) {
            line = line.trim();

            if (line.startsWith("v ")) {
                Vector4 vertex = parseVertex(line);
                vertices.add(vertex);
            } else if (line.startsWith("f ")) {
                Polygon polygon = parsePolygon(line, vertices);
                polygons.add(polygon);
            }
        }

        //ObjFile objFileData = new ObjFile(vertices, polygons);
        //return new Model(objFileData, texture);
        return null;
    }

    private static List<String> readLinesFromFile(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private static Vector4 parseVertex(String line) {
        String[] parts = line.split(" ");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid vertex line: " + line);
        }
        float x = Float.parseFloat(parts[1]);
        float y = Float.parseFloat(parts[2]);
        float z = Float.parseFloat(parts[3]);
        return new Vec4(x, y, z, 1);
    }

    private static Polygon parsePolygon(String line, List<Vector4> vertices) {
        String[] parts = line.split(" ");
        List<Vertex> polygonVertices = new ArrayList<>();

        for (int i = 1; i < parts.length; i++) {
            String[] indices = parts[i].split("/");
            int vertexIndex = Integer.parseInt(indices[0]) - 1;

            if (vertexIndex < 0 || vertexIndex >= vertices.size()) {
                throw new IllegalArgumentException("Invalid vertex index: " + parts[i]);
            }

            polygonVertices.add(new Vertex(vertices.get(vertexIndex)));
        }

        //return new Polygon(polygonVertices);
        return null;
    }



}

