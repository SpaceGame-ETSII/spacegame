package com.tfg.spacegame.GameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.GameObject;

public class Inventary extends GameObject {

    private Element red;
    private Element yellow;
    private Element blue;

    private final static int TRANSITION_SPEED = 900;
    private float relativePos;

    public Inventary() {
        super("inventary", 0, 0);

        red = new Element("red", 0, 0);
        yellow = new Element("yellow", 0, 0);
        blue = new Element("blue", 0, 0);

        relativePos = 0.0f;
    }

    public void restart() {
        red.setX(32);
        red.setY(350);
        yellow.setX(31);
        yellow.setY(193);
        blue.setX(29);
        blue.setY(39);

        this.setX(this.getX() - this.getWidth());
        red.setX(red.getX() - this.getWidth());
        yellow.setX(yellow.getX() - this.getWidth());
        blue.setX(blue.getX() - this.getWidth());
    }

    public void render(SpriteBatch batch) {
        super.render(batch);

        red.render(batch);
        yellow.render(batch);
        blue.render(batch);
    }

    public void update(float delta) {
        if (this.getX() < 0) {
            relativePos = TRANSITION_SPEED * delta;

            this.setX(this.getX() + relativePos);
            red.setX(red.getX() + relativePos);
            yellow.setX(yellow.getX() + relativePos);
            blue.setX(blue.getX() + relativePos);
        }
    }

}
