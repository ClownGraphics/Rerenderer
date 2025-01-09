package io.github.clowngraphics.rerenderer.math;

public class Barycentric {

    private final double lambda1;
    private final double lambda2;
    private final double lambda3;
    private final boolean inside;

    public Barycentric(final double lambda1, final double lambda2, final double lambda3) {
        this.lambda1 = lambda1;
        this.lambda2 = lambda2;
        this.lambda3 = lambda3;
        this.inside = lambda1 >= 0 && lambda2 >= 0 && lambda3 >= 0;
    }

    public double getLambda1() {
        return lambda1;
    }

    public double getLambda2() {
        return lambda2;
    }

    public double getLambda3() {
        return lambda3;
    }

    public boolean isInside() {
        return inside;
    }
}
