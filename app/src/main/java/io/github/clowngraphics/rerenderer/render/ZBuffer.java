package io.github.clowngraphics.rerenderer.render;

import java.util.Arrays;



public class ZBuffer {

    private final int width;

    private final int height;

    private final double[][] zBuffer;

    public ZBuffer(int width, int height) {

        this.width = width;
        this.height = height;
        zBuffer = new double[width][height];
        clear();
    }

    public boolean isDrawable(int x, int y, double z) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }
        if (z < zBuffer[x][y]) {
            zBuffer[x][y] = z;
            return true;
        }
        return false;
    }


    public void clear() {
        for (double[] row : zBuffer) {
            Arrays.fill(row, Float.MAX_VALUE);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
