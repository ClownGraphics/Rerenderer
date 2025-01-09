package io.github.clowngraphics.rerenderer.render;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Triangulation {

    public static List<Polygon> triangulate(List<Polygon> polygons) {
        int triangleCount = 0;

        // Подсчет общего количества треугольников после триангуляции
        for (Polygon polygon : polygons) {
            triangleCount += polygon.getVertexIndices().size() - 2;
        }

        List<Polygon> result = new ArrayList<>(triangleCount);

        // Триангуляция каждого многоугольника
        for (Polygon polygon : polygons) {
            int vertexCount = polygon.getVertexIndices().size();
            List<Integer> vertexIndices = polygon.getVertexIndices();
            List<Integer> textureVertexIndices = polygon.getTextureVertexIndices();
            List<Integer> normalIndices = polygon.getNormalIndices();

            for (int i = 1; i < vertexCount - 1; i++) {
                // Создаём новый треугольник
                List<Integer> newVertexIndices = getVertexes(vertexIndices, 0, i, i + 1);

                List<Integer> newTextureVertexIndices = new ArrayList<>();
                if (!textureVertexIndices.isEmpty()) {
                    // Перенос текстурных координат
                    newTextureVertexIndices = getVertexes(textureVertexIndices, 0, i, i + 1);
                }

                List<Integer> newNormalIndices = new ArrayList<>();
                if (!normalIndices.isEmpty()) {
                    // Перенос нормалей
                    newNormalIndices = getVertexes(normalIndices, 0, i, i + 1);
                }

                // Добавляем новый треугольник в результат
                result.add(new Polygon(newVertexIndices, newTextureVertexIndices, newNormalIndices));
            }
        }

        return result;
    }

    // Утилитарный метод для извлечения индексов
    public static ArrayList<Integer> getVertexes(List<Integer> indices, int v1, int v2, int v3) {
        return new ArrayList<>(Arrays.asList(indices.get(v1), indices.get(v2), indices.get(v3)));
    }
}
