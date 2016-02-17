package com.tfg.spacegame.gameObjects.shoots;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.AssetsManager;


public class Orange extends Shoot{

    private static final float SPEEDX = 100;
    private static final float SPEEDY = 100;
    private static final float SPEEDANGLE = 10;

    //Efecto de particulas de este disparo
    private ParticleEffect shoot;

    private float targetAngle;

    private Vector2 movement;

    private GameObject target;

    public Orange(GameObject shooter, int x, int y, float angle, GameObject target) {
        super("orange_shoot", x, y, shooter, null, null);

        this.setY(this.getY() - this.getHeight()/2);

        shoot = AssetsManager.loadParticleEffect("orange_shoot_effect");
        shoot.getEmitters().first().setPosition(this.getX()+this.getWidth()/2,this.getY()+this.getHeight()/2);

        this.target = target;

        movement = new Vector2();
    }
    public void update(float delta){
        super.update(delta);
        super.updateParticleEffect();

        shoot.update(delta);

        shoot.getEmitters().first().setPosition(this.getX()+this.getWidth()/2,this.getY()+this.getHeight()/2);

        float x1 = this.getX() + this.getWidth()/2;
        float y1 = this.getY() + this.getHeight()/2;

        float x2 = target.getX() + target.getWidth()/2;
        float y2 = target.getY() + target.getHeight()/2;

        movement.set( x2-x1 , y2-y1 );

        targetAngle = movement.angle();

        this.setX(this.getX()+SPEEDX * delta * MathUtils.cosDeg(targetAngle));
        this.setY(this.getY()+SPEEDY * delta * MathUtils.sinDeg(targetAngle));
    }

    public void render(SpriteBatch batch) {
        super.render(batch);

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
