package com.tfg.spacegame.gameObjects.campaignMode.shoots;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.campaignMode.Enemy;
import com.tfg.spacegame.gameObjects.campaignMode.Shoot;

public class Basic extends Shoot {

    // Velocidad de movimiento
    public static final float SPEED = 600;

    // Efecto de partículas que forma el disparo en sí
    private ParticleEffect shoot;

    public Basic(GameObject shooter, int x, int y) {
        // Situamos el disparo en el sitio correcto
        // X - Extremo derecha del shooter
        // Y - La mitad del alto del shooter - la mitad del alto del disparo
        super("basic_shoot",x,y,shooter,
                AssetsManager.loadParticleEffect("basic_effect_shoot"),
                AssetsManager.loadParticleEffect("basic_effect_shoot"));

        //Creamos el efecto que hará las veces de textura
        shoot = AssetsManager.loadParticleEffect("basic_shoot_effect");
        this.updateParticleEffect();
        shoot.start();
    }

    public void updateParticleEffect() {
        super.updateParticleEffect();

        //Comprobamos si el disparo ha chocado
        if (!this.isShocked()) {
            //Se actuará de forma distinta si el shooter es enemigo o no
            if (this.getShooter() instanceof Enemy) {
                shoot.getEmitters().first().setPosition(this.getX() + this.getShooter().getWidth()/2, this.getY());

                // Rotamos el efecto de particulas 180º
                shoot.getEmitters().first().getAngle().setHigh(180,180);
            } else {
                // Lo centramos con la nave para que salga en la posición de su cañón
                shoot.getEmitters().first().setPosition(this.getX() + 3, this.getY() + 7);
            }
        }
    }

    public void update(float delta) {
        if (!this.isShocked()) {
            // Actualizamos el movimiento del disparo
            if (getShooter() instanceof Enemy) {
                this.setX(this.getX() - (SPEED * delta));
            } else {
                this.setX(this.getX() + (SPEED * delta));
            }
        }

        this.updateParticleEffect();

        //Actualizamos los efectos de partículas
        super.update(delta);

        if (!this.isShocked()) {
            shoot.update(delta);
        }
    }

    @Override
    public void render(){
        super.render();
        if (!this.isShocked()) {
            shoot.draw(SpaceGame.batch);
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
        shoot.dispose();
    }
}
