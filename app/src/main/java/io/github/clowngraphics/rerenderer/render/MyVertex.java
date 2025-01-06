package io.github.clowngraphics.rerenderer.render;

import io.github.shimeoki.jshaper.Clearable;
import io.github.shimeoki.jshaper.Point;
import io.github.shimeoki.jshaper.Pos;


/**
 * @author shimeoki {@link https://github.com/shimeoki}
 */
public final class MyVertex implements Clearable, Point {

    private final float[] values = new float[4];

    public MyVertex(float x, float y, float z, float w) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        this.setW(w);
    }

    public float get(Pos p) {
        return switch (p) {
            case X -> this.x();
            case Y -> this.y();
            case Z -> this.z();
            case W -> this.w();
            default -> throw new IllegalArgumentException("Invalid Pos");
        };
    }

    public void set(Pos p, float value) {
        switch (p) {
            case X:
                this.setX(value);
                break;
            case Y:
                this.setY(value);
                break;
            case Z:
                this.setZ(value);
                break;
            case W:
                this.setW(value);
                break;
            default:
                throw new IllegalArgumentException("Invalid Pos");
        }
    }

    public float x() {
        return this.values[0];
    }

    public void setX(float x) {
        this.values[0] = x;
    }

    public float y() {
        return this.values[1];
    }

    public void setY(float y) {
        this.values[1] = y;
    }

    public float z() {
        return this.values[2];
    }

    public void setZ(float z) {
        this.values[2] = z;
    }

    public float w() {
        return this.values[3];
    }

    public void setW(float w) {
        this.values[3] = w;
    }

    @Override
    public void clear() {
        this.setX(0);
        this.setY(0);
        this.setZ(0);
        this.setW(1);
    }
}
