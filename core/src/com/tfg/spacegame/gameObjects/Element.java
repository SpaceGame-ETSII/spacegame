package com.tfg.spacegame.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.GameObject;

public class Element extends GameObject {

    //Indica si el jugador ha pulsado en el elemento para colocarlo en el equipamiento
    private boolean isActivate;

    //Se muestra sobre el elemento cuando isActivate es true
    private ParticleEffect particleEffect;

    public Element(String texture, int x, int y, String particleTextureName) {
        super(texture, x, y);

        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("particleEffects/" + particleTextureName),Gdx.files.internal(""));
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
        if (isActivate) {
            this.particleEffect.draw(batch);
        }
    }

    public void update(float delta) {
        particleEffect.update(delta);
    }

    public boolean isActivate() {
        particleEffect.getEmitters().first().setPosition((getWidth()/2) + getX(), (getHeight()/2) + getY());
        return isActivate;
    }

    public void setIsActivate(boolean isActivate) {
        this.isActivate = isActivate;
    }

}
