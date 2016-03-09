package com.tfg.spacegame.gameObjects.shoots;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShapeRendererManager;

import java.util.Arrays;

public class Purple extends Shoot{

    //Velocidad de movimiento
    public static final float SPEED = 40;

    //Efecto de particulas de este disparo
    private ParticleEffect shoot;

    //Indicará si el misil va a la derecha (1) o a la izquierda (-1)
    private int direction;

    //Variable para almacenar el vector de dirección del disparo
    private Vector2 vector;

    //Variable para almacenar la región de la textura del disparo para poder rotarlo
    private TextureRegion textureRegion;

    public Purple(GameObject shooter, int x, int y, float xTarget,float yTarget) {
        super("purple_shoot", x, y, shooter,
                AssetsManager.loadParticleEffect("purple_shoot_effect_shoot"),
                AssetsManager.loadParticleEffect("purple_effect_shock"));

        //Creamos el efecto de particulas
        shoot = AssetsManager.loadParticleEffect("purple_shoot_effect");

        //Cargamos la textura del disparo
        textureRegion = new TextureRegion(this.getTexture());

        //Creamos el vector para almacenar hacia donde deberá ir el disparo
        vector = new Vector2((xTarget - (shooter.getX() + shooter.getWidth())),(yTarget - (shooter.getY() +
                shooter.getHeight()/2)));

        //Cambiamos el ángulo
        this.getLogicShape().setRotation(vector.angle());

        //Actualizamos el efecot de partículas
        this.updateParticleEffect();

        //Los iniciamos, pero aunque los iniciemos si no haya un update no avanzarán
        shoot.start();

        //Dependiendo de si es la nave o no el shooter, el disparo ira a la derecha o a la izquierda
        if (shooter instanceof Ship) {
            direction = 1;
        } else if (shooter instanceof Enemy){
            direction = -1;
        }
    }

    public void updateParticleEffect() {
        super.updateParticleEffect();

        //Comprobamos si el disparo ha chocado
        if (!this.isShocked()) {
            //Se actuará de forma distinta si el shooter es enemigo o no
            if (this.getShooter() instanceof Ship) {
                shoot.getEmitters().first().setPosition(this.getX() + (vector.x/SPEED), this.getY() + (vector.y/SPEED));

                //Rotamos el efecto de partículas para hacerlo coincidir con el ángulo del disparo
                shoot.getEmitters().first().getAngle().setHigh(vector.angle());
            } else if (this.getShooter() instanceof Enemy){
                shoot.getEmitters().first().setPosition(this.getX() + (vector.x/SPEED), this.getY() + (vector.y/SPEED));

                //Rotamos el efecto de partículas para hacerlo coincidir con el ángulo del disparo
                shoot.getEmitters().first().getAngle().setHigh(vector.angle());
            }
        }
    }

    public void update(float delta) {
        if (!this.isShocked()) {

            if (getShooter() instanceof Ship){
                //Actualizamos el movimiento del disparo
                this.setX(this.getX() + ((vector.x/SPEED) * direction));
                this.setY(this.getY() + ((vector.y/SPEED) * direction));
            }else {
                //Actualizamos el movimiento del disparo
                this.setX(this.getX() - ((vector.x/SPEED) * direction));
                this.setY(this.getY() - ((vector.y/SPEED) * direction));
            }


            //Actualizamos el ángulo del disparo por si acaso tendría que modificarse
            this.getLogicShape().setRotation(vector.angle());

            //Actualizamos la posición del efecto de particulas de acuerdo con la posición del shooter
            this.updateParticleEffect();

            //Actualizamos el efecto de particulas
            super.update(delta);
            shoot.update(delta);
        }else{
            //Actualizamos los efectos de partículas
            this.updateParticleEffect();
            super.update(delta);
        }
    }

    public void render(SpriteBatch batch){
        super.render(batch);
        if (!this.isShocked()) {
            batch.draw(textureRegion, this.getX(), this.getY(), -textureRegion.getRegionX(), textureRegion.getRegionY(),
                    textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), 1f, 1f, vector.angle());
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
