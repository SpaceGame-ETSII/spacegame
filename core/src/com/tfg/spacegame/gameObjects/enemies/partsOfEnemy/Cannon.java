package com.tfg.spacegame.gameObjects.enemies.partsOfEnemy;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.gameObjects.enemies.PartOfEnemy;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShootsManager;

public class Cannon extends PartOfEnemy {

    private final float FREQUENCY_OF_DISABLE_EFFECT = 8f;
    private final int   MAX_EMISSION_DISABLE_EFFECT = 250;
    private final int   MAX_VITALITY = 10;

    private float timeDisableEffect;
    private Vector2 shootingPosition;
    private float shootAngle;
    private ParticleEffect disabledEffect;
    private boolean disable;

    public Cannon(float x, float y, Enemy father, float xShoot, float yShoot, float angle) {
        super("orange_enemy_cannon", 0, 0, 10, AssetsManager.loadParticleEffect("basic_destroyed"), father, true);

        this.setX(x);
        this.setY(y);
        shootingPosition = new Vector2(xShoot,yShoot);
        shootAngle = angle;
        timeDisableEffect = 0;
        disabledEffect = AssetsManager.loadParticleEffect("orange_secondary_cannon_disabled");
        disabledEffect.getEmitters().first().setPosition(shootingPosition.x,shootingPosition.y);
        disabledEffect.getEmitters().first().getAngle().setLow(shootAngle-30,shootAngle+30);
    }

    public void shoot(){
        ShootsManager.shootOneOrangeWeapon(this, (int)shootingPosition.x, (int)shootingPosition.y, shootAngle, ShootsManager.ship);
    }
    public void move(float speed){
        this.setX(this.getX() + speed);
        disabledEffect.getEmitters().first().setPosition(getCenter().x,getCenter().y);
        shootingPosition.x += speed;
    }
    public void update(float delta){
        super.update(delta);
        if(disable){
            disabledEffect.update(delta);
            if(timeDisableEffect >= FREQUENCY_OF_DISABLE_EFFECT){
                this.setVitality(MAX_VITALITY);
                timeDisableEffect = 0;
                disable=false;
                disabledEffect.reset();
            }else{
                timeDisableEffect+=delta;
                disabledEffect.getEmitters().first().getEmission().setHigh(( (FREQUENCY_OF_DISABLE_EFFECT - timeDisableEffect ) * MAX_EMISSION_DISABLE_EFFECT) / FREQUENCY_OF_DISABLE_EFFECT);
            }
        }

    }
    public void render(SpriteBatch batch) {
        super.render(batch);
        if (disable)
            disabledEffect.draw(batch);
    }

    public boolean isDisable(){
        return disable;
    }
    public void collideWithShoot(Shoot shoot) {
        if(!disable)
            this.damage(1);

        if(getVitality() < 3)
            disable = true;
    }

    public boolean isDamagable() {
        return damageable;
    }
}
