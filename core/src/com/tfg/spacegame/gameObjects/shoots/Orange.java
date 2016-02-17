package com.tfg.spacegame.gameObjects.shoots;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.AssetsManager;


public class Orange extends Shoot{

    private static final float SPEED = 150;
    private static final float HIGHSPEED = 250;
    private static final float SPEEDANGLE = 0.7f;

    private static final float LIMIT_TO_CHANGE_ANGLE = 50;

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

        actualAngle = angle;

        distanceFromOrigin = 0;
    }
    public void update(float delta){
        super.update(delta);
        super.updateParticleEffect();

        shoot.update(delta);

        shoot.getEmitters().first().setPosition(this.getX()+this.getWidth()/2,this.getY()+this.getHeight()/2);

        if(distanceFromOrigin > LIMIT_TO_CHANGE_ANGLE){

            float x1 = this.getX() + this.getWidth()/2;
            float y1 = this.getY() + this.getHeight()/2;

            float x2 = target.getX() + target.getWidth()/2;
            float y2 = target.getY() + target.getHeight()/2;

            movement.set( x2-x1 , y2-y1 );

            targetAngle = movement.angle();

            if(targetAngle >=180)
                targetAngle-=360;

            float diffAngle = targetAngle - actualAngle;

            if(diffAngle < 0 )
                actualAngle-=SPEEDANGLE;
            else
                actualAngle+=SPEEDANGLE;

            this.setX(this.getX()+ SPEED * delta * MathUtils.cosDeg(actualAngle));
            this.setY(this.getY()+ SPEED * delta * MathUtils.sinDeg(actualAngle));

        }else{
            distanceFromOrigin+= HIGHSPEED * delta * MathUtils.cosDeg(actualAngle);

            this.setX(this.getX()+ HIGHSPEED * delta * MathUtils.cosDeg(actualAngle));
            this.setY(this.getY()+ HIGHSPEED * delta * MathUtils.sinDeg(actualAngle));
        }

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
