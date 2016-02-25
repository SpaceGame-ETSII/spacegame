package com.tfg.spacegame;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.tfg.spacegame.utils.AssetsManager;

import java.util.Arrays;

public class GameObject {

    //Textura asociada al objeto
    private Texture texture;

    //Objeto lógico, con el que trabajaremos para interactuar con los demás elementos
    private Polygon logicShape;

    private float width;
    private float height;

    public GameObject(String textureName, int x, int y) {
        texture = AssetsManager.loadTexture(textureName);

        float[] vertices = SpaceGame.loadShape(textureName);

        if(vertices == null){
            System.out.println(textureName);
            vertices = new float[8];

            vertices[0] = 0;
            vertices[1] = 0;

            vertices[2] = 0;
            vertices[3] = texture.getHeight();

            vertices[4] = texture.getWidth();
            vertices[5] = texture.getHeight();

            vertices[6] = texture.getWidth();
            vertices[7] = 0;
        }
        logicShape = new Polygon(vertices);
        logicShape.setPosition(x,y);

        loadWidthAndHeight();
    }

    private void loadWidthAndHeight(){

        float widthLowestPoint = logicShape.getVertices()[0];
        float widthGreaterPoint = logicShape.getVertices()[0];

        float heightLowestPoint = logicShape.getVertices()[1];
        float heightGreaterPoint = logicShape.getVertices()[1];

        for(int i = 2; i < logicShape.getVertices().length; i++){

            if(i%2 == 0){
                if(logicShape.getVertices()[i] < widthLowestPoint)
                    widthLowestPoint = logicShape.getVertices()[i];

                if(logicShape.getVertices()[i] > widthGreaterPoint)
                    widthGreaterPoint = logicShape.getVertices()[i];
            }else{
                if(logicShape.getVertices()[i] < heightLowestPoint)
                    heightLowestPoint = logicShape.getVertices()[i];

                if(logicShape.getVertices()[i] > heightGreaterPoint)
                    heightGreaterPoint = logicShape.getVertices()[i];
            }
        }

        width = widthGreaterPoint - widthLowestPoint;
        height = heightGreaterPoint - heightLowestPoint;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getX() {
        return logicShape.getX();
    }

    public float getY() {
        return logicShape.getY();
    }

    public void setX(float x) {
        logicShape.setPosition( x , logicShape.getY());
    }

    public void setY(float y) {
        logicShape.setPosition(logicShape.getX(), y);
    }

    public Texture getTexture(){
        return texture;
    }

    public void setTexture(String textureName) { texture = AssetsManager.loadTexture(textureName); }

    public Polygon getLogicShape() {
        return logicShape;
    }

    public void setScale(float x, float y){
        logicShape.setScale(x,y);
    }
    public void setRotation(float angle){
        logicShape.setRotation(angle);
    }
    public void setOrigin(float x,float y){
        logicShape.setOrigin(x,y);
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, getX(), getY());
    }

    //Método para printar un objeto rotando N grados su textura
    public void renderRotate(SpriteBatch batch, float n){
        batch.draw(new TextureRegion(texture), getX(), getY(), getWidth()/2, getHeight()/2, getWidth(), getHeight(), 1, 1, n);
    }

    public void dispose() {
        texture.dispose();
    }

    //Indica si hay una colisión con el objeto pasado por parámetro
    public boolean isOverlapingWith(GameObject g) {
        return Intersector.overlapConvexPolygons(this.getLogicShape(),g.getLogicShape());
    }

    //Indica si el objeto está sobre el píxel indicado por parámetro
    public boolean isOverlapingWith(float x, float y) {
        return getLogicShape().contains(x,y);
    }
}