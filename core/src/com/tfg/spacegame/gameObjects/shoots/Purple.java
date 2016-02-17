package com.tfg.spacegame.gameObjects.shoots;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.AssetsManager;

public class Purple extends Shoot{

    //Velocidad de movimiento
    public static final float SPEED = 700;

    //Efecto de particulas de este disparo
    private ParticleEffect shoot;

    //Indicará si el misil va a la derecha (1) o a la izquierda (-1)
    private int direction;

    private Vector2 vector;

    public Purple(GameObject shooter, int x, int y, float xTarget,float yTarget) {
        super("purple_shoot", x, y, shooter,
                AssetsManager.loadParticleEffect("purple_shoot_effect_shoot"),
                AssetsManager.loadParticleEffect("purple_effect_shock"));

        //Creamos el efecto de particulas
        shoot = AssetsManager.loadParticleEffect("purple_shoot_effect");

        this.updateParticleEffect();

        //Los iniciamos, pero aunque los iniciemos si no haya un update no avanzarán
        shoot.start();

        //Dependiendo de si es la nave o no el shooter, el disparo vendrá de la derecha o la izquierda
        if (shooter instanceof Ship) {
            direction = 1;
        } else {
            direction = -1;
        }

        vector = new Vector2((xTarget - (shooter.getX() + shooter.getWidth())),(yTarget - (shooter.getY() + shooter.getHeight()/2)));
    }

    public void updateParticleEffect() {
        super.updateParticleEffect();

        //Comprobamos si el disparo ha chocado
        if (!this.isShocked()) {
            //Se actuará de forma distinta si el shooter es enemigo o no
            if (this.getShooter() instanceof Ship) {
                shoot.getEmitters().first().setPosition(this.getX() - 10,this.getY() + 13);

                //shoot.getEmitters().first().getAngle().setHigh(vector.angle(),vector.angle());
            } else if (this.getShooter() instanceof Enemy){
                shoot.getEmitters().first().setPosition(this.getX(), this.getY());

                //Rotamos el efecto de particulas 180º
                shoot.getEmitters().first().getAngle().setHigh(180,180);
            }
        }
    }

    public void update(float delta) {
        if (!this.isShocked()) {
            //Actualizamos el movimiento del disparo
            this.setX(this.getX() + ((vector.x/100) * direction));
            //this.setY(getShooter().getY() + getShooter().getHeight() / 2 - this.getHeight() / 2);
            this.setY(this.getY() + ((vector.y/100) * direction));
            //Actualizamos la posición del efecto de particulas de acuerdo con la posición del shooter
            this.updateParticleEffect();

            //Actualizamos el efecto de particulas
            shoot.update(delta);
            super.update(delta);
        }
    }

    public void render(SpriteBatch batch){
        super.render(batch);
        if (!this.isShocked()) {
            shoot.draw(batch);
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
