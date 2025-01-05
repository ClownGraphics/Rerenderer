package io.github.clowngraphics.rerenderer.render.color;

import io.github.clowngraphics.rerenderer.math.Barycentric;

public interface Texture {



    public ColorRGB get(final Barycentric b);

    public ColorRGB get(final float x, final float y);
}
