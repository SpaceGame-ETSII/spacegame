package com.tfg.spacegame.gameObjects.shoots;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;

public class Basic extends Shoot {

    // Velocidad de movimiento
    public static final float SPEED = 600;

    // Efecto de partículas que forma el disparo en sí
    private ParticleEffect shoot;

    // Efecto de particulas de este disparo
    private ParticleEffect shootEffect;

    // Efecto de partículas cuando el disparo choca
    private ParticleEffect shockEffect;

    public Basic(GameObject shooter, int x, int y) {
        // Situamos el disparo en el sitio correcto
        // X - Extremo derecha del shooter
        // Y - La mitad del alto del shooter - la mitad del alto del disparo
        super("basic_shoot",x,y,shooter);

        // Creamos los efectos de particulas
        shoot = AssetsManager.loadParticleEffect("basic_shoot_effect");
        shootEffect = AssetsManager.loadParticleEffect("basic_effect_shoot");
        shockEffect = AssetsManager.loadParticleEffect("basic_effect_shoot");

        this.updateParticleEffect();

        shoot.start();
        shootEffect.start();
        shockEffect.start();
    }

    public void updateParticleEffect() {
        //Comprobamos si el disparo ha chocado
        if (!this.isShocked()) {
            //Se actuará de forma distinta si el shooter es enemigo o no
            if (this.getShooter() instanceof Enemy) {
                shoot.getEmitters().first().setPosition(this.getX() + this.getShooter().getWidth()/2, this.getY());
                shootEffect.getEmitters().first().setPosition(this.getShooter().getX(), this.getShooter().getY() + this.getShooter().getHeight() / 2);
                // Rotamos el efecto de particulas 180º
                shoot.getEmitters().first().getAngle().setHigh(180,180);
                shootEffect.getEmitters().first().getAngle().setHigh(135, 225);
                shootEffect.getEmitters().first().getAngle().setLow(160, 200);
            } else {
                // Lo centramos con la nave para que salga en la posición de su cañón
                shoot.getEmitters().first().setPosition(this.getX() + 3, this.getY() + 7);
                // Lo ubicamos en el extremo derecha y mitad de altura del shooter
                shootEffect.getEmitters().first().setPosition(this.getShooter().getX() + this.getShooter().getWidth(), this.getShooter().getY() + this.getShooter().getHeight() / 2);
            }
        } else {
            //Si el disparo ha chocado, el efecto a mostrar es el del shockEffect
            if (this.getShooter() instanceof Enemy) {
                shockEffect.getEmitters().first().setPosition(this.getX(), this.getY());
                // Rotamos el efecto de particulas 180º
                shockEffect.getEmitters().first().getAngle().setHigh(135, 225);
                shockEffect.getEmitters().first().getAngle().setLow(160, 200);
            } else {
                shockEffect.getEmitters().first().setPosition(this.getX() + this.getWidth(), this.getY());
            }
        }
    }

    public void update(float delta) {
        if (!this.isShocked()) {
            // Actualizamos el movimiento del disparo
            if (getShooter() instanceof Enemy) {
                this.setX(this.getX() - (SPEED * delta));
            }else {
                this.setX(this.getX() + (SPEED * delta));
            }

            // Actualizamos la posición del efecto de particulas de acuerdo con la posición del shooter
            this.updateParticleEffect();

            // Actualizamos el efecto de particulas
            shoot.update(delta);
            shootEffect.update(delta);
        } else {
            // Actualizamos el efecto de particulas
            this.updateParticleEffect();
            shockEffect.update(delta);

            // Restamos el tiempo que estará el efecto en pantalla, y si pasa el tiempo, marcamos el shoot como deletable
            if (shockEffect.isComplete()) {
                this.changeToDeletable();
            }
        }
    }

    @Override
    public void render(SpriteBatch batch){
        if (!this.isShocked()) {
            super.render(batch);
            shoot.draw(batch);
            shootEffect.draw(batch);
        } else {
            shockEffect.draw(batch);
        }
    }

    public void collideWithShip() {
        super.collideWithShip();
        this.shock();
    }

    public void collideWithEnemy(Enemy enemy) {
        super.collideWithEnemy(enemy);
        this.shock();
    }

    public void collideWithShoot(Shoot shoot) {
        super.collideWithShoot(shoot);
        this.shock();
    }
    
    public void dispose(){
        super.dispose();
        shootEffect.dispose();
        shoot.dispose();
    }
}
