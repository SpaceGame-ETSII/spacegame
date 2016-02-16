package com.tfg.spacegame.gameObjects.shoots;


import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.TouchManager;

public class Yellow extends Shoot{

    // Amplitud del efecto de disparo (el efecto de particulas)
    private static final int AMPLITUDE_OF_FIRE=15;

    // Lo usaremos para hacer el calculo del ángulo
    private Vector2 vector;

    private Vector3 targetVector;

    // El efecto de disparo
    private ParticleEffect shoot;

    public Yellow(GameObject shooter, float x, float y) {
        super("yellow_shoot", 0, 0, shooter,null,null);
        this.setX((int)(shooter.getX()+shooter.getWidth()+this.getHeight()));
        this.setY(getShooter().getY() + this.getHeight());
        this.getLogicShape().setOrigin(0,this.getHeight()/2);

        shoot = AssetsManager.loadParticleEffect("yellow_shoot_effect");
        shoot.getEmitters().first().setPosition(this.getX() - this.getHeight()/2, this.getY()+this.getHeight()/2);

        shoot.start();

        vector = new Vector2();

        targetVector = TouchManager.getTouchFromPosition(x,y);
    }

    public void update(float delta){
        setX(getShooter().getX()+getShooter().getWidth()+this.getHeight());
        setY(getShooter().getY() + this.getHeight());
        shoot.update(delta);
        shoot.getEmitters().first().setPosition(this.getX() - this.getHeight()/2, this.getY() + this.getHeight()/2);

        // Controlamos si el jugador sigue queriendo disparar
        if(TouchManager.isTouchActive(targetVector)){
            // Obtenemos el angulo a donde tenemos que girar
            float x1 = this.getX();
            float y1 = this.getY();
            float x2 = this.getX()+this.getWidth();
            // Dependiendo de si ha habido multitouch o no obtenemos el valor Y correspondiente
            float y2 = targetVector.y;

            vector.set(x2-x1,y2-y1);
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

    public void render(SpriteBatch batch){
        shoot.draw(batch);
    }

    public void dispose(){
        super.dispose();
        shoot.dispose();
    }
}