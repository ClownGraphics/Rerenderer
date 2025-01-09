package io.github.clowngraphics.rerenderer.render;

import io.github.clowngraphics.rerenderer.math.Barycentric;
import io.github.clowngraphics.rerenderer.math.Triangle;
import io.github.clowngraphics.rerenderer.math.Utils;
import io.github.clowngraphics.rerenderer.render.texture.ColorRGB;
import io.github.clowngraphics.rerenderer.render.texture.Texture;
import javafx.geometry.Point3D;
import javafx.scene.image.PixelWriter;
import java.util.*;

public class Rastetisator {

    private PixelWriter pixelWriter;
    private ZBuffer zBuffer;

    public Rastetisator(PixelWriter pixelWriter, ZBuffer zBuffer) {
        this.pixelWriter = pixelWriter;
        this.zBuffer = zBuffer;
    }

    private List<Point3D> sortedVertices(final Triangle t) {
        List<Point3D> vertices = Arrays.asList(t.getPoint1(), t.getPoint2(), t.getPoint3());
        vertices.sort(Comparator.comparing(Point3D::getY).thenComparing(Point3D::getX));
        return vertices;
    }

    private void drawFlat(final Triangle t, Point3D top, Point3D middle, Point3D bottom) {
        double dx1 = (middle.getX() - top.getX()) / (middle.getY() - top.getY());
        double dx2 = (bottom.getX() - top.getX()) / (bottom.getY() - top.getY());
        double dz1 = (middle.getZ() - top.getZ()) / (middle.getY() - top.getY());
        double dz2 = (bottom.getZ() - top.getZ()) / (bottom.getY() - top.getY());

        if (Utils.moreThan(top.getY(), middle.getY())) {
            drawBottom(t, top, middle.getY(), dx1, dx2, dz1, dz2);
        } else {
            drawTop(t, top, middle.getY(), dx1, dx2, dz1, dz2);
        }
    }

    private void drawTop(Triangle t, Point3D top, double maxY, double dx1, double dx2, double dz1, double dz2) {
        double x1 = top.getX();
        double x2 = x1;
        double z1 = top.getZ();
        double z2 = z1;

        for (int y = (int) top.getY(); y <= maxY; y++) {
            drawHLine(t, (int) x1, (int) x2, z1, z2, y);
            x1 += dx1;
            x2 += dx2;
            z1 += dz1;
            z2 += dz2;
        }
    }

    private void drawBottom(Triangle t, Point3D bottom, double minY, double dx1, double dx2, double dz1, double dz2) {
        double x1 = bottom.getX();
        double x2 = x1;
        double z1 = bottom.getZ();
        double z2 = z1;

        for (int y = (int) bottom.getY(); y > minY; y--) {
            drawHLine(t, (int) x1, (int) x2, z1, z2, y);
            x1 -= dx1;
            x2 -= dx2;
            z1 -= dz1;
            z2 -= dz2;
        }
    }

    private void drawHLine(Triangle t, int x1, int x2, double z1, double z2, int y) {
        if (x1 > x2) {
            int tempX = x1; x1 = x2; x2 = tempX;
            double tempZ = z1; z1 = z2; z2 = tempZ;
        }

        double dz = (z2 - z1) / (x2 - x1);
        double z = z1;

        for (int x = x1; x <= x2; x++) {
            if (zBuffer.isDrawable(x, y, z)) {
                Barycentric b = t.barycentrics(new Point3D(x, y, z));
                if (b.isInside()) {
                    ColorRGB color = t.getTexture().get(b);
                    pixelWriter.setColor(x, y, color.convertToJFXColor());
                }
            }
            z += dz;
        }
    }

    public void draw(Point3D p1, Point3D p2, Point3D p3, Texture texture, RenderType renderType) {
        Triangle t = new Triangle(p1, p2, p3, texture);
        List<Point3D> vertices = sortedVertices(t);
        Point3D v1 = vertices.get(0);
        Point3D v2 = vertices.get(1);
        Point3D v3 = vertices.get(2);

        if (Utils.equals(v2.getY(), v3.getY())) {
            drawFlat(t, v1, v2, v3);
        } else if (Utils.equals(v1.getY(), v2.getY())) {
            drawFlat(t, v3, v1, v2);
        } else {
            double x4 = v1.getX() + ((v2.getY() - v1.getY()) / (v3.getY() - v1.getY())) * (v3.getX() - v1.getX());
            double z4 = v1.getZ() + ((v2.getY() - v1.getY()) / (v3.getY() - v1.getY())) * (v3.getZ() - v1.getZ());
            Point3D v4 = new Point3D(x4, v2.getY(), z4);

            if (x4 > v2.getX()) {
                drawFlat(t, v1, v2, v4);
                drawFlat(t, v3, v2, v4);
            } else {
                drawFlat(t, v1, v4, v2);
                drawFlat(t, v3, v4, v2);
            }
        }

        if (renderType == RenderType.WIREFRAME || renderType == RenderType.BOTH) {
            drawEdges(p1, p2, p3);
        }
    }

    private void drawEdges(Point3D p1, Point3D p2, Point3D p3) {
        drawLine(p1, p2);
        drawLine(p2, p3);
        drawLine(p3, p1);
    }

    private void drawLine(Point3D p1, Point3D p2) {
        int x1 = (int) p1.getX();
        int y1 = (int) p1.getY();
        int x2 = (int) p2.getX();
        int y2 = (int) p2.getY();

        double z1 = (double) p1.getZ();
        double z2 = (double) p2.getZ();

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        double dz = dx > dy ? (z2 - z1) / dx : (z2 - z1) / dy;
        double z = z1;

        while (true) {
            if (zBuffer.isDrawable(x1, y1, z)) {
                pixelWriter.setColor(x1, y1, javafx.scene.paint.Color.BLACK);
            }

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
            z += dz;
        }
    }
}
