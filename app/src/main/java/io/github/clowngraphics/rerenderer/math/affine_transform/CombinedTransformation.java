package io.github.clowngraphics.rerenderer.math.affine_transform;

import io.github.alphameo.linear_algebra.mat.Mat4Math;
import io.github.alphameo.linear_algebra.mat.Matrix4;

import java.util.ArrayList;
import java.util.List;

public class CombinedTransformation extends GeneralTransformation{
    private List<Transformation> transformations = new ArrayList<>();


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
            setMatrix(Mat4Math.prod(getMatrix(), t.getMatrix()));
        }
    }

}
