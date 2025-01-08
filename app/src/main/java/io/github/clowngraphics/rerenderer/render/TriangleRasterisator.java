package io.github.clowngraphics.rerenderer.render;

import io.github.clowngraphics.rerenderer.math.Barycentric;
import io.github.clowngraphics.rerenderer.math.Triangle;
import io.github.clowngraphics.rerenderer.math.Utils;
import io.github.clowngraphics.rerenderer.render.texture.ColorRGB;
import io.github.clowngraphics.rerenderer.render.texture.Texture;
import javafx.geometry.Point3D;
import javafx.scene.image.PixelWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class TriangleRasterisator {

    private PixelWriter pixelWriter;

    private ZBuffer zBuffer;

    public TriangleRasterisator(PixelWriter pixelWriter, ZBuffer zBuffer) {
        this.pixelWriter = pixelWriter;
        this.zBuffer = zBuffer;
    }

    public PixelWriter getPixelWriter() {
        return pixelWriter;
    }

    public void setPixelWriter(PixelWriter pixelWriter) {
        this.pixelWriter = pixelWriter;
    }

    private List<Point3D> sortedVertices(final Triangle t) {
        final List<Point3D> vertices = new ArrayList<>();
        vertices.add(t.getPoint1());
        vertices.add(t.getPoint2());
        vertices.add(t.getPoint3());

        vertices.sort(Comparator.comparing(Point3D::getY).thenComparing(Point3D::getX));
        return vertices;
    }

    private void drawFlat(final Triangle t, final Point3D lone, final Point3D flat1, final Point3D flat2) {
        final double lx = lone.getX();
        final double ly = lone.getY();

        // "delta flat x1"
        final double deltaFlatX1 = flat1.getX() - lx;
        final double deltaFlatY1 = flat1.getY() - ly;

        final double deltaFlatX2 = flat2.getX() - lx;
        final double deltaFlatY2 = flat2.getY() - ly;

        double deltaX1 = deltaFlatX1 / deltaFlatY1;
        double deltaX2 = deltaFlatX2 / deltaFlatY2;

        final double flatY = flat1.getY();
        if (Utils.moreThan(ly, flatY)) {
            drawBottom(t, lone, flatY, deltaX1, deltaX2);
        } else {
            drawTop(t, lone, flatY, deltaX1, deltaX2);
        }
    }

    private void drawTop(final Triangle t, final Point3D v, final double maxY, final double dx1, final double dx2) {

        double x1 = v.getX();
        double x2 = x1;

        for (int y = (int) v.getY(); y <= maxY; y++) {
            drawHLine(t, (int) x1, (int) x2, (int) v.getZ(), y);
            x1 += dx1;
            x2 += dx2;
        }
    }

    private void drawBottom(final Triangle t, final Point3D v, final double minY, final double dx1, final double dx2) {

        double x1 = v.getX();
        double x2 = x1;

        for (int y = (int) v.getY(); y > minY; y--) {
            drawHLine(t, (int) x1, (int) x2, (int) v.getZ(), y);

            x1 -= dx1;
            x2 -= dx2;
        }
    }
    // TODO Правильно? -- @Fiecher
    private void drawHLine(final Triangle t, final int x1, final int x2, final int z, final int y) {

        for (int x = (int) x1; x <= x2; x++) {
            final Barycentric b;

            try {
                b = t.barycentrics(new Point3D(x, y, z));
            } catch (Exception e) {
                continue;
            }

            if (!b.isInside()) {
                continue;
            }

            ColorRGB color = t.getTexture().get(b);
            if (zBuffer.isDrawable(x, y, z)) {
                pixelWriter.setColor(x, y, color.convertToJFXColor());
            }
        }
    }

    public void draw(Point3D p1, Point3D p2, Point3D p3, Texture texture, RenderType renderType) {
        Objects.requireNonNull(p1);
        Objects.requireNonNull(p2);
        Objects.requireNonNull(p3);

        if (renderType == RenderType.WIREFRAME) {
            drawEdges(p1, p2, p3);
            return;
        }

        if (renderType == RenderType.SOLID) {

            Triangle t = new Triangle(p1, p2, p3, texture);
            List<Point3D> vertices = sortedVertices(t);

            Point3D v1 = vertices.get(0);
            Point3D v2 = vertices.get(1);
            Point3D v3 = vertices.get(2);

            double x1 = v1.getX();
            double y1 = v1.getY();

            double x2 = v2.getX();
            double y2 = v2.getY();

            double x3 = v3.getX();
            double y3 = v3.getY();

            if (Utils.equals(y2, y3)) {
                drawFlat(t, v1, v2, v3);
                return;
            }

            if (Utils.equals(y1, y2)) {
                drawFlat(t, v3, v1, v2);
                return;
            }

            final double x4 = x1 + ((y2 - y1) / (y3 - y1)) * (x3 - x1);
            final Point3D v4 = new Point3D(x4, v2.getY(), v2.getZ());

            if (Utils.moreThan(x4, x2)) {
                drawFlat(t, v1, v2, v4);
                drawFlat(t, v3, v2, v4);
            } else {
                drawFlat(t, v1, v4, v2);
                drawFlat(t, v3, v4, v2);
            }
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

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            pixelWriter.setColor(x1, y1, javafx.scene.paint.Color.BLACK);

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
        }
    }
}
