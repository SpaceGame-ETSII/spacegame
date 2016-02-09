package com.tfg.spacegame.gameObjects;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.tfg.spacegame.GameObject;

public class Enemy extends GameObject {

    //Indica si el enemigo se puede borrar de la pantalla, que será cuando termine el destroyEffect
    private boolean deletable;

    //Indica la posición inicial del enemigo
    private Vector2 initialPosition;

    //Indica la vitalidad del enemigo
    private int vitality;

    //Es el efecto de partículas que se mostrará al destruirse
    private ParticleEffect destroyEffect;

    public Enemy(String textureName, int x, int y, int vitality, ParticleEffect destroyEffect) {
        super(textureName, x, y);
        initialPosition = new Vector2(x,y);
        this.vitality = vitality;
        deletable = false;
        this.destroyEffect = destroyEffect;

        this.destroyEffect.getEmitters().first().setPosition(this.getX() + this.getWidth()/2, this.getY() + this.getHeight()/2);
        this.destroyEffect.start();
    }

    public void update(float delta) {
        //Sólo se hará si el enemigo ha sido derrotado, y se encargará de mostrar el efecto de partículas
        if (this.isDefeated()) {
            this.updateParticleEffect();
            if (destroyEffect.isComplete()) {
                this.changeToDeletable();
            }
            destroyEffect.update(delta);
        }
    }

    public void updateParticleEffect() {
        destroyEffect.getEmitters().first().setPosition(this.getX() + this.getWidth()/2, this.getY() + this.getHeight()/2);
    }

    public void render(SpriteBatch batch) {
        if (!this.isDefeated()) {
            super.render(batch);
        } else {
            destroyEffect.draw(batch);
        }
    }

    //Daña al enemigo con la cantidad indicada por parámetro
    public void damage(int damage) {
        vitality -= damage;
    }

    //Indica si el enemigo ha sido derrotado
    public boolean isDefeated(){
        return vitality <= 0;
    }

    //Cambia el enemigo a un estado de borrable
    public void changeToDeletable() {
        deletable = true;
    }

    //Indica si el enemigo es borrable o no
    public boolean isDeletable() {
        return deletable;
    }

    public Vector2 getInitialPosition(){
        return initialPosition;
    }

    public void collideWithShip() {}

    public void collideWithShoot(Shoot shoot) {}

    public void dispose() {
        super.dispose();
        destroyEffect.dispose();
    }
}
