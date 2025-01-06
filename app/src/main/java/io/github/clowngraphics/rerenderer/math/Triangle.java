package io.github.clowngraphics.rerenderer.math;

import io.github.clowngraphics.rerenderer.render.texture.Texture;
import javafx.geometry.Point2D;

import java.util.Objects;

public class Triangle {

    private Point2D point1;
    private Point2D point2;
    private Point2D point3;

    private Texture texture;

    public Triangle(Point2D point1, Point2D point2, Point2D point3, Texture texture) {

        Objects.requireNonNull(point1);
        Objects.requireNonNull(point2);
        Objects.requireNonNull(point3);
        Objects.requireNonNull(texture);

        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Point2D getPoint1() {
        return point1;
    }

    public Point2D getPoint2() {
        return point2;
    }

    public Point2D getPoint3() {
        return point3;
    }

    public float x3() {
        return (float) point3.getX();
    }

    public float y3() {
        return (float) point3.getY();
    }

    public Barycentric barycentrics(final Point2D p) {

        final float x = (float) p.getX();
        final float y = (float) p.getY();

        final float x1 = (float) getPoint1().getX();
        final float y1 = (float) getPoint1().getY();

        final float x2 = (float) getPoint2().getX();
        final float y2 = (float) getPoint2().getY();

        final float x3 = (float) getPoint3().getX();
        final float y3 = (float) getPoint3().getY();

        final float n1 = (y2 - y3) * (x - x3) + (x3 - x2) * (y - y3);
        final float n2 = (y3 - y1) * (x - x3) + (x1 - x3) * (y - y3);
        final float n3 = (y1 - y2) * (x - x1) + (x2 - x1) * (y - y1);

        final float d = 1 / ((y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3));

        final float l1 = n1 * d;
        final float l2 = n2 * d;
        final float l3 = n3 * d;

        return new Barycentric(l1, l2, l3);
    }

}
