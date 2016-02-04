package com.tfg.spacegame.utils.shapes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;

public class Rectangle extends Polygon {


    public Rectangle(float x, float y, float width, float height){
        super();

        float[] vertices = new float[8];

        vertices[0] = x;
        vertices[1] = y;

        vertices[2] = x;
        vertices[3] = y+height;

        vertices[4] = x+width;
        vertices[5] = y+height;

        vertices[6] = x+width;
        vertices[7] = y;


    }

    public float getX(){
        return this.getVertices()[0];
    }
    public float getY(){
        return this.getVertices()[1];
    }

    public float getWidth(){
        float result;
        if(getVertices()[6] > getVertices()[4])
            result = getVertices()[6];
        else
            result = getVertices()[4];

        if(getVertices()[2] < getVertices()[1])
            result-=getVertices()[2];
        else
            result-=getVertices()[4];

        return result;
    }

    public float getHeight(){
        float result;

        if(getVertices()[3] > getVertices()[5])
            result = getVertices()[3];
        else
            result = getVertices()[5];

        if(getVertices()[1] < getVertices()[7])
            result -= getVertices()[1];
        else
            result -= getVertices()[7];
        
        return  result;
    }

}
