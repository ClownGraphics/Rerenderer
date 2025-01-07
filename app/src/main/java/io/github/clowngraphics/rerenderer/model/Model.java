package io.github.clowngraphics.rerenderer.model;



import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Matrix4;
import io.github.clowngraphics.rerenderer.model.transform.ModelTransform;
import io.github.clowngraphics.rerenderer.render.texture.Texture;
import io.github.shimeoki.jshaper.ObjFile;
import io.github.shimeoki.jshaper.obj.Face;
import io.github.shimeoki.jshaper.obj.Vertex;
import io.github.shimeoki.jshaper.obj.TextureVertex;
import io.github.shimeoki.jshaper.obj.VertexNormal;
import java.util.List;

/**
 * @author traunin {@link https://github.com/Traunin}
 */
public class Model  implements Object{

    //TODO: решить, как лучше получать преобразованную модель:
    //  1) Статическим классом трансформатором
    //  2) Внутренними методами
    private ModelTransform orientation;
    private final List<Vertex> vertices;

    private final List<TextureVertex> textureVertices;
    private final List<Face> faces;
    private final List<VertexNormal> normals;

    private final Matrix4 modelM = new Mat4(
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
    );

    private Texture texture;

    public Model(final ObjFile obj, final Texture texture){
        vertices = obj.vertexData().vertices();
        textureVertices = obj.vertexData().textureVertices();
        normals = obj.vertexData().vertexNormals();
        faces = obj.elements().faces();
        this.texture = texture;
    }

    public List<Face> getFaces() {
        return faces;
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

    public List<VertexNormal> getNormals() {
        return normals;
    }

    @Override
    public ModelTransform getTransform() {
        return orientation;
    }
}
