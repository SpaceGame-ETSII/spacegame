package com.tfg.spacegame.gameObjects.shoots;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.ShootsManager;
import com.tfg.spacegame.utils.TouchManager;


public class Fire extends Shoot {

    // Amplitud del efecto de disparo (el efecto de particulas)
    private static final int AMPLITUDE_OF_FIRE=15;

    // Lo usaremos para hacer el calculo del ángulo
    private Vector2 vector;

    // Almacena el punto objetivo del arma
    private Vector3 targetVector;

    // El efecto de disparo
    private ParticleEffect shoot;

    public Fire(GameObject shooter, float xTarget, float yTarget, ParticleEffect shoot) {
        super("yellow_shoot", 0, 0, shooter,null,null);

        if (!this.isFromShootOfEnemy()) {
            this.setX((int)(shooter.getX()+shooter.getWidth()+this.getHeight()));
        } else {
            this.setX((int)(shooter.getX()+this.getHeight()));
        }
        this.setY(getShooter().getY() + this.getHeight());
        this.getLogicShape().setOrigin(0,this.getHeight()/2);

        this.shoot = shoot;
        shoot.getEmitters().first().setPosition(this.getX() - this.getHeight()/2, this.getY()+this.getHeight()/2);

        shoot.start();

        vector = new Vector2();

        if (shooter instanceof Ship) {
            targetVector = TouchManager.getTouchFromPosition(xTarget, yTarget);
        } else {
            targetVector = new Vector3();
            targetVector.x = xTarget;
            targetVector.y = yTarget;
        }
    }

    public void update(float delta){
        setX(getShooter().getX()+getShooter().getWidth()+this.getHeight());
        setY(getShooter().getY() + this.getHeight());
        shoot.update(delta);
        shoot.getEmitters().first().setPosition(this.getX() - this.getHeight()/2, this.getY() + this.getHeight()/2);

        // Controlamos si el jugador sigue queriendo disparar
        // En caso de ser un enemigo el shooter, lo dejamos que dispare constantemente
        if(TouchManager.isTouchActive(targetVector) || this.isFromShootOfEnemy()) {

            if (this.isFromShootOfEnemy()) {
                targetVector.x = ShootsManager.ship.getX();
                targetVector.y = ShootsManager.ship.getY();
            }

            // Obtenemos el angulo a donde tenemos que girar
            vector.set(targetVector.x - this.getX(), targetVector.y - this.getY());

            // Basta con llamar al vector.angle para tener el angulo a girar
            float angle = vector.angle();

            // Rotamos el rectangulo de colisión y el efecto de particulas
            this.getLogicShape().setRotation(angle);

            ParticleEmitter.ScaledNumericValue angles = this.shoot.getEmitters().first().getAngle();
            angles.setLow(angle);
            angles.setHigh(angle-AMPLITUDE_OF_FIRE,angle+AMPLITUDE_OF_FIRE);
        }else{
            this.shock();
            shoot.allowCompletion();
            if (shoot.isComplete()) {
                this.changeToDeletable();
            }
        }
    }

    public boolean isFromShootOfEnemy() {
        return (this.getShooter() instanceof Shoot && ((Shoot) this.getShooter()).getShooter() instanceof Enemy);
    }

    public void render(SpriteBatch batch){
        shoot.draw(batch);
    }

    public void dispose(){
        super.dispose();
        shoot.dispose();
    }
}
