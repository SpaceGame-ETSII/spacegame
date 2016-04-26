package com.tfg.spacegame.gameObjects.campaignMode.shoots;

import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.enums.TypeShoot;


public class GreenFire extends Fire {

    public GreenFire(GameObject shooter, float xTarget, float yTarget) {
        super(shooter,xTarget,yTarget, AssetsManager.loadParticleEffect("green_shoot_effect"));
    }

}
