package com.tfg.spacegame.gameObjects;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.utils.AssetsManager;

public class Shoot extends GameObject {

    //Objeto de juego que ha provocado el disparo
    private GameObject shooter;

    // Indica si el disparo ha chocado con algo
    private boolean shocked;

    // Indica si el disparo se puede borrar
    private boolean deletable;

    // Efecto de particulas de este disparo
    private ParticleEffect shootEffect;

    // Efecto de partículas cuando el disparo choca
    private ParticleEffect destroyEffect;

    public Shoot(String nameTexture, int x, int y, GameObject shooter, ParticleEffect shootEffect, ParticleEffect destroyEffect) {
        super(nameTexture,x,y);
        this.shooter = shooter;
        shocked = false;

        //Creamos los efectos de partículas si no vienen nulos
        if (shootEffect != null) {
            this.shootEffect = shootEffect;
            shootEffect.start();
        }
        if (destroyEffect != null) {
            this.destroyEffect = destroyEffect;
            destroyEffect.start();
        }
    }

    public GameObject getShooter(){
        return shooter;
    }

    public boolean isShocked() { return shocked;}

    public void shock() { shocked = true; }

    public boolean isDeletable() { return deletable; }

    public void changeToDeletable() { deletable = true;}

    public void update(float delta) {
        //Si el disparo no ha chocado mostramos el efecto de salida, y si ha chocado el efecto de destrucción
        if (!this.isShocked()) {
            shootEffect.update(delta);
        } else {
            destroyEffect.update(delta);

            // Restamos el tiempo que estará el efecto en pantalla, y si pasa el tiempo, marcamos el shoot como deletable
            if (destroyEffect.isComplete()) {
                this.changeToDeletable();
            }
        }
    }

    public void updateParticleEffect() {
        //Comprobamos si el disparo ha chocado
        if (!this.isShocked()) {
            //Se actuará de forma distinta si el shooter es enemigo o no
            if (this.getShooter() instanceof Enemy) {
                shootEffect.getEmitters().first().setPosition(this.getShooter().getX(), this.getShooter().getY() + this.getShooter().getHeight() / 2);

                // Rotamos el efecto de particulas
                shootEffect.getEmitters().first().getAngle().setHigh(135, 225);
                shootEffect.getEmitters().first().getAngle().setLow(160, 200);
            } else {
                // Lo ubicamos en el extremo derecha y mitad de altura del shooter
                shootEffect.getEmitters().first().setPosition(this.getShooter().getX() + this.getShooter().getWidth(), this.getShooter().getY() + this.getShooter().getHeight() / 2);
            }
        } else {
            //Si el disparo ha chocado, el efecto a mostrar es el del shockEffect
            if (this.getShooter() instanceof Enemy) {
                destroyEffect.getEmitters().first().setPosition(this.getX(), this.getY());
                // Rotamos el efecto de particulas 180º
                destroyEffect.getEmitters().first().getAngle().setHigh(135, 225);
                destroyEffect.getEmitters().first().getAngle().setLow(160, 200);
            } else {
                destroyEffect.getEmitters().first().setPosition(this.getX() + this.getWidth(), this.getY());
            }
        }
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
        if (!this.isShocked()) {
            if (shootEffect != null)
                shootEffect.draw(batch);
        } else {
            if (destroyEffect != null)
                destroyEffect.draw(batch);
        }
    }

    public void collideWithShip() {
    }

    public void collideWithEnemy(Enemy enemy) {}

    public void collideWithShoot(Shoot shoot) {}

    public void dispose() {
        super.dispose();
        shootEffect.dispose();
        destroyEffect.dispose();
    }

}