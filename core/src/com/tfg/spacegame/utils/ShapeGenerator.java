package com.tfg.spacegame.utils;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.tfg.spacegame.utils.shapes.Rectangle;

public class ShapeGenerator {

    public static Polygon getRectangle(Texture texture){
        float[] vertices = new float[8];
        vertices[0] = 0;
        vertices[1] = 0;

        vertices[2] = 0;
        vertices[3] = texture.getHeight();

        vertices[4] = texture.getWidth();
        vertices[5] = texture.getHeight();

        vertices[6] = texture.getWidth();
        vertices[7] = 0;

        return new Polygon(vertices);

    }
}
