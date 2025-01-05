package io.github.clowngraphics.rerenderer.render;

import io.github.alphameo.linear_algebra.vec.Vec3;
import io.github.alphameo.linear_algebra.vec.Vec3Math;
import io.github.alphameo.linear_algebra.vec.Vector3;
import io.github.shimeoki.jshaper.ObjFile;
import io.github.shimeoki.jshaper.obj.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Normalizator {

    public ObjFile computeNormals(ObjFile file) {

        List<Vertex> vertices = file.vertexData().vertices();

        Map<Integer, Vector3> vertexNormals = new HashMap<>();
        Map<Integer, Integer> vertexNormalsCount = new HashMap<>();
        ArrayList<Integer> vertexIndices = new ArrayList<>();

        for (Vertex vertex : vertices) {
            vertexIndices.add(vertices.lastIndexOf(vertex));
            if (vertexIndices.size() < 3) {
                continue;
            }

            Vec3[] vectors = new Vec3[3];
            for (int i = 0; i < 3; i++) {
                vectors[i] = new Vec3(vertices.get(vertexIndices.get(i)).x(), vertices.get(vertexIndices.get(i)).y(),
                        vertices.get(vertexIndices.get(i)).z());
            }

            Vector3 edge1 = Vec3Math.subtracted(vectors[0], vectors[1]);
            Vector3 edge2 = Vec3Math.subtracted(vectors[0], vectors[2]);
            Vector3 faceNormal = Vec3Math.normalize(Vec3Math.cross(edge1, edge2));

            for (int index : vertexIndices) {
                vertexNormals.compute(index, (k, v) -> {
                    if (v == null) {
                        return faceNormal.clone();
                    } else {
                        return Vec3Math.add(v, faceNormal);
                    }
                });
            }

            for (int index : vertexIndices) {

                if (vertexNormalsCount.containsKey(index)) {
                    vertexNormalsCount.put(index, vertexNormalsCount.get(index) + 1);
                } else {
                    vertexNormalsCount.put(index, 1);
                }

            }

        }
        for (Integer index : vertexNormals.keySet()) {
            vertexNormals.put(index, Vec3Math.divided(vertexNormals.get(index), vertexNormalsCount.get(index)));
        }

        // Проверить, скорее всего нормали не изменяются, так как меняем не
        // textureVertices
        return new ObjFile(new VertexData(vertices, file.vertexData().textureVertices(),
                file.vertexData().vertexNormals(), file.vertexData().parameterVertices()), file.elements(),
                file.groupingData());
    }

}
