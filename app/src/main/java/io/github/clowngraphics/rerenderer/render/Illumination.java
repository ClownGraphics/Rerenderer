package io.github.clowngraphics.rerenderer.render;

import io.github.alphameo.linear_algebra.vec.Vec3;
import io.github.alphameo.linear_algebra.vec.Vec3Math;
import io.github.alphameo.linear_algebra.vec.Vector3;
import io.github.clowngraphics.rerenderer.math.Barycentric;
import io.github.clowngraphics.rerenderer.model.Model;
import io.github.shimeoki.jshaper.ObjFile;
import io.github.shimeoki.jshaper.obj.Vertex;
import io.github.shimeoki.jshaper.obj.VertexData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Illumination {

    private float brightness;

    private static double k = 0.9;

    private Vector3 ray;


    public Illumination(final float baseBrightness) {
        this(baseBrightness, new Vec3(1, 0, 0));
    }

    public Illumination(float brightness, Vector3 ray) {
        this.brightness = brightness;
        this.ray = ray;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public void setRay(Vector3 ray) {
        this.ray = ray;
    }

    // TODO Change ObjFile to Model or something else - @Fiecher
    public ObjFile computeNormals(ObjFile file) {

        List<Vertex> vertices = file.vertexData().vertices();

        Map<Integer, Vector3> vertexNormals = new HashMap<>();
        Map<Integer, Integer> vertexNormalsCount = new HashMap<>();
        ArrayList<Integer> vertexIndices = new ArrayList<>();

        for (Vertex myVertex : vertices) {
            vertexIndices.add(vertices.lastIndexOf(myVertex));
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

        // TODO Проверить, скорее всего нормали не изменяются, так как меняем не textureVertices - @Fiecher
        return new ObjFile(new VertexData(vertices, file.vertexData().textureVertices(),
                file.vertexData().vertexNormals(), file.vertexData().parameterVertices()), file.elements(),
                file.groupingData());
    }

    // TODO Сделать один метод внутри барицентриков, для расчитывания подобного (мб уже есть) - @Fiecher
    private static Vector3 smoothNormals(final Barycentric b, final List<Vector3> n) {
        float x = (float) (b.getLambda1() * n.get(0).x() + b.getLambda2() * n.get(1).x() + b.getLambda3() * n.get(2).x());
        float y = (float) (b.getLambda1() * n.get(0).y() + b.getLambda2() * n.get(1).y() + b.getLambda3() * n.get(2).y());
        float z = (float) (b.getLambda1() * n.get(0).z() + b.getLambda2() * n.get(1).z() + b.getLambda3() * n.get(2).z());
        return new Vec3(x, y, z);
    }

    // TODO Переделать эту дичь - @Fiecher
    public static void light(Barycentric b, final List<Vector3> normals, float[] light, int[] rgb) {
        Vector3 smooth = smoothNormals(b, normals);

        double l = -(light[0] * smooth.x() + light[1] * smooth.y() + light[2] * smooth.z());
        if (l < 0) {
            l = 0;
        }
        rgb[0] = Math.min(255, (int) (rgb[0] * (1 - k) + rgb[0] * k * l));
        rgb[1] = Math.min(255, (int) (rgb[1] * (1 - k) + rgb[1] * k * l));
        rgb[2] = Math.min(255, (int) (rgb[2] * (1 - k) + rgb[2] * k * l));
    }

}

