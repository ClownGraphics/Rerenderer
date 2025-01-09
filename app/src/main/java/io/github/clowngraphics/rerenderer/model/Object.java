package io.github.clowngraphics.rerenderer.model;

import io.github.clowngraphics.rerenderer.math.affine_transform.Axis;
import io.github.clowngraphics.rerenderer.model.transform.ModelTransform;

public interface Object {
    ModelTransform getTransform();

    default void scale(float s, Axis axis){
        getTransform().scale(s, axis);
    }
    default void rotate(float angle, Axis axis){
        getTransform().rotate(angle, axis);
    }
    default void translate(float t, Axis axis){
        getTransform().translate(t, axis);
    }

    default void setAngle(float angle, Axis axis){
        getTransform().setAngle(angle, axis);
    }
    default void setScale(float ds, Axis axis){
        getTransform().setScale(ds, axis);
    }
    default void setPosition(float dt, Axis axis){
        getTransform().setTranslation(dt, axis);
    }
}
