package io.github.clowngraphics.rerenderer.render.texture;

import io.github.alphameo.linear_algebra.vec.Vector2;
import io.github.clowngraphics.rerenderer.math.Barycentric;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ImageTexture implements Texture {

    private BufferedImage texture;
    private final int width;
    private final int height;
    private List<Vector2> textureVertices;

    private ImageTexture(final BufferedImage image) {
        this.texture = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public ColorRGB get(final float x, final float y) {
        if (x < -1 || y < -1 || x > 1 || y > 1) {
            throw new IllegalArgumentException("Coordinates must be in [-1, 1]");
        }

        float u = (x + 1) / 2;
        float v = (y + 1) / 2;

        int pixelX = Math.min((int) (u * (width - 1)), width - 1);
        int pixelY = Math.min((int) (v * (height - 1)), height - 1);

        int color = texture.getRGB(pixelX, pixelY);

        float alpha = ((color >> 24) & 0xFF) / 255f;
        float red = ((color >> 16) & 0xFF) / 255f;
        float green = ((color >> 8) & 0xFF) / 255f;
        float blue = (color & 0xFF) / 255f;

        return new ColorRGB(red, green, blue, alpha);
    }

    @Override
    public ColorRGB get(Barycentric b) {
        // Interpolate UV coordinates using barycentric weights
        float u = (float) (
                b.getLambda1() * textureVertices.get(0).x() +
                        b.getLambda2() * textureVertices.get(1).x() +
                        b.getLambda3() * textureVertices.get(2).x()
        );

        float v = (float) (
                b.getLambda1() * textureVertices.get(0).y() +
                        b.getLambda2() * textureVertices.get(1).y() +
                        b.getLambda3() * textureVertices.get(2).y()
        );

        int pixelX = Math.min((int) (u * (width - 1)), width - 1);
        int pixelY = Math.min((int) (v * (height - 1)), height - 1);

        int color = texture.getRGB(pixelX, pixelY);

        float alpha = ((color >> 24) & 0xFF) / 255f;
        float red = ((color >> 16) & 0xFF) / 255f;
        float green = ((color >> 8) & 0xFF) / 255f;
        float blue = (color & 0xFF) / 255f;

        return new ColorRGB(red, green, blue, alpha);
    }

    public static ImageTexture setFromFile(final File file) {
        Objects.requireNonNull(file);
        final BufferedImage image;
        try {
            image = ImageIO.read(file);
        } catch (IllegalArgumentException | IOException e) {
            throw new IllegalArgumentException("Unable to read texture", e);
        }

        return new ImageTexture(image);
    }
}
