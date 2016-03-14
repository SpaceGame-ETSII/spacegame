package com.tfg.spacegame.gameObjects.arcadeMode;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.utils.FontManager;
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

    public void render(SpriteBatch batch) {
        this.renderRotate(batch, degrees);
        ShapeRendererManager.easyRenderPolygon(this);
    }

    public void renderTransparent(SpriteBatch batch) {
        Color c = batch.getColor();
        float oldAlpha = c.a;

        c.a = 0.3f;
        batch.setColor(c);

        this.renderRotate(batch, degrees / 2);

        c.a = oldAlpha;
        batch.setColor(c);
    }

    public void dispose() {
        super.dispose();
    }

}