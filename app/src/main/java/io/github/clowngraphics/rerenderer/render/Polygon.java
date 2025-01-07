package io.github.clowngraphics.rerenderer.render;

import io.github.clowngraphics.rerenderer.render.texture.PolygonUVCoordinates;

import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private List<Integer> vertexIndices;

    public List<PolygonUVCoordinates> polygonUVCoordinates;

    // TODO текстурные вершины нужно будет убрать - @Fiecher
    private List<Integer> textureVertexIndices;
    public List<Integer> normalIndices;



    public Polygon() {
        vertexIndices = new ArrayList<>();
        textureVertexIndices = new ArrayList<>();
        normalIndices = new ArrayList<>();
        polygonUVCoordinates = new ArrayList<>();
    }

    public List<Integer> getVertexIndices() {
        return vertexIndices;
    }

    public List<Integer> getTextureVertexIndices() {
        return textureVertexIndices;
    }

    public List<Integer> getNormalIndices() {
        return normalIndices;
    }

    public void setVertexIndices(List<Integer> vertexIndices) {
        if (vertexIndices.size() < 3){
            throw new IllegalArgumentException("vertexIndices List has less then 3 vertices");
        }
        this.vertexIndices = vertexIndices;
    }

    public void setTextureVertexIndices(List<Integer> textureVertexIndices) {
        if (textureVertexIndices.size() < 3){
            throw new IllegalArgumentException("textureVertexIndices List has less then 3 vertices");
        }
        this.textureVertexIndices = textureVertexIndices;
    }

    public void setNormalIndices(List<Integer> normalIndices) {
        if (normalIndices.size() < 3){
            throw new IllegalArgumentException("normalIndices List has less then 3 vertices");
        }
        this.normalIndices = normalIndices;
    }
}
