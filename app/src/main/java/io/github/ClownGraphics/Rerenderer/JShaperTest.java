package io.github.ClownGraphics.Rerenderer;

import io.github.shimeoki.jshaper.ShaperError;
import io.github.shimeoki.jshaper.obj.ModelReader;
import io.github.shimeoki.jshaper.obj.Reader;

import java.io.File;

public class JShaperTest {

    public static void main(String[] args) throws ShaperError {
        System.out.println("Amogus");
        Reader mr = new ModelReader();
        File file = new File("C:/Users/user/Downloads/NonManifold.obj");
        mr.read(file);
    }
}
