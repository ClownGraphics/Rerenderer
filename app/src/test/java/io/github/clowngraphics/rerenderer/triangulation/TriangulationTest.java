package io.github.clowngraphics.rerenderer.triangulation;

import io.github.clowngraphics.rerenderer.render.Polygon;
import io.github.clowngraphics.rerenderer.render.Triangulation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.*;

public class TriangulationTest {
    @Test
    public void testSingleTriangle() {
        Polygon triangle = new Polygon();
        triangle.setVertexIndices(Arrays.asList(0, 1, 2));

        List<Polygon> result = Triangulation.triangulate(Collections.singletonList(triangle));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(Arrays.asList(0, 1, 2), result.get(0).getVertexIndices());
    }

    @Test
    public void testQuadToTriangles() {
        Polygon quad = new Polygon();
        quad.setVertexIndices(Arrays.asList(0, 1, 2, 3));

        List<Polygon> result = Triangulation.triangulate(Collections.singletonList(quad));

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(Arrays.asList(0, 1, 2), result.get(0).getVertexIndices());
        Assertions.assertEquals(Arrays.asList(0, 2, 3), result.get(1).getVertexIndices());
    }

    @Test
    public void testPolygonWithTextureAndNormals() {
        Polygon polygon = new Polygon();
        polygon.setVertexIndices(Arrays.asList(0, 1, 2, 3));
        polygon.setTextureVertexIndices(Arrays.asList(10, 11, 12, 13));
        polygon.setNormalIndices(Arrays.asList(20, 21, 22, 23));

        List<Polygon> result = Triangulation.triangulate(Collections.singletonList(polygon));

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals(Arrays.asList(0, 1, 2), result.get(0).getVertexIndices());
        Assertions.assertEquals(Arrays.asList(10, 11, 12), result.get(0).getTextureVertexIndices());
        Assertions.assertEquals(Arrays.asList(20, 21, 22), result.get(0).getNormalIndices());

        Assertions.assertEquals(Arrays.asList(0, 2, 3), result.get(1).getVertexIndices());
        Assertions.assertEquals(Arrays.asList(10, 12, 13), result.get(1).getTextureVertexIndices());
        Assertions.assertEquals(Arrays.asList(20, 22, 23), result.get(1).getNormalIndices());
    }

    @Test
    public void testEmptyPolygonList() {
        List<Polygon> result = Triangulation.triangulate(Collections.emptyList());
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testInvalidPolygon() {
        Polygon invalidPolygon = new Polygon();
        invalidPolygon.setVertexIndices(Arrays.asList(0, 1));

        try {
            Triangulation.triangulate(Collections.singletonList(invalidPolygon));
            Assertions.fail("Expected an exception for invalid polygon");
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Polygon must have at least 3 vertices.", e.getMessage());
        }
    }
}
