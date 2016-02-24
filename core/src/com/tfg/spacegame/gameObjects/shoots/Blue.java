package com.tfg.spacegame.gameObjects.shoots;

import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.utils.AssetsManager;

public class Blue extends Rocket {

    public Blue(GameObject shooter, int x, int y, float yTarget) {
        super("blue_shoot", shooter, x , y, yTarget, 350,
                AssetsManager.loadParticleEffect("blue_shoot_effect_shoot"),
                AssetsManager.loadParticleEffect("blue_shoot_effect_shock"),
                AssetsManager.loadParticleEffect("blue_propulsion_effect"));
    }

}
