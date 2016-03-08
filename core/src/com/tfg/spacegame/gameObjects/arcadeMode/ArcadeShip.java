package com.tfg.spacegame.gameObjects.arcadeMode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.FontManager;

public class ArcadeShip extends GameObject {

    private final static float SPEED = 6;

    //Efecto de particulas del fuego de propulsión
    protected ParticleEffect fireEffect;

    //Rango de sensibilidad del movimiento
    private float sensitiveRange;

    public ArcadeShip() {
        super("arcadeShip", 0, 25);

        this.setX((SpaceGame.width / 2) - this.getWidth());

        fireEffect = AssetsManager.loadParticleEffect("propulsion_ship_effect");
        sensitiveRange = 0.5f;

        this.updateParticleEffect();
        fireEffect.start();
    }

    @Override
    public void render(SpriteBatch batch){
        super.render(batch);

        fireEffect.draw(SpaceGame.batch);
    }

    public void update(float delta) {
        //Nos movemos si hemos salido del rango de sensibilidad
        if (Gdx.input.getAccelerometerX() > sensitiveRange)
            this.setX(this.getX() - ((Gdx.input.getAccelerometerX() - sensitiveRange) * SPEED));
        else if (Gdx.input.getAccelerometerX() < -sensitiveRange)
            this.setX(this.getX() - ((Gdx.input.getAccelerometerX() + sensitiveRange) * SPEED));

        //Controlamos que no nos salgamos de la pantalla
        if (this.getX() < 0) {
            this.setX(0);
        } else if (this.getX() > SpaceGame.width - this.getWidth()) {
            this.setX(SpaceGame.width - this.getWidth());
        }

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
