package com.tfg.spacegame.gameObjects.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.AssetsManager;


public class OrangeEnemy extends Enemy {

    private static final float FREQUENCY_OF_BURST = 4f;
    private static final float FREQUENCY_OF_SHOOTING = 0.5f;
    private static final float FREQUENCY_OF_OPEN_SHIELD = 10f;
    private static final int NUMBER_OF_SHOOTS = 3;

    private float timeToBurst;
    private int shootsFired;
    private float timeToShoot;

    private boolean openShield;
    private boolean shieldOpened;
    private boolean closeShield;

    private PartOfEnemy shield;
    private PartOfEnemy body;
    private PartOfEnemy body_aux_up;
    private PartOfEnemy body_aux_bottom;


    private Cannon cannonUpperLeft;
    private Cannon cannonUpperRight;

    private Cannon cannonLowerLeft;
    private Cannon cannonLowerRight;

    public OrangeEnemy(int x, int y) {
        super("orange_enemy", x, y, 9, AssetsManager.loadParticleEffect("basic_type5_destroyed"));

        body = new PartOfEnemy("orange_enemy_body",x,y,7,AssetsManager.loadParticleEffect("basic_destroyed"),this, false);
        body.setX(x + getWidth()/2);
        body.setY((y+getHeight()/2)-body.getHeight()/2);

        body_aux_up = new PartOfEnemy("orange_enemy_body_aux_up",x,y,7,AssetsManager.loadParticleEffect("basic_destroyed"),this, false);
        body_aux_up.setX(body.getX()-65);
        body_aux_up.setY(body.getY()+getHeight() + body_aux_up.getHeight() + 10);

        body_aux_bottom = new PartOfEnemy("orange_enemy_body_aux_bottom",x,y,7,AssetsManager.loadParticleEffect("basic_destroyed"),this, false);
        body_aux_bottom.setX(body_aux_up.getX());
        body_aux_bottom.setY(body.getY() - body_aux_up.getHeight()/2 + 40);

        shield = new PartOfEnemy("orange_enemy_shield",x,y,7,AssetsManager.loadParticleEffect("basic_destroyed"),this, false);
        shield.setX(body.getX() - 79);
        shield.setY(body.getY()+body.getHeight()/2 - shield.getHeight()/2 + 2);

        float xPosition = body.getX() - 30;
        float yPosition = body.getY() + body.getHeight()/2 + 90;

        cannonUpperLeft = new Cannon(xPosition, yPosition , this, xPosition-10, yPosition+30, 150);

        xPosition = cannonUpperLeft.getX() + body.getWidth()/2 + 40;

        cannonUpperRight = new Cannon(xPosition, yPosition, this, xPosition+10, yPosition+30, 90);

        xPosition = cannonUpperLeft.getX();
        yPosition = body.getY();

        cannonLowerLeft = new Cannon(xPosition, yPosition, this, xPosition-10, yPosition+10, 230);

        xPosition = cannonUpperRight.getX();

        cannonLowerRight = new Cannon(xPosition, yPosition, this, xPosition+10, yPosition+10, 270);

        timeToBurst = 0;
        timeToShoot = 0;
        shootsFired = 0;

        openShield      = false;
        closeShield     = false;
        shieldOpened    = false;

        super.updateParticleEffect();
    }

    public PartOfEnemy getShield(){
        return shield;
    }

    public PartOfEnemy getBody(){
        return body;
    }
    public PartOfEnemy getBodyAuxUp() {
        return body_aux_up;
    }
    public PartOfEnemy getBodyAuxBottom(){
        return body_aux_bottom;
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
        result.add(getBodyAuxUp());
        result.add(getBodyAuxBottom());
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
        if(timeToBurst > FREQUENCY_OF_BURST){
            if(timeToShoot > FREQUENCY_OF_SHOOTING){
                switch (MathUtils.random(3)){
                    case 0:
                        cannonUpperLeft.shoot();
                        break;
                    case 1:
                        cannonUpperRight.shoot();
                        break;
                    case 2:
                        cannonLowerLeft.shoot();
                        break;
                    case 3:
                        cannonLowerRight.shoot();
                        break;
                }
                shootsFired++;
                timeToShoot = 0;
                if(shootsFired >= NUMBER_OF_SHOOTS){
                    shootsFired = 0;
                    timeToBurst = 0;
                }
            }else{
                timeToShoot+=delta;
            }
        }else{
           timeToBurst+=delta;
        }

      
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
    }

}
