package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4;
import io.github.alphameo.linear_algebra.mat.Mat4Math;
import io.github.alphameo.linear_algebra.mat.Matrix4;

import java.util.ArrayList;
import java.util.List;

public class GeneralTransformation implements Transformation{
    private List<Transformation> transformations = new ArrayList<>();
    private Matrix4 transformationMatrix;


    public  void add(Transformation t){
        transformations.add(t);
    }
    public void remove(int i){
        transformations.remove(i);
    }
    public void remove(Transformation t){
        transformations.remove(t);
    }

    private void recalculateMatrix(){
        for(Transformation t: transformations){
            transformationMatrix = Mat4Math.prod(transformationMatrix, t.getMatrix());
        }
    }
    @Override
    public Matrix4 getMatrix() {
        recalculateMatrix();
        return transformationMatrix;
    }
}
