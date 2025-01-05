package io.github.clowngraphics.rerenderer.render;

import java.util.Arrays;

public class ZBuffer {

    private int width;

    private int height;

    private float[][] depthsM;

    public ZBuffer(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Dimensions are invalid");
        }

        this.width = width;
        this.height = height;

        depthsM = new float[height][width];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }



    public boolean draw(int x, int y, int z) {
        if (x >= getWidth() || y >= getHeight() || x < 0 || y < 0) {
            throw new IllegalArgumentException("Coordinates are outside of dimensions box");
        }

        if (depthsM[x][y] < z) {
            return false;
        }

        depthsM[x][y] = z;
        return true;
    }

    public void clear() {
        Arrays.fill(depthsM, 2);
    }
}
