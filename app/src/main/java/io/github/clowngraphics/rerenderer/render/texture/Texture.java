package io.github.clowngraphics.rerenderer.render.texture;

import io.github.clowngraphics.rerenderer.math.Barycentric;

import java.util.List;

public interface Texture {

    ColorRGB get(Barycentric b, List<Integer> tvIndecies);

}
