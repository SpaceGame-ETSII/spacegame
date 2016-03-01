package com.tfg.spacegame.gameObjects.enemies;


import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;

public class Eye extends PartOfEnemy{

    //Atributo para controlar si un ojo está abierto o cerrado
    private boolean closed;

    public Eye(String texture, int x, int y, int vitality, ParticleEffect particleEffect, Enemy father, boolean damageable) {
        super(texture, x, y, vitality, particleEffect, father, damageable);
    }

    public void setClosed(boolean change){
        closed = change;
    }

    public boolean getClosed(){
        return closed;
    }

    public void render(SpriteBatch batch) {
        if (!this.isDefeated() && !closed) {
            super.render(batch);
        }
    }

    public void collideWithShoot(Shoot shoot) {
        if (isDamagable()){
            this.damage(1);
        }
        if (isDefeated()){
            setClosed(true);
        }
    }
}
