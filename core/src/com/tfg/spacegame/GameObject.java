package com.tfg.spacegame;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.tfg.spacegame.utils.AssetsManager;

public class GameObject {

    //Textura asociada al objeto
    private Texture texture;

    //Objeto lógico, con el que trabajaremos para interactuar con los demás elementos
    private Polygon logicShape;

    public GameObject(String textureName, int x, int y) {
        texture = AssetsManager.loadTexture(textureName);

        float vertices[] = new float[8];
        vertices[0] = 0;
        vertices[1] = 0;

        vertices[2] = 0;
        vertices[3] = texture.getHeight();

        vertices[4] = texture.getWidth();
        vertices[5] = texture.getHeight();

        vertices[6] = texture.getWidth();
        vertices[7] = 0;

        logicShape = new Polygon(vertices);
        logicShape.setPosition(x,y);
    }

    public float getWidth() {
        return logicShape.getVertices()[6] - logicShape.getVertices()[0];
    }

    public float getHeight() {
        return logicShape.getVertices()[5] - logicShape.getVertices()[7];
    }

    public float getX() {
        return logicShape.getX();
    }

    public float getY() {
        return logicShape.getY();
    }

    public void setX(float x) {
        logicShape.setPosition(x,getY());
    }

    public void setY(float y) {
        logicShape.setPosition(getX(),y);
    }

    public Texture getTexture(){
        return texture;
    }

    public void setTexture(String textureName) { texture = AssetsManager.loadTexture(textureName); }

    public Polygon getLogicShape() {
        return logicShape;
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
        return this.getLogicShape().getBoundingRectangle().overlaps(g.getLogicShape().getBoundingRectangle());
    }

    //Indica si el objeto está sobre el píxel indicado por parámetro
    public boolean isOverlapingWith(float x, float y) {
        return this.getLogicShape().contains(x,y);
    }

}
