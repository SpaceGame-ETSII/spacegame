package com.tfg.spacegame.gameObjects.campaignMode.enemies;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.tfg.spacegame.gameObjects.campaignMode.Enemy;
import com.tfg.spacegame.gameObjects.campaignMode.Shoot;

public class PartOfEnemy extends Enemy {

    //Enemigo al que pertece la parte
    private Enemy father;

    //Indica si esta parte daña al enemigo padre
    public boolean damageable;

    public PartOfEnemy(String texture, int x, int y, int vitality, ParticleEffect particleEffect, Enemy father, boolean damageable) {
        super(texture, x, y, vitality, particleEffect);
        this.father = father;
        this.damageable = damageable;
    }

    public void render() {
        if (!father.isDefeated()) {
            super.render();
        }
    }

    //Si choca con un disparo y es dañable, se resta vitalidad al enemigo padre
    public void collideWithShoot(Shoot shoot) {
        if (damageable)
            father.damage(1);
    }

}