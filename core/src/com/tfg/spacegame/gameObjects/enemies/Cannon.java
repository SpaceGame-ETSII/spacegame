package com.tfg.spacegame.gameObjects.enemies;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShootsManager;

public class Cannon extends PartOfEnemy{


    private Vector2 shootingPosition;
    private float shootAngle;

    public Cannon(float x, float y, Enemy father, float xShoot, float yShoot, float angle) {
        super("orange_enemy_cannon", 0, 0, 7, AssetsManager.loadParticleEffect("basic_destroyed"), father, false);
        this.setX(x);
        this.setY(y);
        shootingPosition = new Vector2(xShoot,yShoot);
        shootAngle = angle;

    }

    public void shoot(){
        ShootsManager.shootOneOrangeWeapon(this, (int)shootingPosition.x, (int)shootingPosition.y, shootAngle, ShootsManager.ship);
    }
}
