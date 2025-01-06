package io.github.clowngraphics.rerenderer.render.texture;

import io.github.clowngraphics.rerenderer.math.Barycentric;

public interface Texture {

    ColorRGB get(Barycentric b);

}
