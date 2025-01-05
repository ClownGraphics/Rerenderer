package io.github.clowngraphics.rerenderer.render;

import io.github.clowngraphics.rerenderer.math.Barycentric;

public interface Texture {
    public ColorRGB get(final Barycentric b);
}
