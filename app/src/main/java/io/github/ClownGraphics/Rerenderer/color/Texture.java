package io.github.ClownGraphics.Rerenderer.color;


import io.github.ClownGraphics.Rerenderer.math.Barycentric;

public interface Texture {
    public ColorRGB get(final Barycentric b);
}

