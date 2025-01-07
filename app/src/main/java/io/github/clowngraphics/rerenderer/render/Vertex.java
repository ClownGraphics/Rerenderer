package io.github.clowngraphics.rerenderer.render;

import io.github.alphameo.linear_algebra.vec.Vec4;
import io.github.alphameo.linear_algebra.vec.Vector4;
import io.github.shimeoki.jshaper.Clearable;

import java.util.ArrayList;
import java.util.List;


// TODO Убрать имплементацию интерфейсов - @Fiecher

public final class Vertex implements Clearable {

    private Vector4 values = new Vec4();

    public Vertex(float x, float y, float z, float w) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setW(w);
    }

    public Vertex(Vector4 vector){
        this.setX(vector.x());
        this.setY(vector.y());
        this.setZ(vector.z());
        this.setW(vector.w());
    }

    public void setX(float x){
        this.values.setX(x);
    }

    public void setY(float y){
        this.values.setY(y);
    }

    public void setZ(float z){
        this.values.setY(z);
    }

    public void setW(float w){
        this.values.setY(w);
    }

    public Vector4 getValues() {
        return values;
    }

    public float getX(){
        return values.x();
    }

    public float getY(){
        return values.y();
    }

    public float getZ(){
        return values.z();
    }

    public float getW(){
        return values.w();
    }

    @Override
    public void clear() {
        this.setX(0);
        this.setY(0);
        this.setZ(0);
        this.setW(1);
    }

    public static List<Vertex> convertVerticesFromJShaper(List<io.github.shimeoki.jshaper.obj.Vertex> oldVertices){
        List<Vertex> newVertices = new ArrayList<>();
        for (io.github.shimeoki.jshaper.obj.Vertex vertex : oldVertices){
            newVertices.add(new Vertex(vertex.x(), vertex.y(), vertex.z(), vertex.w()));
        }
        return newVertices;
    }
}
