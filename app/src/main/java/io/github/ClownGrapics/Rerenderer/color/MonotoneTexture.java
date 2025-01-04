package io.github.ClownGrapics.Rerenderer.color;


import io.github.ClownGrapics.Rerenderer.math.Barycentric;
import javafx.scene.paint.Color;
import java.util.Objects;

public class MonotoneTexture implements Texture {
    private final ColorRGB color;

    public MonotoneTexture(final ColorRGB color) {
        Objects.requireNonNull(color);

        this.color = color;
    }

    public MonotoneTexture(final Color color) {
        Objects.requireNonNull(color);

        this.color = new ColorRGB(color);
    }

    @Override
    public ColorRGB get(final Barycentric b) {
        Objects.requireNonNull(b);

        return color;
    }
}
