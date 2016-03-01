package com.tfg.spacegame.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.screens.CampaignScreen;
import com.tfg.spacegame.utils.FontManager;

public class Button extends GameObject {

    //Indica si el botón ha sido pulsado
    private boolean pressed;

    //Será el Screen que tenga asociado el botón. Si no tiene ninguno, deberá ser null
    private Screen screen;

    //Será el texto que habrá en el botón, en el caso de haberlo
    private String content;

    //Almacena la posición del content
    private float contentX;
    private float contentY;

    public Button(String texture, Screen screen, int x, int y, String content) {
        super(texture, x, y);

        this.screen = screen;
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

    public boolean isPressed() {
        return pressed;
    }

    public boolean press(float x, float y) {
        if (super.isOverlapingWith(x, y)) {
            this.setPressed(true);
            if (screen instanceof CampaignScreen)
                ((CampaignScreen) screen).loadLevel(content);
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

        if (content != null) {
            FontManager.drawText(SpaceGame.batch, FontManager.text,content,contentX,contentY);
        }
    }

}
