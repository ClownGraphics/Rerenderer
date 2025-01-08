package io.github.clowngraphics.rerenderer.render;


import io.github.clowngraphics.rerenderer.render.texture.PolygonUVCoordinates;

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
        Polygon polygonResult;
        for (Polygon polygon : polygons) {
            for (int vertex = 1; vertex < polygon.getVertexIndices().size() - 1; vertex++) {
                polygonResult = new Polygon();
                ArrayList<Integer> vertexIndices = (ArrayList<Integer>) getVertexes(
                        polygon.getVertexIndices(),
                        0, vertex, vertex + 1);
                polygonResult.setVertexIndices(vertexIndices);
//                if (!polygon.getTextureVertexIndices().isEmpty()) {
//                    ArrayList<Integer> textureVertexIndices = (ArrayList<Integer>) getVertexes(
//                            polygon.getTextureVertexIndices(),
//                            0, vertex, vertex + 1);
//                    polygonResult.setTextureVertexIndices(textureVertexIndices);
//                }
                polygon.computeNormal();
                result.add(polygonResult);
            }
        }

        return result;
    }

    public static List<Integer> getVertexes(List<Integer> vertexes, int v1, int v2, int v3) {
        return new ArrayList<>(Arrays.asList(
                vertexes.get(v1),
                vertexes.get(v2),
                vertexes.get(v3)
        ));
    }
}
