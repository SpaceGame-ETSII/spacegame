package com.tfg.spacegame.gameObjects.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.gameObjects.shoots.Basic;
import com.tfg.spacegame.gameObjects.shoots.Purple;
import com.tfg.spacegame.utils.AssetsManager;

public class PurpleEnemy extends Enemy {

    //Indica la velocidad a la que se moverá el enemigo
    private static final int SPEED = 75;

    //Valor inicial que tendrá el contador de disparo cada vez que se reinicie
    public static final int INITIAL_COUNTER = 150;

    //Valor inicial que tendrá el contador para indicar cada cuanto se reinicirá en combate con el enemigo
    public static final int RESTART_COUNTER = 1500;

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
    private float counterToShoot;

    //Contador para resetear los ojos del enemigo
    private float timeToRestart;

    public PurpleEnemy(int x, int y) {
        super("purple_eye_center", x, y, 30, AssetsManager.loadParticleEffect("purple_destroyed"));

        body = new PartOfEnemy("purple_body", x, y, 1,
                AssetsManager.loadParticleEffect("purple_destroyed"), this, false);
        eye1 = new Eye("purple_eye_1", x + 35, y + 380, 1,
                AssetsManager.loadParticleEffect("purple_destroyed"), this, true, false);
        eye2 = new Eye("purple_eye_2", x + 10, y + 260, 1,
                AssetsManager.loadParticleEffect("purple_destroyed"), this, true, false);
        eye3 = new Eye("purple_eye_3", x + 10, y + 145, 1,
                AssetsManager.loadParticleEffect("purple_destroyed"), this, true, false);
        eye4 = new Eye("purple_eye_4", x + 36, y + 25, 1,
                AssetsManager.loadParticleEffect("purple_destroyed"), this, true, false);

        this.setX(body.getX() + 175);
        this.setY(body.getY() + 165);

        isReady = false;
        counterToShoot = INITIAL_COUNTER;
        timeToRestart = RESTART_COUNTER;
    }

    public void update(float delta) {
        super.update(delta);

        if (!this.isDefeated()) {
            //El enemigo avanza hasta un punto designado, una vez allí ya estará listo para disparar
            if (body.getX() >= 450) {
                this.setX(this.getX() - SPEED * delta);
                body.setX(body.getX() - SPEED * delta);
                eye1.setX(eye1.getX() - SPEED * delta);
                eye2.setX(eye2.getX() - SPEED * delta);
                eye3.setX(eye3.getX() - SPEED * delta);
                eye4.setX(eye4.getX() - SPEED * delta);
            }else {
                if (isReady==false)
                    eye1.setWaiting(true);
                isReady = true;
            }

            //Si el enemigo está listo y ha terminado el contador, disparará y lo reiniciamos
            if (isReady && counterToShoot <= 0) {
                this.shoot();
                counterToShoot = INITIAL_COUNTER;
            } else if (eye1.isWaiting() || eye2.isWaiting() || eye3.isWaiting() || eye4.isWaiting()){
                counterToShoot -= delta * SPEED;
            }

            //Si no has derrotado al enemigo en el tiempo designado, los ojos volverán a aparecer y el combate se reiniciará
            if (timeToRestart <= 0){
                eye1.setClosed(false);
                eye1.setWaiting(true);
                eye2.setClosed(false);
                eye3.setClosed(false);
                eye4.setClosed(false);
                timeToRestart = RESTART_COUNTER;
            } else if (isReady && (eye1.getClosed() || eye2.getClosed() || eye3.getClosed() || eye4.getClosed())){
                timeToRestart -= delta * SPEED;
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

    public void changeToDeletable() {
        super.changeToDeletable();
        body.changeToDeletable();
        eye1.changeToDeletable();
        eye2.changeToDeletable();
        eye3.changeToDeletable();
        eye4.changeToDeletable();
    }

    public void shoot(){
        if (eye1.isWaiting() && isReady) {
            eye1.shoot();
            eye1.setWaiting(false);
            eye2.setWaiting(true);
        }else if (eye2.isWaiting()){
            eye2.shoot();
            eye2.setWaiting(false);
            eye3.setWaiting(true);
        }else if (eye3.isWaiting()){
            eye3.shoot();
            eye3.setWaiting(false);
            eye4.setWaiting(true);
        }else if (eye4.isWaiting()){
            eye4.shoot();
            eye4.setWaiting(false);
            eye1.setWaiting(true);
        }
    }

    public void collideWithShoot(Shoot shoot) {
        if (this.isDamagable()) {
            if (shoot instanceof Basic){
                //Si el enemigo es alcanzado por un disparo de tipo básico, sólo recibirá un punto de daño
                this.damage(1);
            }else if (shoot instanceof Purple){
                //Si por el contrario, es alcanzado por un disparo de tipo morado, perderá tres puntos de vida
                this.damage(3);
            }
        }
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
