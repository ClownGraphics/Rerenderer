package io.github.clowngraphics.rerenderer.render.texture;

import io.github.clowngraphics.rerenderer.math.Barycentric;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ImageTexture implements Texture{

    private BufferedImage texture;
    private final int width;
    private final int height;

    private ImageTexture(final BufferedImage image) {
        texture = image;
        width = image.getWidth();
        height = image.getHeight();
    }
    @Override
    public ColorRGB get(final float x, final float y) {

        if (x < -1 || y < -1 || x > 1 || y > 1) {
            throw new IllegalArgumentException("Coordinates has to be in [-1, 1]");
        }

        int pixelX = (int) ((x + 1) / 2 * width);
        int pixelY = (int) ((y + 1) / 2 * height);

        int color = texture.getRGB(pixelX, pixelY);
        System.out.println(color);

        float alpha = ((color >> 24) & 0xFF) / 255f;
        float red = ((color >> 16) & 0xFF) / 255f;
        float green = ((color >> 8) & 0xFF) / 255f;
        float blue = (color & 0xFF) / 255f;

        System.out.println(new ColorRGB(red, green, blue, alpha).convertToJFXColor().toString());
        return new ColorRGB(red, green, blue, alpha);
    }
    @Override
    public ColorRGB get(Barycentric b) {
        return new ColorRGB(0,0,0,0);
    }


    public static ImageTexture setFromFile(final File file) {
        Objects.requireNonNull(file);
        final BufferedImage image;
        try {
            image = ImageIO.read(file);
        } catch (IllegalArgumentException | IOException e) {
            throw new IllegalArgumentException("Unable to read texture");
        }

        return new ImageTexture(image);
    }

}
