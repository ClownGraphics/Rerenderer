package io.github.clowngraphics.rerenderer.model;


import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import io.github.alphameo.linear_algebra.vec.Vector3;
import io.github.clowngraphics.rerenderer.model.transform.ModelTransform;
import io.github.clowngraphics.rerenderer.render.Polygon;
import io.github.clowngraphics.rerenderer.render.Triangulation;
import io.github.clowngraphics.rerenderer.render.Vertex;
import io.github.clowngraphics.rerenderer.render.texture.MonotoneTexture;
import io.github.clowngraphics.rerenderer.render.texture.Texture;
import io.github.shimeoki.jshaper.ObjFile;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


public class Model implements Object {

    //TODO: решить, как лучше получать преобразованную модель:
    //  1) Статическим классом трансформатором
    //  2) Внутренними методами
    // - Миша

    private ModelTransform modelTransform = new ModelTransform();
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



    public Model(final ObjFile obj) {
        vertices = Vertex.convertVerticesFromJShaper(obj.vertexData().vertices());
        polygons = Triangulation.triangulate(Polygon.convertPolgonsFromJShaper(obj.elements().faces()));
//        polygons = Polygon.convertPolgonsFromJShaper(obj.elements().faces());
        normals = new ArrayList<>();
        for (Polygon polygon : polygons){
            normals.add(polygon.getNormal());
        }

        this.texture = new MonotoneTexture(Color.BLACK);

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

    public List<Polygon> getPolygons() {
        return polygons;
    }

    @Override
    public ModelTransform getTransform() {
        return modelTransform;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }


}
