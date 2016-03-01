package com.tfg.spacegame.gameObjects.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.gameObjects.shoots.Basic;
import com.tfg.spacegame.gameObjects.shoots.Green;
import com.tfg.spacegame.gameObjects.shoots.GreenFire;
import com.tfg.spacegame.gameObjects.shoots.Purple;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShootsManager;

public class PurpleEnemy extends Enemy {

    //Indica la velocidad a la que se moverá el enemigo
    private static final int SPEED = 75;

    //Valor inicial que tendrá el contador de disparo cada vez que se reinicie
    public static final int INITIAL_COUNTER = 250;

    //Partes referentes a los ojos del enemigo
    private Eye eye1;
    private Eye eye2;
    private Eye eye3;
    private Eye eye4;

    //Parte del enemigo correspondiente al cuerpo del enemigo
    private PartOfEnemy body;

    //Indicará cuándo a partir de cuándo puede disparar el enemigo
    private boolean isReady;

    //Contador que usaremos para saber cuándo disparar
    private float counter;

    public PurpleEnemy(int x, int y) {
        super("purple_eye_center", x, y, 50, AssetsManager.loadParticleEffect("purple_destroyed"));

        body = new PartOfEnemy("purple_body", x, y, 1,
                AssetsManager.loadParticleEffect("purple_destroyed"), this, false);
        eye1 = new Eye("purple_eye_1", x + 35, y + 380, 1,
                AssetsManager.loadParticleEffect("purple_eye_destroyed"), this, true);
        eye2 = new Eye("purple_eye_2", x + 10, y + 260, 1,
                AssetsManager.loadParticleEffect("purple_eye_destroyed"), this, true);
        eye3 = new Eye("purple_eye_3", x + 10, y + 145, 1,
                AssetsManager.loadParticleEffect("purple_eye_destroyed"), this, true);
        eye4 = new Eye("purple_eye_4", x + 36, y + 25, 1,
                AssetsManager.loadParticleEffect("purple_eye_destroyed"), this, true);

        this.setX(body.getX() + 175);
        this.setY(body.getY() + 165);

        isReady = false;
        counter = INITIAL_COUNTER;
    }

    public void update(float delta) {
        super.update(delta);

        if (!this.isDefeated()) {
            if (body.getX() >= 450) {
                this.setX(this.getX() - SPEED * delta);
                body.setX(body.getX() - SPEED * delta);
                eye1.setX(eye1.getX() - SPEED * delta);
                eye2.setX(eye2.getX() - SPEED * delta);
                eye3.setX(eye3.getX() - SPEED * delta);
                eye4.setX(eye4.getX() - SPEED * delta);
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
        partsOfEnemy.add(body);
        partsOfEnemy.add(eye1);
        partsOfEnemy.add(eye2);
        partsOfEnemy.add(eye3);
        partsOfEnemy.add(eye4);

        return partsOfEnemy;
    }

    public void render(SpriteBatch batch){
        if (eye1.getClosed() && eye2.getClosed() && eye3.getClosed() && eye4.getClosed()) {
            super.render(batch);
        }
    }

    public void shoot(){
        ShootsManager.shootPurpleWeapon(this,0,0);
    }

    public void collideWithShoot(Shoot shoot) {
        if (this.isDamagable()) {
            if (shoot instanceof Basic){
                //Si el enemigo es alcanzado por un disparo de tipo básico, sólo recibirá un punto de daño
                this.damage(10);
            }else if (shoot instanceof Purple){
                //Si por el contrario, es alcanzado por un disparo de tipo morado, perderá tres puntos de vida
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
        body.changeToDeletable();
    }

    public void dispose(){
        super.dispose();
        eye1.dispose();
        eye2.dispose();
        eye3.dispose();
        eye4.dispose();
        body.dispose();
    }

}
