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
    private float fireEffectScale;
    private float fireEffectLife;

    //Rango de sensibilidad del movimiento horizontal
    private float sensitiveRange;

    public ArcadeShip() {
        super("arcadeShip", 0, 25);

        this.setX((SpaceGame.width / 2) - this.getWidth());

        fireEffect = AssetsManager.loadParticleEffect("propulsion_ship_effect");
        fireEffectScale = fireEffect.getEmitters().first().getScale().getHighMax();
        fireEffectLife = fireEffect.getEmitters().first().getLife().getHighMax();

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
        //Colocamos el efecto según la posición de la nave, y ajustamos el ángulo, ya que originalmente es horizontal
        fireEffect.getEmitters().first().setPosition(this.getX() + (this.getWidth()/2),
                                                     this.getY() + (this.getHeight() * ((1 - this.getLogicShape().getScaleY()) / 2)));
        fireEffect.getEmitters().first().getAngle().setHigh(270);
    }

    //Además de escalar el propio objeto, se encarga de escalar el efecto de partículas
    public void setScale(float x, float y) {
        super.setScale(x, y);
        fireEffect.getEmitters().first().getScale().setHigh(fireEffectScale * y);
        fireEffect.getEmitters().first().getLife().setHigh(fireEffectLife * y);
    }

    @Override
    public void dispose() {
        super.dispose();
        fireEffect.dispose();
    }

}
