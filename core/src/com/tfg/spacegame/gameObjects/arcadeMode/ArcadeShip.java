package com.tfg.spacegame.gameObjects.arcadeMode;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.utils.AssetsManager;

public class ArcadeShip extends GameObject {

    //Efecto de particulas del fuego de propulsión
    protected ParticleEffect fireEffect;

    public ArcadeShip() {
        super("arcadeShip", 0, 25);

        fireEffect = AssetsManager.loadParticleEffect("propulsion_ship_effect");

        this.updateParticleEffect();
        fireEffect.start();
    }

    @Override
    public void render(SpriteBatch batch){
        super.render(batch);

        fireEffect.draw(SpaceGame.batch);
    }

    public void update(float delta) {
        //Actualizamos los efectos de partículas
        this.updateParticleEffect();

        fireEffect.update(delta);
    }

    public void updateParticleEffect() {
        fireEffect.getEmitters().first().setPosition(this.getX() + (this.getWidth()/2),this.getY());
        fireEffect.getEmitters().first().getAngle().setHigh(270);
    }

    @Override
    public void dispose() {
        super.dispose();
        fireEffect.dispose();
    }

}
