package com.tfg.spacegame.gameObjects.campaignMode.enemies.partsOfEnemy;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.campaignMode.Enemy;
import com.tfg.spacegame.gameObjects.campaignMode.Shoot;
import com.tfg.spacegame.gameObjects.campaignMode.enemies.PartOfEnemy;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShootsManager;

public class Eye extends PartOfEnemy {

    //Atributo para controlar si un ojo está abierto o cerrado
    private boolean closed;

    //Es el efecto de partículas que se mostrará para avisar de
    private ParticleEffect shootEffectWarning;

    //Frencuencia de disparo se refiere a cada cuantos segundos va a disparar
    private static final float FREQUENCY_OF_SHOOTING = 7f;

    //Variable para saber si el ojo está esperando a disparar o no
    private boolean waitToShoot;

    public Eye(String texture, int x, int y, Enemy father) {
        super(texture, x, y, 100, AssetsManager.loadParticleEffect("purple_destroyed"), father, true, true);
        this.waitToShoot = false;
        closed=true;

        //Creamos el efecto de particulas
        shootEffectWarning = AssetsManager.loadParticleEffect("purple_eye_warning");
        this.updateParticleEffect();

        //Iniciamos el efecto de partículas
        shootEffectWarning.start();
    }

    public void closeEye(){
        closed = true;
    }

    public boolean isClosed(){
        return closed;
    }

    public void openEye(){
        closed = false;
    }

    public boolean isWaitToShoot() {
        return waitToShoot;
    }

    public void setWaitToShoot(boolean change){
        waitToShoot = change;
    }

    public void waitToShoot(){
        waitToShoot = true;
    }

    public void update(float delta){
        super.update(delta);

        //Si está esperando para disparar
        if (isWaitToShoot()){
            //Actualizamos el efecto de partículas
            this.updateParticleEffect();
            shootEffectWarning.update(delta);
        }

    }

    public void updateParticleEffect() {
        if (this.isDefeated()){
            super.updateParticleEffect();
        }else
            //Si el ojo no está cerrado y está esperando, actualizamos el efecto de partículas
            if (!isClosed() && isWaitToShoot()){
                shootEffectWarning.getEmitters().first().setPosition(this.getCenter().x+15, this.getCenter().y+15);
            }
    }

    public void shoot(){
        //Para que el ojo pueda disparar debe de estar abierto y esperando para disparar
        if (!isClosed() && isWaitToShoot())
            ShootsManager.shootPurpleWeapon(this,0,0);
    }

    public void render() {
        //Si el ojo está abierto, se pintará la textura
        if (!closed) {
            super.render();
            //Si está esperando, pintamos el efecto de partículas
            if (isWaitToShoot())
                shootEffectWarning.draw(SpaceGame.batch);
        }

    }

    public boolean canCollide(){
        return !closed;
    }

    public void collideWithShoot(Shoot shoot) {
        if (canCollide()){
            this.damage(0);
            closeEye();
            waitToShoot = false;
        }
    }

    public void dispose() {
        super.dispose();
        if (shootEffectWarning.isComplete())
            shootEffectWarning.dispose();
    }
}
