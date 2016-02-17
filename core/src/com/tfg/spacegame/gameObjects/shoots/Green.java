package com.tfg.spacegame.gameObjects.shoots;

import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.utils.AssetsManager;

public class Green extends Rocket {

    public Green(GameObject shooter, int x, int y, float yTarget) {
        super("green_shoot", shooter, x , y, yTarget, 150,
                AssetsManager.loadParticleEffect("green_shoot_effect_shoot"),
                AssetsManager.loadParticleEffect("green_shoot_effect_shock"),
                AssetsManager.loadParticleEffect("green_propulsion_effect"));
    }

}