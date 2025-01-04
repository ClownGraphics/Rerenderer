package io.github.ClownGraphics.Rerenderer.triangulation;

import io.github.ClownGraphics.Rerenderer.math.VectorMath;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public final class Triangulation {

    private Triangulation() {
        throw new UnsupportedOperationException("Cannot be instantiated.");
    }

    private static void checkVertexIndicesCount(int n) {
        if (n < 3) {
            throw new IllegalArgumentException("Not enough vertex indices for a polygon.");
        }
    }

    public static <T extends Vector2f> List<int[]> earClippingTriangulate(List<T> vertices) {
        List<Integer> vertexIndices = IntStream.rangeClosed(0, vertices.size() - 1).boxed().toList();
        return earClippingTriangulate(vertices, vertexIndices);
    }

    // Триангуляция любых многоугольников, методом отсечения "ушей".
    public static <T extends Vector2f> List<int[]> earClippingTriangulate(List<T> vertices, List<Integer> vertexIndices) {
        int vertexIndicesCount = vertexIndices.size();
        checkVertexIndicesCount(vertexIndicesCount);

        int vertexCount = vertices.size();
        for (Integer vertexIndex : vertexIndices) {
            if (vertexIndex >= vertexCount) {
                throw new IllegalArgumentException(
                        String.format("Vertex index = %d is outside of vertex list of length = %d.", vertexIndex, vertexCount)
                );
            }
        }

        List<int[]> triangles = new ArrayList<>(vertexIndicesCount - 2);
        List<Integer> potentialEars = new ArrayList<>(vertexIndices);
        int potentialEarsCount = vertexIndicesCount;

        boolean isCCW = isCounterClockwise(vertices, vertexIndices);
        boolean hasClippedEars = true;
        //Продолжаем пока отрезаем уши, если за итерацию не отрезали -- расходимся.
        while (hasClippedEars) {
            hasClippedEars = false;
            for (int i = 1; i < potentialEarsCount - 1; i++) {
                int prevVertexIndex = potentialEars.get(i - 1);
                int curVertexIndex = potentialEars.get(i);
                int nextVertexIndex = potentialEars.get(i + 1);

                Vector2f prevVertex = vertices.get(prevVertexIndex);
                Vector2f curVertex = vertices.get(curVertexIndex);
                Vector2f nextVertex = vertices.get(nextVertexIndex);

                // Ухо выпуклое? Проверка через векторное произведение
                float crossProduct = VectorMath.crossProduct(prevVertex, curVertex, nextVertex);
                if ((isCCW ? crossProduct : -crossProduct) < VectorMath.EPSILON) {
                    continue;
                }

                if (!isEar(vertices, vertexIndices, prevVertexIndex, curVertexIndex, nextVertexIndex)) {
                    continue;
                }

                triangles.add(new int[] {prevVertexIndex, curVertexIndex, nextVertexIndex});
                potentialEars.remove(i);
                potentialEarsCount--;
                i--;
                hasClippedEars = true;
            }
        }

        if (triangles.size() != vertexIndicesCount - 2) {
            throw new TriangulationException("Polygon has self-intersections.");
        }

        return triangles;
    }

    // Ориентация (против часовой стрелки/по часовой стрелке) вычисляется с помощью площади многоугольника,
    // которая рассчитывается через сумму попарных произведений координат вершин.
    // Если площадь отрицательная, многоугольник ориентирован против часовой стрелки, иначе -- по часовой.
    private static <T extends Vector2f> boolean isCounterClockwise(List<T> vertices, List<Integer> vertexIndices) {
        float area = 0;
        int vertexIndicesCount = vertexIndices.size();

        Vector2f prevVertex = vertices.get(vertexIndices.get(0));
        for (int i = 1; i <= vertexIndicesCount; i++) {
            Vector2f currentVertex = vertices.get(vertexIndices.get(i % vertexIndicesCount));

            area += (currentVertex.x() - prevVertex.x()) * (currentVertex.y() + prevVertex.y());
            prevVertex = currentVertex;
        }

        return area < 0;
    }

    private static <T extends Vector2f> boolean isEar(List<T> vertices, List<Integer> vertexIndices, int prevVertexIndex, int curVertexIndex, int nextVertexIndex) {
        Vector2f prevVertex = vertices.get(prevVertexIndex);
        Vector2f curVertex = vertices.get(curVertexIndex);
        Vector2f nextVertex = vertices.get(nextVertexIndex);

        for (int checkedVertexIndex : vertexIndices) {
            if (checkedVertexIndex == prevVertexIndex || checkedVertexIndex == curVertexIndex || checkedVertexIndex == nextVertexIndex) {
                continue;
            }

            Vector2f checkedVertex = vertices.get(checkedVertexIndex);
            if (VectorMath.isPointInTriangle(prevVertex, curVertex, nextVertex, checkedVertex)) {
                return false;
            }
        }

        return true;
    }
}