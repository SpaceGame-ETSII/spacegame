package com.tfg.spacegame.gameObjects.arcadeMode;

import com.badlogic.gdx.graphics.Color;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.utils.ShapeRendererManager;

public class Obstacle extends GameObject {

    private final static int SPEED = 100;

    private float degrees;

    private boolean gira;

    public Obstacle(String textureName, int x, int y, boolean gira) {
        super(textureName, x, y);

        degrees = 0;

        this.gira = gira;
    }

    public void update(float delta) {
        if (gira) {
            this.setRotation(degrees);
            degrees += SPEED * delta;
        } else {
            this.setY(this.getY() - (SPEED * delta));
        }
    }

    public void render() {
        this.renderRotate(degrees);
        ShapeRendererManager.renderPolygon(this.getLogicShape().getTransformedVertices(), Color.BLUE);
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