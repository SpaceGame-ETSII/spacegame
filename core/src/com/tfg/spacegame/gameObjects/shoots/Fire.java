package com.tfg.spacegame.gameObjects.shoots;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.ShapeRendererManager;
import com.tfg.spacegame.utils.TouchManager;


public class Fire extends Shoot {

    // Amplitud del efecto de disparo (el efecto de particulas)
    private static final int AMPLITUDE_OF_FIRE=10;
    private static final int SHOOT_EFFECT_LIFE = 225;
    private final float FULL_WIDTH;
    private static final float CHANGE_IN_SCALLING = 0.02f;
    private float actualReasonOfScaling;

    // Lo usaremos para hacer el calculo del ángulo
    private Vector2 vector;

    // Almacena el punto objetivo del arma
    private Vector3 targetVector;

    // El efecto de disparo
    private ParticleEffect shoot;

    public Fire(GameObject shooter, float xTarget, float yTarget, ParticleEffect shoot) {
        super("yellow_shoot", 0, 0, shooter, null, null);
        this.setX((int) (shooter.getX() + shooter.getWidth() + this.getHeight()));
        this.setY(getShooter().getY() + this.getHeight());
        this.getLogicShape().setOrigin(0,this.getHeight()/2);

        FULL_WIDTH = getWidth();
        actualReasonOfScaling = 1.0f;

        this.shoot = shoot;
        shoot.getEmitters().first().setPosition(this.getX() - this.getHeight()/2, this.getY()+this.getHeight()/2);

        shoot.start();

        vector = new Vector2();

        targetVector = TouchManager.getTouchFromPosition(xTarget, yTarget);

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

            // Gestionamos el escalado del logicShape dependiendo si ha colisionado o no con un enemigo
            // Si la razón de escalado es menor que 1.0f es que
            if(actualReasonOfScaling < 1.0f){
                actualReasonOfScaling+= CHANGE_IN_SCALLING/2;
            }else{
                actualReasonOfScaling = 1.0f;
            }

            this.getLogicShape().setScale( actualReasonOfScaling , 1.0f);

            shoot.getEmitters().first().getLife().setHigh(SHOOT_EFFECT_LIFE * actualReasonOfScaling);

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
        ShapeRendererManager.renderPolygon(this.getLogicShape().getTransformedVertices(),Color.WHITE);
    }

    public void collideWithEnemy(Enemy enemy) {
        // Como ha colisionado con un enemigo, tenemos que reducir el ancho del logicShape
        // además de reducir el efecto de particulas
        // Esto lo hacemos reduciendo su factor de escalado
        actualReasonOfScaling -= CHANGE_IN_SCALLING;
    }

    public void dispose(){
        super.dispose();
        shoot.dispose();
    }
}
