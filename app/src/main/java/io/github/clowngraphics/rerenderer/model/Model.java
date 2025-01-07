package io.github.clowngraphics.rerenderer.model;


import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import io.github.alphameo.linear_algebra.vec.Vector3;
import io.github.clowngraphics.rerenderer.model.transform.ModelTransform;
import io.github.clowngraphics.rerenderer.render.Polygon;
import io.github.clowngraphics.rerenderer.render.Vertex;
import io.github.clowngraphics.rerenderer.render.texture.Texture;
import io.github.shimeoki.jshaper.ObjFile;
import io.github.shimeoki.jshaper.obj.Face;

import io.github.shimeoki.jshaper.obj.TextureVertex;
import io.github.shimeoki.jshaper.obj.VertexNormal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author traunin {@link https://github.com/Traunin}
 */
public class Model implements Object {

    //TODO: решить, как лучше получать преобразованную модель:
    //  1) Статическим классом трансформатором
    //  2) Внутренними методами
    // - Миша
    private ModelTransform orientation;
    private Texture texture;
    private final List<Vertex> vertices;
    private final List<Polygon> polygons;
    private final List<Vector3> normals;

    private final Matrix4 modelM = new Mat4(
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
    );


    public Model(final ObjFile obj, final Texture texture) {
        vertices = Vertex.convertVerticesFromJShaper(obj.vertexData().vertices());
        polygons = Polygon.convertPolgonsFromJShaper(obj.elements().faces());

        normals = new ArrayList<>();
        for (Polygon polygon : polygons){
            normals.add(polygon.getNormal());
        }

        this.texture = texture;
    }

    public Matrix4 getModelMatrix() {
        return modelM;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public List<Vector3> getNormals() {
        return normals;
    }

    @Override
    public ModelTransform getTransform() {
        return orientation;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }


}
