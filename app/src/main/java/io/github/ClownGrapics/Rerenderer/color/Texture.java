package io.github.ClownGrapics.Rerenderer.color;


import io.github.ClownGrapics.Rerenderer.math.Barycentric;

public interface Texture {
    public ColorRGB get(final Barycentric b);
}

