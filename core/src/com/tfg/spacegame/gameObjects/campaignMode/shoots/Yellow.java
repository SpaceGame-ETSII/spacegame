package com.tfg.spacegame.gameObjects.campaignMode.shoots;


import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.enums.TypeShoot;

public class Yellow extends Fire {

    public Yellow(GameObject shooter, float xTarget, float yTarget) {
        super(shooter,xTarget,yTarget,AssetsManager.loadParticleEffect("yellow_shoot_effect"));
    }

}