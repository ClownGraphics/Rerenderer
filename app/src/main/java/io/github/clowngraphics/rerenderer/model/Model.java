package io.github.clowngraphics.rerenderer.model;


import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import io.github.alphameo.linear_algebra.vec.Vec2;
import io.github.alphameo.linear_algebra.vec.Vec3;
import io.github.alphameo.linear_algebra.vec.Vector2;
import io.github.alphameo.linear_algebra.vec.Vector3;
import io.github.clowngraphics.rerenderer.model.transform.ModelTransform;
import io.github.clowngraphics.rerenderer.render.Polygon;
import io.github.clowngraphics.rerenderer.render.Triangulation;
import io.github.clowngraphics.rerenderer.render.Vertex;
import io.github.clowngraphics.rerenderer.render.texture.ImageTexture;
import io.github.clowngraphics.rerenderer.render.texture.Texture;
import io.github.shimeoki.jshaper.ObjFile;
import io.github.shimeoki.jshaper.obj.TextureVertex;
import io.github.shimeoki.jshaper.obj.VertexNormal;

import java.util.ArrayList;
import java.util.List;


public class Model implements Object {

    //TODO: решить, как лучше получать преобразованную модель:
    //  1) Статическим классом трансформатором
    //  2) Внутренними методами
    // - Миша

    private final List<Vertex> vertices;
    private List<Vector2> textureVertices;
    private final List<Vector3> normals;
    private final List<Polygon> polygons;

    private ModelTransform modelTransform = new ModelTransform();
    private Texture texture;

    private final Matrix4 modelM = new Mat4(
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
    );


    public Model(final ObjFile obj, final ImageTexture texture) {
        vertices = Vertex.convertVerticesFromJShaper(obj.vertexData().vertices());
        polygons = Triangulation.triangulate(Polygon.convertPolygonsFromJShaper(obj));
        textureVertices = convertTextureVerticesFromJShaper(obj.vertexData().textureVertices());
        normals = convertNormalsFromJShaper(obj.vertexData().vertexNormals());
        texture.setTextureVertices(getTextureVertices());
        this.texture = texture;
    }

    public Model(final ObjFile obj) {
        vertices = Vertex.convertVerticesFromJShaper(obj.vertexData().vertices());
        polygons = Triangulation.triangulate(Polygon.convertPolygonsFromJShaper(obj));
        textureVertices = convertTextureVerticesFromJShaper(obj.vertexData().textureVertices());
        normals = convertNormalsFromJShaper(obj.vertexData().vertexNormals());
    }

    public Model(final ObjFile obj, final Texture texture) {
        vertices = Vertex.convertVerticesFromJShaper(obj.vertexData().vertices());
        polygons = Triangulation.triangulate(Polygon.convertPolygonsFromJShaper(obj));
        textureVertices = convertTextureVerticesFromJShaper(obj.vertexData().textureVertices());
        normals = convertNormalsFromJShaper(obj.vertexData().vertexNormals());
        this.texture = texture;
    }

    private static List<Vector2> convertTextureVerticesFromJShaper(List<TextureVertex> oldTextureVertices){
        List<Vector2> newTextureVertices = new ArrayList<>();
        for (TextureVertex v : oldTextureVertices){
            newTextureVertices.add(new Vec2(v.u(),v.v()));
        }
        return newTextureVertices;
    }

    private static List<Vector3> convertNormalsFromJShaper(List<VertexNormal> oldNormals){
        List<Vector3> newNormals = new ArrayList<>();
        for (VertexNormal v : oldNormals){
            newNormals.add(new Vec3(v.i(),v.j(),v.k()));
        }
        return newNormals;
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

    public List<Vector2> getTextureVertices() {
        return textureVertices;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

}
