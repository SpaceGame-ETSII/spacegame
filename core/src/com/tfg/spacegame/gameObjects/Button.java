package com.tfg.spacegame.gameObjects;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.GameObject;

public class Button extends GameObject {

    //Indica si el botón ha sido pulsado
    private boolean pressed;

    //Será el Screen que tenga asociado el botón. Si no tiene ninguno, deberá ser null
    private Screen screen;

    public Button(String texture, Screen screen, int x, int y) {
        super(texture, x, y);

        this.screen = screen;
        pressed = false;
    }

    public boolean isPressed() {
        return pressed;
    }

    public boolean press(float x, float y) {
        if (super.isOverlapingWith(x, y)) {
            this.setPressed(true);
            return true;
        } else {
            return false;
        }
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    //Crea y devuelve un S
    public Screen getScreen() {
        return screen;
    }

    //Si el botón está pulsado, girará la textura 180 grados
    public void render(SpriteBatch batch) {
        if (!this.isPressed()) {
            super.render(batch);
        } else {
            super.renderRotate(batch, 180);
        }

    }

}
