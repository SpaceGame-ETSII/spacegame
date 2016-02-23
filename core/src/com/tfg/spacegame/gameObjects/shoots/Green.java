package com.tfg.spacegame.gameObjects.shoots;

import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShootsManager;

public class Green extends Rocket {

    public Green(GameObject shooter, int x, int y, float yTarget) {
        super("green_shoot", shooter, x , y, yTarget, 100,
                AssetsManager.loadParticleEffect("green_shoot_effect_shoot"),
                AssetsManager.loadParticleEffect("green_shoot_effect_shock"),
                AssetsManager.loadParticleEffect("green_propulsion_effect"));

        if (this.getShooter() instanceof Enemy) {
            ShootsManager.shootGreenFireWeapon(this, ShootsManager.ship.getX(), ShootsManager.ship.getY());
        }
    }

}