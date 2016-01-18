package com.tfg.spacegame;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameObject {

    private Texture texture;
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

    public void setX(float x) {
        logicShape.x = x;
    }

    public void setY(float y) {
        logicShape.y = y;
    }

    public Rectangle getLogicShape() {
        return logicShape;
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, logicShape.x, logicShape.y);
    }

    public void dispose() {
        texture.dispose();
    }

    public boolean isOverlapingWith(GameObject g){
        return logicShape.overlaps(g.getLogicShape());
    }

    public boolean isOverlapingWith(float x, float y) {
        float xLimit = getX() + getWidth();
        float yLimit = getY() + getHeight();
        return (x >= getX() && x <= xLimit && y >= getY() && y <= yLimit);
    }

}
