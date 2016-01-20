package com.tfg.spacegame.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.AssetsManager;
import com.tfg.spacegame.GameObject;

public class Element extends GameObject {

    //Indica si el jugador ha pulsado en el elemento para colocarlo en el equipamiento
    private boolean isActivate;

    //Se muestra sobre el elemento cuando isActivate es true
    private ParticleEffect activatedParticleEffect;

    //Se muestra permanentemente encima del elemento
    private ParticleEffect particleEffect;

    public Element(String texture, int x, int y) {
        super(texture, x, y);

        activatedParticleEffect = new ParticleEffect();
        activatedParticleEffect.load(Gdx.files.internal(AssetsManager.assetsReferences.get(texture + "_selected")), Gdx.files.internal(""));

        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal(AssetsManager.assetsReferences.get(texture + "_element")), Gdx.files.internal(""));
    }

    public void setX(float delta, float x) {
        super.setX(x);
        particleEffect.getEmitters().first().setPosition((getWidth()/2) + getX(), (getHeight()/2) + getY());
        this.update(delta);
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
        this.particleEffect.draw(batch);
        if (isActivate) {
            this.activatedParticleEffect.draw(batch);
        }
    }

    public void update(float delta) {
        particleEffect.update(delta);
        if (isActivate()) {
            activatedParticleEffect.update(delta);
        }
    }

    public boolean isActivate() {
        activatedParticleEffect.getEmitters().first().setPosition((getWidth()/2) + getX(), (getHeight()/2) + getY());
        return isActivate;
    }

    public void setIsActivate(boolean isActivate) {
        this.isActivate = isActivate;
    }

}
