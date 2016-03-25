package com.tfg.spacegame.gameObjects.campaignMode.shoots;

import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.screens.CampaignScreen;
import com.tfg.spacegame.gameObjects.campaignMode.Enemy;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShootsManager;
import com.tfg.spacegame.utils.enums.TypeShoot;

public class Green extends Rocket {

    //Indica si el arma está disparando fuego
    private boolean isShootingFire;

    //Contador para saber cuando disparar fuego en caso de hacerlo un enemigo
    private float counter;

    public Green(GameObject shooter, int x, int y, float yTarget) {
        super("green_shoot", shooter, x , y, yTarget, 100,
                AssetsManager.loadParticleEffect("green_shoot_effect_shoot"),
                AssetsManager.loadParticleEffect("green_shoot_effect_shock"),
                AssetsManager.loadParticleEffect("green_propulsion_effect"));

        counter = 100;
        isShootingFire = false;
        // Establememos el tipo del arma
        type = TypeShoot.GREEN;
    }

    public void update(float delta) {
        super.update(delta);

        //Si el shooter es un enemigo, necesitamos actualizar el tiempo para disparar el fuego
        if (this.getShooter() instanceof Enemy && !this.isShootingFire) {
            if (counter < 0) {
                ShootsManager.shootGreenFireWeapon(this, CampaignScreen.ship.getX(), CampaignScreen.ship.getY());
                isShootingFire = true;
            } else {
                counter -= this.speed * delta;
            }
        }
    }

}