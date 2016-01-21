package com.tfg.spacegame;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class GameObject {

    //Textura asociada al objeto
    private Texture texture;

    //Objeto lógico, con el que trabajaremos para interactuar con los demás elementos
    private Rectangle logicShape;

    public GameObject(String textureName, int x, int y) {
        texture = AssetsManager.loadTexture(textureName);

        logicShape = new Rectangle();
        logicShape.width = texture.getWidth();
        logicShape.height = texture.getHeight();
        logicShape.x = x;
        logicShape.y = y;
    }

    public float getWidth() {
        return logicShape.getWidth();
    }

    public float getHeight() {
        return logicShape.getHeight();
    }

    public float getX() {
        return logicShape.getX();
    }

    public float getY() {
        return logicShape.getY();
    }

    public void setX(float x) { logicShape.x = x; }

    public void setY(float y) { logicShape.y = y; }

    public void setTexture(String textureName) { texture = AssetsManager.loadTexture(textureName); }

    public Rectangle getLogicShape() {
        return logicShape;
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, logicShape.x, logicShape.y);
    }

    public void renderRotate(SpriteBatch batch, float n){
        batch.draw(new TextureRegion(texture), logicShape.x, logicShape.y, logicShape.getWidth()/2, logicShape.getHeight()/2,
                logicShape.getWidth(), logicShape.getHeight(), 1, 1, n);
        //batch.draw();
        //batch.draw(new TextureRegion(texture), (logicShape.x)+(logicShape.getWidth()), (logicShape.y)+(logicShape.getHeight()), (logicShape.x)+(logicShape.getWidth()/2), (logicShape.y)+(logicShape.getHeight()/2), logicShape.getWidth(), logicShape.getHeight(), 1, 1, n);
    }

    public void dispose() {
        texture.dispose();
    }

    //Indica si hay una colisión con el objeto pasado por parámetro
    public boolean isOverlapingWith(GameObject g){
        return logicShape.overlaps(g.getLogicShape());
    }

    //Indica si el objeto está sobre el píxel indicado por parámetro
    public boolean isOverlapingWith(float x, float y) {
        float xLimit = getX() + getWidth();
        float yLimit = getY() + getHeight();
        return (x >= getX() && x <= xLimit && y >= getY() && y <= yLimit);
    }

}
