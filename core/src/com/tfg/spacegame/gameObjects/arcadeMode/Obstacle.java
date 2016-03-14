package com.tfg.spacegame.gameObjects.arcadeMode;

import com.badlogic.gdx.graphics.Color;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.utils.ShapeRendererManager;

public class Obstacle extends GameObject {

    private final static int SPEED = 100;

    private float degrees;

    public Obstacle(String textureName, int x, int y) {
        super(textureName, x, y);

        degrees = 0;
    }

    public void update(float delta) {
        this.setRotation(degrees);
        degrees += SPEED * delta;
        this.setY(this.getY() - (SPEED * delta));
    }

    public void render() {
        this.renderRotate(degrees);
    }

    public void renderTransparent() {
        Color c = SpaceGame.batch.getColor();
        float oldAlpha = c.a;

        c.a = 0.3f;
        SpaceGame.batch.setColor(c);

        this.renderRotate(degrees / 2);

        c.a = oldAlpha;
        SpaceGame.batch.setColor(c);
    }

    public void dispose() {
        super.dispose();
    }

}