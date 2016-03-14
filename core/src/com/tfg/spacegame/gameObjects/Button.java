package com.tfg.spacegame.gameObjects;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.utils.FontManager;

public class Button extends GameObject {

    //Indica si el botón ha sido pulsado
    private boolean pressed;

    //Será el texto que habrá en el botón, en el caso de haberlo
    private String content;

    //Almacena la posición del content
    private float contentX;
    private float contentY;

    public Button(String texture, int x, int y, String content) {
        super(texture, x, y);

        pressed = false;
        this.content = content;

        //Si tenemos un texto para contenido, lo centramos en el botón
        if (content != null) {
            GlyphLayout layout = new GlyphLayout();
            layout.setText(FontManager.text, FontManager.getFromBundle(content));
            contentX = x + (this.getWidth() - layout.width) / 2;
            contentY = y + (this.getHeight() + layout.height) / 2;
        }
    }

    public String getContent() {
        return content;
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

    //Si el botón está pulsado, girará la textura 180 grados
    public void render() {
        if (!this.isPressed()) {
            super.render();
        } else {
            super.renderRotate(180);
        }

        if (content != null) {
            FontManager.drawText(content,contentX,contentY);
        }
    }

}
