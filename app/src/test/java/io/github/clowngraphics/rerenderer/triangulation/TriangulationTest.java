package io.github.clowngraphics.rerenderer.triangulation;

import io.github.alphameo.linear_algebra.vec.Vec2;
import io.github.clowngraphics.rerenderer.render.Polygon;
import io.github.clowngraphics.rerenderer.render.Triangulation;
import io.github.clowngraphics.rerenderer.render.texture.PolygonUVCoordinates;
import io.github.alphameo.linear_algebra.vec.Vec4;
import io.github.clowngraphics.rerenderer.render.Vertex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TriangulationTest {

    @Test
    public void testSingleTriangle() {
        Polygon triangle = createPolygon(Arrays.asList(
                new Vertex(new Vec4(0, 0, 0, 1)),
                new Vertex(new Vec4(1, 0, 0, 1)),
                new Vertex(new Vec4(0, 1, 0, 1))
        ));

        triangle.setVertexIndices(Arrays.asList(0, 1, 2));

        List<Polygon> result = Triangulation.triangulate(Collections.singletonList(triangle));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(Arrays.asList(0, 1, 2), result.get(0).getVertexIndices());
    }

    @Test
    public void testQuadToTriangles() {
        Polygon quad = createPolygon(Arrays.asList(
                new Vertex(new Vec4(0, 0, 0, 1)),
                new Vertex(new Vec4(1, 0, 0, 1)),
                new Vertex(new Vec4(1, 1, 0, 1)),
                new Vertex(new Vec4(0, 1, 0, 1))
        ));

        quad.setVertexIndices(Arrays.asList(0, 1, 2, 3));

        List<Polygon> result = Triangulation.triangulate(Collections.singletonList(quad));

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(Arrays.asList(0, 1, 2), result.get(0).getVertexIndices());
        Assertions.assertEquals(Arrays.asList(0, 2, 3), result.get(1).getVertexIndices());
    }

    @Test
    public void testPolygonWithTextureAndNormals() {
        Polygon polygon = createPolygon(Arrays.asList(
                new Vertex(new Vec4(0, 0, 0, 1)),
                new Vertex(new Vec4(1, 0, 0, 1)),
                new Vertex(new Vec4(1, 1, 0, 1)),
                new Vertex(new Vec4(0, 1, 0, 1))
        ));

        polygon.setVertexIndices(Arrays.asList(0, 1, 2, 3));

        List<Polygon> result = Triangulation.triangulate(Collections.singletonList(polygon));

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals(Arrays.asList(0, 1, 2), result.get(0).getVertexIndices());
        Assertions.assertEquals(Arrays.asList(0, 2, 3), result.get(1).getVertexIndices());
    }

    @Test
    public void testEmptyPolygonList() {
        List<Polygon> result = Triangulation.triangulate(Collections.emptyList());
        Assertions.assertTrue(result.isEmpty());
    }

    // Utility method to create a polygon
    private Polygon createPolygon(List<Vertex> vertices) {
        PolygonUVCoordinates dummyUV = new PolygonUVCoordinates(
                new Vec2(0, 0),
                new Vec2(1, 0),
                new Vec2(0, 1 )
        );

        return new Polygon(vertices, dummyUV);
    }
}
