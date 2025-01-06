package io.github.clowngraphics.rerenderer.model.transform;

import io.github.clowngraphics.rerenderer.math.affine_transform.GeneralRotation;
import io.github.clowngraphics.rerenderer.math.affine_transform.Scale;
import io.github.clowngraphics.rerenderer.math.affine_transform.Translation;
import io.github.clowngraphics.rerenderer.model.Model;

public class ModelTransform {
    GeneralRotation generalRotation;
    Translation translation;
    Scale scale;
}
