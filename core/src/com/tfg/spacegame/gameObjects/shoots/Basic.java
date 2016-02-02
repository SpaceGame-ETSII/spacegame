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

    // Tiempo necesario a esperar para que empiece a moverse el disparo
    // Se trata de un delay de aparición
    private float timeToMove;

    // Efecto de particulas de este disparo
    private ParticleEffect shootEffect;

    // Efecto de partículas cuando el disparo choca
    private ParticleEffect shockEffect;

    // Indica el tiempo que estará en pantalla el efecto al chocar
    private float timeToShockEffect;

    public Basic(GameObject shooter, int x, int y, float delay) {
        // Situamos el disparo en el sitio correcto
        // X - Extremo derecha del shooter
        // Y - La mitad del alto del shooter - la mitad del alto del disparo
        super("shoot",x,y,shooter);

        // Creamos los efectos de particulas
        shootEffect = AssetsManager.loadParticleEffect("basic_effect_shoot");
        shockEffect = AssetsManager.loadParticleEffect("basic_effect_shoot");

        this.updateParticleEffect();

        // Lo iniciamos, pero aunque lo iniciemos si no hay un update no avanzará
        shootEffect.start();
        shockEffect.start();
        timeToMove = delay;
        timeToShockEffect = 1.0f;
    }

    public void updateParticleEffect() {
        if (!this.isShocked()) {
            if (this.getShooter() instanceof Enemy) {
                shootEffect.getEmitters().first().setPosition(this.getShooter().getX(), this.getShooter().getY() + this.getShooter().getHeight() / 2);
                // Rotamos el efecto de particulas 180º
                shootEffect.getEmitters().first().getAngle().setHigh(135, 225);
                shootEffect.getEmitters().first().getAngle().setLow(160, 200);
            } else {
                // Lo ubicamos en el extremo derecha y mitad de altura del shooter
                shootEffect.getEmitters().first().setPosition(this.getShooter().getX() + this.getShooter().getWidth(), this.getShooter().getY() + this.getShooter().getHeight() / 2);
            }
        } else {
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
            // Esperaremos a que sea el momento correcto para moverse
            if (timeToMove < 0) {
                // Actualizamos el movimiento del disparo
                if (getShooter() instanceof Enemy)
                    this.setX(this.getX() - (SPEED * delta));
                else
                    this.setX(this.getX() + (SPEED * delta));

                // Actualizamos la posición del efecto de particulas de acuerdo con la posición del shooter
                this.updateParticleEffect();
                // Actualizamos el efecto de particulas
                shootEffect.update(delta);

            }
            // Mientras no sea el momento para moverse
            else {
                // Vamos a ir restando el tiempo de delay con el delta hasta que sea menor que 0
                timeToMove -= delta;

                // Podemos además ir actualizando la posición Y por si el shooter se está moviendo
                this.setY(getShooter().getY() + getShooter().getHeight() / 2 - this.getHeight() / 2);
            }
        } else {
            this.updateParticleEffect();
            shockEffect.update(delta);

            // Restamos el tiempo que estará el efecto en pantalla, y si pasa el tiempo, marcamos el shoot como deletable
            if (timeToShockEffect > 0) {
                timeToShockEffect -= delta;
            } else {
                this.changeToDeletable();
            }

        }

    }

    public void render(SpriteBatch batch){
        if (!this.isShocked()) {
            super.render(batch);
            // Mientras no sea el momento para disparar, no renderizamos el efecto de particulas
            if (timeToMove < 0)
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
    }
}
