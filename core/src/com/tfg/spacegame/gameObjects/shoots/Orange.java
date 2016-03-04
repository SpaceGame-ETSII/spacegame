package com.tfg.spacegame.gameObjects.shoots;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.AssetsManager;


public class Orange extends Shoot{

    private final float SPEEDX;
    private final float SPEEDY;
    private final float HIGHSPEEDX;
    private final float HIGHSPEEDY;

    private final float SPEEDANGLE;

    private final float HIGHSPEEDANGLE;

    private final float LIMIT_TO_CHANGE_ANGLE;

    private float distanceFromOrigin;

    //Efecto de particulas de este disparo
    private ParticleEffect shoot;

    private float targetAngle;
    private float actualAngle;

    private Vector2 movement;

    private GameObject target;

    public Orange(GameObject shooter, int x, int y, float angle, GameObject target) {
        super("orange_shoot", x, y, shooter, AssetsManager.loadParticleEffect("orange_shoot_effect_shoot"), AssetsManager.loadParticleEffect("orange_shoot_effect_shock"));

        this.setY(this.getY() - this.getHeight()/2);

        shoot = AssetsManager.loadParticleEffect("orange_shoot_effect");
        shoot.getEmitters().first().setPosition(this.getX()+this.getWidth()/2,this.getY()+this.getHeight()/2);

        this.target = target;

        movement = new Vector2();

        super.updateParticleEffect();

        actualAngle = angle;

        distanceFromOrigin = 0;

        if(getShooter() instanceof Ship){
            LIMIT_TO_CHANGE_ANGLE = 50;
            SPEEDX=150;
            SPEEDY=150;
            HIGHSPEEDY = HIGHSPEEDX = 250;
            SPEEDANGLE = 0.7f;
            HIGHSPEEDANGLE = 0.7f;
        }else{
            LIMIT_TO_CHANGE_ANGLE = 100;
            SPEEDANGLE = 0.8f;
            HIGHSPEEDANGLE = 1.7f;
            SPEEDX=250;
            SPEEDY=150;
            HIGHSPEEDX = 400;
            HIGHSPEEDY = 100;
        }
    }
    public void update(float delta){
        super.update(delta);
        super.updateParticleEffect();

        shoot.update(delta);

        shoot.getEmitters().first().setPosition(this.getX()+this.getWidth()/2,this.getY()+this.getHeight()/2);

        if(!isShocked()){
            if(distanceFromOrigin >= LIMIT_TO_CHANGE_ANGLE){

                if(calculateDiffAngle() < 0 )
                    actualAngle-=SPEEDANGLE;
                else
                    actualAngle+=SPEEDANGLE;

                this.setX(this.getX()+ SPEEDX * delta * MathUtils.cosDeg(actualAngle) );
                this.setY(this.getY()+ SPEEDY * delta * MathUtils.sinDeg(actualAngle) );
            }else{
                distanceFromOrigin+= Math.abs(HIGHSPEEDX * delta * MathUtils.cosDeg(actualAngle));

                if(getShooter() instanceof Enemy){
                    if(calculateDiffAngle() < 0 )
                        actualAngle-=HIGHSPEEDANGLE;
                    else
                        actualAngle+=HIGHSPEEDANGLE;
                }

                this.setX(this.getX()+ HIGHSPEEDX * delta * MathUtils.cosDeg(actualAngle));
                this.setY(this.getY() + HIGHSPEEDY * delta * MathUtils.sinDeg(actualAngle));
            }
        }


    }

    private float calculateDiffAngle(){
        float x1 = this.getX() + this.getWidth()/2;
        float y1 = this.getY() + this.getHeight()/2;

        float x2 = target.getX() + target.getWidth()/2;
        float y2 = target.getY() + target.getHeight()/2;

        movement.set( x2-x1 , y2-y1 );

        targetAngle = movement.angle();

        if(getShooter() instanceof Ship && targetAngle >=180)
            targetAngle-=360;

        return targetAngle - actualAngle;
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
        if(!isShocked())
            shoot.draw(batch);
    }

    public void collideWithEnemy(Enemy enemy) {
        super.collideWithEnemy(enemy);
        this.shock();
    }

    public void collideWithShoot(Shoot shoot) {
        super.collideWithShoot(shoot);
        this.shock();
    }

    public void collideWithShip() {
        super.collideWithShip();
        this.shock();
    }
}
