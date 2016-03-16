package com.tfg.spacegame.gameObjects.arcadeMode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.FontManager;
import com.tfg.spacegame.utils.ShapeRendererManager;

public class ArcadeShip extends GameObject {

    private final static float SPEED = 6;

    //Efecto de particulas del fuego de propulsión
    private ParticleEffect fireEffect;
    private float fireEffectScale;
    private float fireEffectLife;

    //Efecto de destrucción de la nave
    private ParticleEffect destroyEffect;
    private float destroyEffectScale;
    private float destroyEffectLife;

    //Rango de sensibilidad del movimiento horizontal
    private float sensitiveRange;

    //Indica si la nave está derrotada
    private boolean defeated;

    public ArcadeShip() {
        super("arcadeShip", 0, 25);

        //Colocamos la nave en la mitad de la pantalla
        this.setX((SpaceGame.width / 2) - this.getWidth());

        //Preparamos los efectos de partículas con sus respectivos reescalados
        fireEffect = AssetsManager.loadParticleEffect("propulsion_ship_effect");
        fireEffectScale = fireEffect.getEmitters().first().getScale().getHighMax();
        fireEffectLife = fireEffect.getEmitters().first().getLife().getHighMax();
        destroyEffect = AssetsManager.loadParticleEffect("ship_shock_effect");
        destroyEffectScale = destroyEffect.getEmitters().first().getScale().getHighMax();
        destroyEffectLife = destroyEffect.getEmitters().first().getLife().getHighMax();

        sensitiveRange = 0.5f;
        defeated = false;

        this.updateParticleEffect();
        fireEffect.start();
        destroyEffect.start();
    }

    @Override
    public void render(){
        if (!defeated) {
            super.render();
            fireEffect.draw(SpaceGame.batch);
        } else {
            destroyEffect.draw(SpaceGame.batch);
        }
    }

    public void update(float delta) {
        if (!defeated) {
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
        } else {
            this.updateParticleEffect();
            destroyEffect.update(delta);
        }
    }

    public void updateParticleEffect() {
        if (!defeated) {
            //Colocamos el efecto según la posición de la nave, y ajustamos el ángulo, ya que originalmente es horizontal
            fireEffect.getEmitters().first().setPosition(this.getX() + (this.getWidth() / 2),
                    this.getY() + (this.getHeight() * ((1 - this.getLogicShape().getScaleY()) / 2)));
            fireEffect.getEmitters().first().getAngle().setHigh(270);
        } else {
            destroyEffect.getEmitters().first().setPosition(this.getX(), this.getY() + (this.getHeight() / 4));
        }
    }

    //Indica que la nave está derrotada
    public void defeat() {
        defeated = true;
    }

    //Además de escalar el propio objeto, se encarga de escalar el efecto de partículas
    public void setScale(float x, float y) {
        super.setScale(x, y);

        fireEffect.getEmitters().first().getScale().setHigh(fireEffectScale * y);
        fireEffect.getEmitters().first().getLife().setHigh(fireEffectLife * y);

        destroyEffect.getEmitters().first().getScale().setHigh(destroyEffectScale * y);
        destroyEffect.getEmitters().first().getLife().setHigh(destroyEffectLife * y);
    }

    @Override
    public void dispose() {
        super.dispose();
        fireEffect.dispose();
        destroyEffect.dispose();
    }

}
