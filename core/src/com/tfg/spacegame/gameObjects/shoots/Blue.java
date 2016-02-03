package com.tfg.spacegame.gameObjects.shoots;

import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.Shoot;

public class Blue extends Shoot {

    public Blue(GameObject shooter, int x, int y) {
        super("blue_shoot",x,y,shooter);
    }

    public void updateParticleEffect() {

    }

}
