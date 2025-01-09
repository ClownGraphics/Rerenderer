package io.github.clowngraphics.rerenderer.render;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public final class Triangulation {

    public static List<Polygon> triangulate(List<Polygon> polygons) {
        int vertexCount = 0;

        for (Polygon polygon : polygons) {
            vertexCount += polygon.getVertexIndices().size() - 1;
        }

        List<Polygon> result = new ArrayList<>(vertexCount);

        for (Polygon polygon : polygons) {
            for (int vertex = 1; vertex < polygon.getVertexIndices().size() - 1; vertex++) {

                List<Integer> vertexIndices = getVertexes(polygon.getVertexIndices(), 0, vertex, vertex + 1);
                List<Integer> textureVertexIndices;
                if (!polygon.getTextureVertexIndices().isEmpty()) {
                    textureVertexIndices = getVertexes(polygon.getTextureVertexIndices(), 0, vertex, vertex + 1);

                } else {
                    textureVertexIndices = polygon.getTextureVertexIndices();
                }
                polygon.computeNormal();
                result.add(new Polygon(vertexIndices, textureVertexIndices, polygon.getNormalIndices()));
            }
        }

        return result;
    }

    public static ArrayList<Integer> getVertexes(List<Integer> vertexes, int v1, int v2, int v3) {
        return new ArrayList<>(Arrays.asList(vertexes.get(v1), vertexes.get(v2), vertexes.get(v3)));
    }
}
