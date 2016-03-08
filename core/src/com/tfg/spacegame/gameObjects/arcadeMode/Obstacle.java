package com.tfg.spacegame.gameObjects.arcadeMode;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.GameObject;

public class Obstacle extends GameObject {

    private final static int SPEED = 150;

    private float degrees;

    public Obstacle(String textureName, int x, int y) {
        super(textureName, x, y);

        degrees = 0;
    }

    public void update(float delta) {
        this.setY(this.getY() - (SPEED * delta));
        this.getLogicShape().rotate(degrees);
        degrees += (SPEED * delta);
    }

    public void render(SpriteBatch batch) {
        this.renderRotate(batch, degrees);
    }

    public void dispose() {
        super.dispose();
    }

}