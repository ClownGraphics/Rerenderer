package io.github.clowngraphics.rerenderer.render.texture;

import io.github.alphameo.linear_algebra.vec.Vector2;
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
    private final PolygonUVCoordinates polygonUVCoordinates;

    private ImageTexture(final BufferedImage image, PolygonUVCoordinates polygonUVCoordinates) {
        this.texture = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.polygonUVCoordinates = polygonUVCoordinates;
    }



    public ColorRGB get(final float x, final float y) {

        if (x < -1 || y < -1 || x > 1 || y > 1) {
            throw new IllegalArgumentException("Coordinates has to be in [-1, 1]");
        }

        int pixelX = Math.min(Math.max(0, (int) ((x + 1) / 2 * width)), width - 1);
        int pixelY = Math.min(Math.max(0, (int) ((y + 1) / 2 * height)), height - 1);

        int color = texture.getRGB(pixelX, pixelY);
        System.out.println(color);

        float alpha = ((color >> 24) & 0xFF) / 255f;
        float red = ((color >> 16) & 0xFF) / 255f;
        float green = ((color >> 8) & 0xFF) / 255f;
        float blue = (color & 0xFF) / 255f;

        System.out.println(new ColorRGB(red, green, blue, alpha).convertToJFXColor().toString());
        return new ColorRGB(red, green, blue, alpha);
    }



    public ColorRGB get(Barycentric b) {

        Vector2 interpolatedUV = polygonUVCoordinates.barycentric(b);

        float u = interpolatedUV.x();
        float v = interpolatedUV.y();

        int pixelX = Math.min((int) (u * (width - 1)), width - 1);
        int pixelY = Math.min((int) (v * (height - 1)), height - 1);

        int color = texture.getRGB(pixelX, pixelY);


        float alpha = ((color >> 24) & 0xFF) / 255f;
        float red = ((color >> 16) & 0xFF) / 255f;
        float green = ((color >> 8) & 0xFF) / 255f;
        float blue = (color & 0xFF) / 255f;

        return new ColorRGB(red, green, blue, alpha);
    }


    public static ImageTexture setFromFile(final File file, PolygonUVCoordinates polygonUvCoordinates) {
        Objects.requireNonNull(file);
        final BufferedImage image;
        try {
            image = ImageIO.read(file);
        } catch (IllegalArgumentException | IOException e) {
            throw new IllegalArgumentException("Unable to read texture");
        }

        return new ImageTexture(image, polygonUvCoordinates);
    }

}
