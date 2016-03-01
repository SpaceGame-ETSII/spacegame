package com.tfg.spacegame.gameObjects.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.gameObjects.shoots.Basic;
import com.tfg.spacegame.gameObjects.shoots.Green;
import com.tfg.spacegame.gameObjects.shoots.GreenFire;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShootsManager;

public class PurpleEnemy extends Enemy {

    //Indica la velocidad a la que se moverá el enemigo
    private static final int SPEED = 50;

    //Valor inicial que tendrá el contador de disparo cada vez que se reinicie
    public static final int INITIAL_COUNTER = 250;

    //Partes referentes a los ojos del enemigo
    private PartOfEnemy eye1;
    private PartOfEnemy eye2;
    private PartOfEnemy eye3;
    private PartOfEnemy eye4;
    private PartOfEnemy center;

    //Indicará cuándo a partir de cuándo puede disparar el enemigo
    private boolean isReady;

    //Contador que usaremos para saber cuándo disparar
    private float counter;

    public PurpleEnemy(int x, int y) {
        super("purple_body", x, y, 15, AssetsManager.loadParticleEffect("purple_destroyed"));

        eye1 = new PartOfEnemy("purple_eye_1", x + 35, y + 380, 15,
                AssetsManager.loadParticleEffect("purple_eye_destroyed"), this, false);
        eye2 = new PartOfEnemy("purple_eye_2", x + 10, y + 260, 15,
                AssetsManager.loadParticleEffect("purple_eye_destroyed"), this, false);
        eye3 = new PartOfEnemy("purple_eye_3", x + 10, y + 145, 15,
                AssetsManager.loadParticleEffect("purple_eye_destroyed"), this, false);
        eye4 = new PartOfEnemy("purple_eye_4", x + 36, y + 25, 15,
                AssetsManager.loadParticleEffect("purple_eye_destroyed"), this, false);
        center = new PartOfEnemy("purple_eye_center", x + 183, y + 165, 15,
                AssetsManager.loadParticleEffect("purple_destroyed"), this, false);

        isReady = false;
        counter = INITIAL_COUNTER;
    }

    public void update(float delta) {
        super.update(delta);

        if (!this.isDefeated()) {
            if (this.getX() >= 450) {
                this.setX(this.getX() - SPEED * delta);
                eye1.setX(eye1.getX() - SPEED * delta);
                eye2.setX(eye2.getX() - SPEED * delta);
                eye3.setX(eye3.getX() - SPEED * delta);
                eye4.setX(eye4.getX() - SPEED * delta);
                center.setX(center.getX() - SPEED * delta);
            }

            //Si el enemigo está listo y ha terminado el contador, disparará y lo reiniciamos
            if (isReady && counter <= 0) {
                //this.shoot();
                counter = INITIAL_COUNTER;
            } else {
                counter -= delta * SPEED;
            }

        }
    }

    public Array<PartOfEnemy> getPartsOfEnemy() {
        Array<PartOfEnemy> partsOfEnemy = new Array<PartOfEnemy>();
        partsOfEnemy.add(eye1);
        partsOfEnemy.add(eye2);
        partsOfEnemy.add(eye3);
        partsOfEnemy.add(eye4);
        partsOfEnemy.add(center);
        return partsOfEnemy;
    }

    public void render(SpriteBatch batch){
        super.render(batch);
    }

    public void shoot(){
        ShootsManager.shootPurpleWeapon(this,0,0);
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

    public void changeToDeletable() {
        super.changeToDeletable();
        eye1.changeToDeletable();
        eye2.changeToDeletable();
        eye3.changeToDeletable();
        eye4.changeToDeletable();
        center.changeToDeletable();
    }

    public void dispose(){
        super.dispose();
        eye1.dispose();
        eye2.dispose();
        eye3.dispose();
        eye4.dispose();
        center.dispose();
    }

}
