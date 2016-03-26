package com.tfg.spacegame.gameObjects.campaignMode.shoots;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.campaignMode.Enemy;
import com.tfg.spacegame.gameObjects.campaignMode.Shoot;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShapeRendererManager;
import com.tfg.spacegame.utils.enums.TypeShoot;

public class Purple extends Shoot{

    //Velocidad de movimiento
    public static final float SPEED = 10;

    //Efecto de particulas de este disparo
    private ParticleEffect shoot;

    private float angle;

    public Purple(GameObject shooter, int x, int y, float xTarget, float yTarget) {
        super("purple_shoot", x, y, shooter,
                AssetsManager.loadParticleEffect("purple_shoot_effect_shoot"),
                AssetsManager.loadParticleEffect("purple_effect_shock"));

        this.getLogicShape().setOrigin(0,this.getHeight()/2);

        //Creamos el efecto de particulas
        shoot = AssetsManager.loadParticleEffect("purple_shoot_effect");

        Vector2 aux = new Vector2((xTarget - (shooter.getX() + shooter.getWidth())),(yTarget - (shooter.getY() +
                shooter.getHeight()/2)));
        angle = aux.angle();
        // Establememos el tipo del arma
        type = TypeShoot.PURPLE;

        //Cambiamos el ángulo
        this.getLogicShape().setRotation(angle);

        //Actualizamos el efecot de partículas
        this.updateParticleEffect();

        //Los iniciamos, pero aunque los iniciemos si no haya un update no avanzarán
        shoot.start();
    }

    public void updateParticleEffect() {
        super.updateParticleEffect();
        //Comprobamos si el disparo ha chocado
        if (!this.isShocked()) {
            shoot.getEmitters().first().setPosition(this.getX() + SPEED*MathUtils.cosDeg(angle) , this.getY()+ this.getHeight()/2 + SPEED*MathUtils.sinDeg(angle));
            //Rotamos el efecto de partículas para hacerlo coincidir con el ángulo del disparo
            shoot.getEmitters().first().getAngle().setHigh(angle);
        }
    }

    public void update(float delta){
        if (!this.isShocked()) {

            //Actualizamos el movimiento del disparo
            this.setX(this.getX() + SPEED*MathUtils.cosDeg(angle));
            this.setY(this.getY() + SPEED*MathUtils.sinDeg(angle));

            //Actualizamos el ángulo del disparo por si acaso tendría que modificarse
            this.getLogicShape().setRotation(angle);

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


    public void render(){
        if (!this.isShocked()) {
            ShapeRendererManager.renderPolygon(this.getLogicShape().getTransformedVertices(), Color.WHITE);
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
