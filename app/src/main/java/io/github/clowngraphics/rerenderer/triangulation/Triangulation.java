package io.github.clowngraphics.rerenderer.triangulation;


import io.github.clowngraphics.rerenderer.render.Polygon;

import java.util.ArrayList;
import java.util.List;


public final class Triangulation {

    public static List<Polygon> triangulate(List<Polygon> polygons) {
        List<Polygon> triangulatedPolygons = new ArrayList<>();

        for (Polygon polygon : polygons) {
            List<Integer> vertexIndices = polygon.getVertexIndices();
            int vertexCount = vertexIndices.size();
            if (vertexCount < 3) {
                throw new IllegalArgumentException("Polygon has to have at least 3 vertices.");
            }

            for (int i = 1; i < vertexCount - 1; i++) {
                Polygon triangle = new Polygon();

                triangle.setVertexIndices(createSublist(vertexIndices, 0, i, i + 1));

                if (!polygon.getTextureVertexIndices().isEmpty()) {
                    triangle.setTextureVertexIndices(createSublist(polygon.getTextureVertexIndices(), 0, i, i + 1));
                }

                if (!polygon.getNormalIndices().isEmpty()) {
                    triangle.setNormalIndices(createSublist(polygon.getNormalIndices(), 0, i, i + 1));
                }

                triangulatedPolygons.add(triangle);
            }
        }

        return triangulatedPolygons;


    }

    private static List<Integer> createSublist(List<Integer> list, int... indices) {
        List<Integer> sublist = new ArrayList<>(indices.length);
        for (int index : indices) {
            sublist.add(list.get(index));
        }
        return sublist;
    }
}
