package io.github.clowngraphics.rerenderer.render;

import io.github.alphameo.linear_algebra.vec.Vec4;
import io.github.alphameo.linear_algebra.vec.Vector4;

import java.util.ArrayList;
import java.util.List;


public final class Vertex {

    private int index;
    private Vector4 values = new Vec4();

    public Vertex(float x, float y, float z, float w) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setW(w);
    }

    public Vertex(float x, float y, float z, float w, int index) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setW(w);
    }

    public Vertex(Vector4 vector) {
        this.setX(vector.x());
        this.setY(vector.y());
        this.setZ(vector.z());
        this.setW(vector.w());
    }

    public void setX(float x) {
        this.values.setX(x);
    }

    public void setY(float y) {
        this.values.setY(y);
    }

    public void setZ(float z) {
        this.values.setZ(z);
    }

    public void setW(float w) {
        this.values.setW(w);
    }

    public Vector4 getValues() {
        return values;
    }

    public float getX() {
        return values.x();
    }

    public float getY() {
        return values.y();
    }

    public float getZ() {
        return values.z();
    }

    public float getW() {
        return values.w();
    }

    public int getIndex() {
        return index;
    }

    public void clear() {
        this.setX(0);
        this.setY(0);
        this.setZ(0);
        this.setW(1);
    }

    public static List<Vertex> convertVerticesFromJShaper(List<io.github.shimeoki.jshaper.obj.Vertex> oldVertices) {
        List<Vertex> newVertices = new ArrayList<>();
        for (io.github.shimeoki.jshaper.obj.Vertex v : oldVertices) {
            newVertices.add(new Vertex(v.x(), v.y(), v.z(), v.w(), oldVertices.indexOf(v)));
        }
        return newVertices;
    }
}
