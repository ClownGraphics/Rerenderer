package io.github.clowngraphics.rerenderer.render.texture;


import io.github.clowngraphics.rerenderer.math.Barycentric;
import javafx.scene.paint.Color;

import java.util.List;
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

    public ColorRGB get(final Barycentric b, List<Integer> tvIndecies) {
        Objects.requireNonNull(b);
        return color;
    }


    public ColorRGB get(final float x, final float y){
        return color;
    }
}
