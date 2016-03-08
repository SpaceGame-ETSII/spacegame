package com.tfg.spacegame.gameObjects.enemies;


import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShootsManager;

public class Eye extends PartOfEnemy{

    //Atributo para controlar si un ojo está abierto o cerrado
    private boolean closed;

    //Es el efecto de partículas que se mostrará para avisar de
    private ParticleEffect shootEffectWarning;

    //Frencuencia de disparo se refiere a cada cuantos segundos va a disparar
    private static final float FREQUENCY_OF_SHOOTING = 7f;

    //Tiempo necesario para que dispare
    private float timeToShoot;

    //Variable de control para saber si ha disparado o no
    private boolean hasShooted;

    //Variable para saber si el ojo está esperando a disparar o no
    private boolean isWaiting;

    public Eye(String texture, int x, int y, int vitality, ParticleEffect particleEffect, Enemy father, boolean damageable, boolean isWaiting) {
        super(texture, x, y, vitality, particleEffect, father, damageable);

        //Inicializamos las variables
        timeToShoot = FREQUENCY_OF_SHOOTING;
        hasShooted = false;
        this.isWaiting = isWaiting;

        //Creamos el efecto de particulas
        shootEffectWarning = AssetsManager.loadParticleEffect("purple_eye_warning");
        this.updateParticleEffect();

        //Iniciamos el efecto de partículas
        shootEffectWarning.start();
    }

    public void setClosed(boolean change){
        closed = change;
    }

    public boolean getClosed(){
        return closed;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public void setWaiting(boolean change){
        isWaiting = change;
    }

    public void update(float delta){
        super.update(delta);

        if (!getClosed() && isWaiting()) {
            //Si hemos sobrepasado el tiempo para disparar
            if (timeToShoot < 0) {
                //Si no ha disparado aún el enemigo
                if (!hasShooted && isWaiting) {
                    //El enemigo dispara y lo hacemos saber a la variable de control
                    this.shoot();
                    hasShooted = true;

                } else {
                    //Reseteamos todos los tiempos y controles
                    hasShooted = false;
                    timeToShoot = FREQUENCY_OF_SHOOTING;
                }
            } else {
                //Actualizamos el tiempo para disparar
                timeToShoot -= delta;

                if (isWaiting()){
                    //Actualizamos el efecto de partículas
                    this.updateParticleEffect();
                    shootEffectWarning.update(delta);
                }

            }
        }

    }

    public void updateParticleEffect() {
        if (this.isDefeated()){
            super.updateParticleEffect();
        }else if (!getClosed() && isWaiting()){
            shootEffectWarning.getEmitters().first().setPosition(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2);
        }
    }

    public void shoot(){
        if (!getClosed() && isWaiting())
            ShootsManager.shootPurpleWeapon(this,0,0);
    }

    public void render(SpriteBatch batch) {
        if (!closed) {
            super.render(batch);
            if (isWaiting())
                shootEffectWarning.draw(batch);
        }

    }

    public boolean isDamagable(){
        return !closed;
    }

    public void collideWithShoot(Shoot shoot) {
        if (isDamagable()){
            this.damage(1);
            setClosed(true);
        }
    }

    public void dispose() {
        super.dispose();
        if (shootEffectWarning.isComplete())
            shootEffectWarning.dispose();
    }
}
