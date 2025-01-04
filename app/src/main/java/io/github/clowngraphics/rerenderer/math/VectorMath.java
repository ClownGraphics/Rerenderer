package io.github.clowngraphics.rerenderer.math;

import io.github.clowngraphics.rerenderer.triangulation.Vector2f;

public final class VectorMath {

    public static final double EPSILON = 0.000000001;

    private VectorMath() {
        throw new UnsupportedOperationException("Cannot be instantiated.");
    }

    public static float crossProduct(Vector2f a, Vector2f b, Vector2f c) {
        float dx1 = b.x() - a.x();
        float dy1 = b.y() - a.y();
        float dx2 = c.x() - a.x();
        float dy2 = c.y() - a.y();

        return dx1 * dy2 - dx2 * dy1;
    }

    public static boolean isPointInTriangle(Vector2f a, Vector2f b, Vector2f c, Vector2f p) {
        float check1 = crossProduct(a, b, p);
        float check2 = crossProduct(p, b, c);
        float check3 = crossProduct(p, c, a);

        return (check1 >= -EPSILON && check2 >= -EPSILON && check3 >= -EPSILON)
                || (check1 <= EPSILON && check2 <= EPSILON && check3 <= EPSILON);
    }

    public static float edgeLength(Vector2f a, Vector2f b) {
        float dx = a.x() - b.x();
        float dy = a.y() - b.y();
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}
