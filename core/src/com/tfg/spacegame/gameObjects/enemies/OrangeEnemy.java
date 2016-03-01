package com.tfg.spacegame.gameObjects.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShootsManager;


public class OrangeEnemy extends Enemy {

    private static final float FREQUENCY_OF_SHOOTING = 4f;

    private float timeToShoot;

    private PartOfEnemy shield;
    private PartOfEnemy body;

    private PartOfEnemy cannonUpperLeft;
    private PartOfEnemy cannonUpperRight;

    private PartOfEnemy cannonLowerLeft;
    private PartOfEnemy cannonLowerRight;

    public OrangeEnemy(int x, int y) {
        super("orange_enemy", x, y, 9, AssetsManager.loadParticleEffect("basic_type5_destroyed"));

        body = new PartOfEnemy("orange_enemy_body",x,y,7,AssetsManager.loadParticleEffect("basic_destroyed"),this, false);
        body.setX(((x+getWidth()/2)-body.getWidth()/2)+getWidth());
        body.setY((y+getHeight()/2)-body.getHeight()/2);

        shield = new PartOfEnemy("orange_enemy_shield",x,y,7,AssetsManager.loadParticleEffect("basic_destroyed"),this, false);
        shield.setX(getX() - 63);
        shield.setY(body.getY()+body.getHeight()/2 - shield.getHeight()/2);

        cannonUpperLeft = new PartOfEnemy("orange_enemy_cannon",x,y,7,AssetsManager.loadParticleEffect("basic_destroyed"),this, false);
        cannonUpperLeft.setX(body.getX() + 15);
        cannonUpperLeft.setY(body.getY() + body.getHeight()/2 + 90);

        cannonUpperRight = new PartOfEnemy("orange_enemy_cannon",x,y,7,AssetsManager.loadParticleEffect("basic_destroyed"),this, false);
        cannonUpperRight.setX(cannonUpperLeft.getX() + body.getWidth()/2 + 35);
        cannonUpperRight.setY(cannonUpperLeft.getY());

        cannonLowerLeft = new PartOfEnemy("orange_enemy_cannon",x,y,7,AssetsManager.loadParticleEffect("basic_destroyed"),this, false);
        cannonLowerLeft.setX(cannonUpperLeft.getX());
        cannonLowerLeft.setY(body.getY());

        cannonLowerRight = new PartOfEnemy("orange_enemy_cannon",x,y,7,AssetsManager.loadParticleEffect("basic_destroyed"),this, false);
        cannonLowerRight.setX(cannonUpperRight.getX());
        cannonLowerRight.setY(body.getY());

        timeToShoot = 0;

        super.updateParticleEffect();
    }

    public PartOfEnemy getShield(){
        return shield;
    }

    public PartOfEnemy getBody(){
        return body;
    }

    public PartOfEnemy getCannonUpperLeft(){
        return cannonUpperLeft;
    }
    public PartOfEnemy getCannonUpperRight(){
        return cannonUpperRight;
    }
    public PartOfEnemy getCannonLowerLeft(){
        return cannonLowerLeft;
    }
    public PartOfEnemy getCannonLowerRight(){
        return cannonLowerRight;
    }

    public void collideWithShip() {}

    public void collideWithShoot(Shoot shoot) {

    }

    public Array<PartOfEnemy> getPartsOfEnemy() {
        Array<PartOfEnemy> result = new Array<PartOfEnemy>();
        result.add(getBody());
        result.add(getShield());
        result.add(getCannonUpperLeft());
        result.add(getCannonUpperRight());
        result.add(getCannonLowerLeft());
        result.add(getCannonLowerRight());

        return  result;
    }

    public void update(float delta){
        super.update(delta);
        super.updateParticleEffect();

        if(timeToShoot > FREQUENCY_OF_SHOOTING){
            //ShootsManager.shootOneOrangeWeapon(cannonUpperLeft,(int)(cannonUpperLeft.getX()),(int)(cannonUpperLeft.getY()+cannonUpperLeft.getHeight()),150,ShootsManager.ship);
            ShootsManager.shootOneOrangeWeapon(cannonLowerLeft,(int)(cannonLowerLeft.getX()),(int)(cannonLowerLeft.getY()),230,ShootsManager.ship);
            timeToShoot = 0;
        }else{
            timeToShoot+=delta;
        }
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
    }

}
