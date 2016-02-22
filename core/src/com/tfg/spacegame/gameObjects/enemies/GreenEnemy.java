package com.tfg.spacegame.gameObjects.enemies;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.gameObjects.shoots.Basic;
import com.tfg.spacegame.gameObjects.shoots.Green;
import com.tfg.spacegame.gameObjects.shoots.GreenFire;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShootsManager;

public class GreenEnemy extends Enemy {

    private static final int SPEED = 50;

    // El escudo del enemigo
    private PartOfEnemy shield;

    // Direccion de movimiento
    private int direction;

    public GreenEnemy(int x, int y) {
        super("green_body", x, y, 15, AssetsManager.loadParticleEffect("green_destroyed"));
        shield = new PartOfEnemy("green_shield", x - 56,y - 33, 15,
                                    AssetsManager.loadParticleEffect("green_destroyed"), this, false);

        direction = 1;
    }

    public void update(float delta) {
        super.update(delta);

        if (!this.isDefeated()) {
            // Evitamos que el enemigo se salga de la pantalla tanto por arriba como por abajo
            if(this.getY() + this.getShield().getHeight() > SpaceGame.height){
                direction = -1;
            } else if(this.getY() < 0 ){
                direction = 1;
            }

            // Movemos las tres partes del enemigo
            this.setY(this.getY() + SPEED * delta * direction);
            shield.setY(shield.getY() + SPEED * delta * direction);
            System.out.println(this.getY());
        }
    }

    public PartOfEnemy getShield() {
        return shield;
    }

    public void render(SpriteBatch batch){
        super.render(batch);
        if (!this.isDefeated()) {
            shield.render(batch);
        }
    }

    public void shoot(){
        ShootsManager.shootOneBasicWeapon(this);
    }

    public void collideWithShoot(Shoot shoot) {
        if (this.isDamagable()) {
            if (shoot instanceof Basic){
                //Si el enemigo es alcanzado por un disparo de tipo básico, sólo recibirá un punto de daño
                this.damage(1);
            }else if (shoot instanceof Green || shoot instanceof GreenFire){
                //Si por el contrario, es alcanzado por un disparo de tipo verde, perderá tres puntos de vida
                this.damage(3);
            }
        }
    }

    public void dispose(){
        super.dispose();
        shield.dispose();
    }

}
