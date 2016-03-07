package com.tfg.spacegame.gameObjects.enemies;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShootsManager;


public class OrangeEnemy extends Enemy {

    private enum OrangeEnemyState{
        OPENING_SHIELD, SHIELD_OPENED, CLOSING_SHIELD, NONE
    }

    private static final float  FREQUENCY_OF_BURST = 4f;
    private static final float  FREQUENCY_OF_SHOOTING = 0.9f;
    private static final int    NUMBER_OF_SHOOTS = 3;
    private static final float  FREQUENCY_OF_SHOOT_MAIN_CANNON = 6f;
    private static final int    MAXIMUM_VELOCITY_CHARGING_EFFECT = 50;
    private final float         INITIAL_X_SHIELD;
    private static final int    MAXIMUM_NUMBER_SHOOTS = 25;

    private float   timeChargingMainCannon;
    private float   timeToBurst;
    private float   timeToShoot;

    private int     shootsFired;
    private int     burstsFired;
    private int     selectedCannonToFire;

    private boolean shootMainCannon;
    private OrangeEnemyState orangeEnemyState;

    private PartOfEnemy shield;
    private PartOfEnemy body;
    private PartOfEnemy body_aux_up;
    private PartOfEnemy body_aux_bottom;

    private Cannon cannonUpperLeft;
    private Cannon cannonUpperRight;

    private Cannon cannonLowerLeft;
    private Cannon cannonLowerRight;

    private ParticleEffect chargeMainCannonEffect;

    public OrangeEnemy(int x, int y) {
        super("orange_enemy", x, y, 60, AssetsManager.loadParticleEffect("basic_type5_destroyed"));

        chargeMainCannonEffect = AssetsManager.loadParticleEffect("orange_main_cannon_charging");

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
        INITIAL_X_SHIELD = shield.getX();
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

        resetStates();

        super.updateParticleEffect();
        chargeMainCannonEffect.getEmitters().first().setPosition(this.getX()-10,this.getY()+this.getHeight()/2);
        chargeMainCannonEffect.start();
    }

    private void resetStates(){
        timeToBurst             = 0;
        timeToShoot             = 0;
        shootsFired             = 0;
        burstsFired             = 0;
        timeChargingMainCannon  = 0;
        selectedCannonToFire    = 0;

        orangeEnemyState = OrangeEnemyState.NONE;
        shootMainCannon = false;
    }

    public void update(float delta){
        super.update(delta);
        super.updateParticleEffect();

        switch(orangeEnemyState){
            case OPENING_SHIELD:
                shield.setX(shield.getX() + 10*delta);
                if(shield.getX() >= body.getX()){
                    orangeEnemyState = OrangeEnemyState.SHIELD_OPENED;
                }
                shootSecondaryCannonBurst(delta);
                break;
            case SHIELD_OPENED:
                chargeMainCannonEffect.update(delta);
                if(!shootMainCannon){
                    if(timeChargingMainCannon >= FREQUENCY_OF_SHOOT_MAIN_CANNON){
                        shootMainCannon = true;
                        timeToBurst=0;
                        timeToShoot=0;
                        shootsFired=0;
                        burstsFired=0;
                    }else{
                        timeChargingMainCannon+=delta;
                        chargeMainCannonEffect.getEmitters().first().getVelocity().setHigh(10+((timeChargingMainCannon*MAXIMUM_VELOCITY_CHARGING_EFFECT)/(FREQUENCY_OF_SHOOT_MAIN_CANNON)));
                    }
                }else{
                    chargeMainCannonEffect.getEmitters().first().getVelocity().setHigh((( ( (MAXIMUM_NUMBER_SHOOTS - (shootsFired + 1)) * MAXIMUM_VELOCITY_CHARGING_EFFECT) / MAXIMUM_NUMBER_SHOOTS) ));
                    this.shoot(delta);
                }
                break;
            case CLOSING_SHIELD:
                shield.setX(shield.getX() - 10*delta);
                if(shield.getX() <= INITIAL_X_SHIELD){
                    shield.setX(INITIAL_X_SHIELD);
                    resetStates();
                }
                break;
            case NONE:
                shootSecondaryCannonBurst(delta);
                if(burstsFired >= 3){
                    orangeEnemyState = OrangeEnemyState.OPENING_SHIELD;
                }
                break;
        }
    }

    private void shoot(float delta){
        if(timeToShoot >= FREQUENCY_OF_SHOOTING/5){
            float angle = MathUtils.random(110,250);
            ShootsManager.shootOneOrangeWeapon(this,(int)(getX() - this.getWidth()/2 +5),(int)(this.getY()+this.getHeight()/2),angle,ShootsManager.ship);
            timeToShoot=0;
            shootsFired++;
            if(shootsFired>= MAXIMUM_NUMBER_SHOOTS){
                shootMainCannon=false;
                orangeEnemyState = OrangeEnemyState.CLOSING_SHIELD;
                timeChargingMainCannon = 0;
            }
        }else{
            timeToShoot+=delta;
        }
    }

    private void shootSecondaryCannonBurst(float delta){
        // Esperamos a que sea el momento de iniciar una ráfaga
        if(timeToBurst > FREQUENCY_OF_BURST){
            if(selectedCannonToFire == 0)
                selectedCannonToFire = MathUtils.random(1,4);
            // Dentro de cada ráfaga esperamos a que sea el momento de cada disparo
            if(timeToShoot > FREQUENCY_OF_SHOOTING){
                // Disparamos siguiendo un patrón aleatorio entre los 4 cañones
                switch (selectedCannonToFire){
                    case 1:
                        cannonUpperLeft.shoot();
                        break;
                    case 2:
                        cannonUpperRight.shoot();
                        break;
                    case 3:
                        cannonLowerLeft.shoot();
                        break;
                    case 4:
                        cannonLowerRight.shoot();
                        break;
                }
                shootsFired++;
                timeToShoot = 0;
                if(shootsFired >= NUMBER_OF_SHOOTS){
                    selectedCannonToFire = 0;
                    shootsFired = 0;
                    burstsFired++;
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
        if(orangeEnemyState.equals(OrangeEnemyState.SHIELD_OPENED))
            chargeMainCannonEffect.draw(batch);
    }

    public void collideWithShoot(Shoot shoot) {
        super.damage(1);
        timeChargingMainCannon-=0.2;
        if(this.getVitality()%20 == 0)
            orangeEnemyState = OrangeEnemyState.CLOSING_SHIELD;
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

}
