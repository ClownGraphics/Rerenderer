package io.github.clowngraphics.rerenderer.triangulation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import io.github.clowngraphics.rerenderer.math.VectorMath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TriangulationTest {
    private final static Random RANDOM = new Random();

    @RepeatedTest(10)
    public void testRandomPolygon() {
        int verticesCount = 8;
        float size = 15;
        List<ReadOnlyVector2f> randomPolygon = new ArrayList<>(verticesCount);

        for (int i = 0; i < verticesCount; i++) {
            float randomSize = RANDOM.nextFloat(size / 2, size);
            randomPolygon.add(new ReadOnlyVector2f(
                    (float) (Math.cos(i * 2 * Math.PI / verticesCount) * randomSize),
                    (float) (Math.sin(i * 2 * Math.PI / verticesCount) * randomSize)));
        }

        Triangulation Triangulation = null;
        List<int[]> triangles = Triangulation.earClippingTriangulate(randomPolygon);
        Assertions.assertEquals(verticesCount - 2, triangles.size());
    }

    private final static List<ReadOnlyVector2f> TRIANGLE = Arrays.asList(
            new ReadOnlyVector2f(0, 0),
            new ReadOnlyVector2f(6, 0),
            new ReadOnlyVector2f(0, 8));

    private final static List<ReadOnlyVector2f> SELF_INTERSECTING_POLYGON = Arrays.asList(
            new ReadOnlyVector2f(0, 0),
            new ReadOnlyVector2f(0, 3),
            new ReadOnlyVector2f(-2, 1),
            new ReadOnlyVector2f(3, 0));

    @Test
    public void testVertexIndexOutsideOfVertices() {
        try {
            Triangulation.earClippingTriangulate(TRIANGLE, Arrays.asList(1, 2, 4));
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            String expectedError = "Vertex index = 4 is outside of vertex list of length = 3.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testSelfIntersectingPolygon() {
        try {
            Triangulation.earClippingTriangulate(SELF_INTERSECTING_POLYGON);
            Assertions.fail();
        } catch (TriangulationException exception) {
            String expectedError = "Polygon has self-intersections.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testCrossProduct() {
        float cwCrossProduct = VectorMath.crossProduct(
                TRIANGLE.get(1),
                TRIANGLE.get(0),
                TRIANGLE.get(2));

        float ccwCrossProduct = VectorMath.crossProduct(
                TRIANGLE.get(2),
                TRIANGLE.get(0),
                TRIANGLE.get(1));

        float zeroCrossProduct = VectorMath.crossProduct(
                TRIANGLE.get(1),
                TRIANGLE.get(0),
                TRIANGLE.get(1));

        Assertions.assertEquals(-48, cwCrossProduct);
        Assertions.assertEquals(48, ccwCrossProduct);
        Assertions.assertEquals(0, Math.abs(zeroCrossProduct));
    }

    @Test
    public void testPointWithinTriangle() {
        Assertions.assertTrue(VectorMath.isPointInTriangle(
                TRIANGLE.get(1),
                TRIANGLE.get(0),
                TRIANGLE.get(2),
                new ReadOnlyVector2f(2, 2)));

        Assertions.assertFalse(VectorMath.isPointInTriangle(
                TRIANGLE.get(1),
                TRIANGLE.get(0),
                TRIANGLE.get(2),
                new ReadOnlyVector2f(15, 15)));
    }

    @Test
    public void testEdgeLength() {
        Assertions.assertEquals(10, VectorMath.edgeLength(
                TRIANGLE.get(1),
                TRIANGLE.get(2)));
    }
}
