package com.tfg.spacegame.gameObjects.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.gameObjects.shoots.Basic;
import com.tfg.spacegame.gameObjects.shoots.Yellow;
import com.tfg.spacegame.utils.AssetsManager;

public class YellowEnemy extends Enemy{

    // Velocidad del enemigo
    private static final int SPEED = 200;

    // Vector de movimiento del enemigo
    private Vector2 movement;

    // Variable de control para saber si se ha alcanzado el punto de "inserción"
    // Con punto de inserción nos referimos a: el vector de movimiento no cambiará
    private boolean positionReached;
    private static final float INSERTION_POINT=0.15f;

    // A continuación se definen las variables para el efecto sinusoidal o "ola"
    // Velocidad para el cambio en grados
    private static final int INCREMENTAL_DEGREE_SPEED = 150;
    // Amplitud del efecto sinusoidal
    private static final int WAVE_AMPLITUDE = 70;
    // Guardamos hacía donde está girando ahora mismo, si es hacia arriba (true) o hacia abajo (false)
    private boolean waveDirection;
    // Variable para guardar cuantos grados de inclinación va teniendo
    private float waveDegree;

    private Ship target;

    public YellowEnemy(int x, int y, Ship target) {
        super("yellow_enemy", x, y, 15, AssetsManager.loadParticleEffect("yellow_enemy_defeated"));

        movement = new Vector2();

        // Hacemos un Random entre -WAVE_AMPLITUDE y WAVE_AMPLITUDE
        // Para otorgar variedad en el inicio del efecto sinusoidal
        waveDegree = MathUtils.random(-WAVE_AMPLITUDE,WAVE_AMPLITUDE);
        // Hacemos un Random para saber en que dirección inicial se iniciará
        // el efecto
        waveDirection = MathUtils.randomBoolean();

        positionReached = false;

        this.target = target;
    }

    public void update(float delta){
        super.update(delta);
        // Mientras la nave no haya sido derrotada
        if(!isDefeated()){
            // Mientras la posición de "incursión" no haya sido alcanzada
            if(!positionReached){
                // Situamos el punto de ataque en la punta del enemigo amarillo
                float srcx = this.getX() + this.getWidth()/2;
                float srcy = this.getY();

                // Calculamos el vector desde el punto origen al punto destino (centro de la nave)
                movement.set(target.getX()+target.getWidth()/2 - srcx, target.getY() - srcy);

                // Calculamos el vector movimiento que debe de hacer el enemigo
                // Efecto sinusoidal -> Y = speed * sen(angle+wave)
                movement.set(SPEED * delta * MathUtils.cosDeg(movement.angle()), SPEED * delta * MathUtils.sinDeg(movement.angle() + waveDegree));

                // Actualizamos el efecto sinusoidal
                calculateWaveEffect(delta);

                // Actualizamos la posición del enemigo
                this.setX(this.getX() + movement.x);
                this.setY(this.getY() + movement.y);

                // Comprobamos si el punto de inserción ha sido alcanzado
                if(getX()/SpaceGame.width < INSERTION_POINT)
                    positionReached=true;
            }else{
                // Añadimos un plus del 20% de velocidad a la nave para el movimiento de inserción
                movement.set(SPEED*1.2f * delta * MathUtils.cosDeg(movement.angle()), SPEED*1.2f * delta * MathUtils.sinDeg(movement.angle()));
                this.setX(this.getX() + movement.x);
                this.setY(this.getY() + movement.y);
            }
        }
    }

    private void calculateWaveEffect(float delta){
        if(waveDirection){
            waveDegree +=INCREMENTAL_DEGREE_SPEED*delta;
            if (waveDegree >= WAVE_AMPLITUDE *getX()/SpaceGame.width)
                waveDirection = false;
        }
        else{
            waveDegree -=INCREMENTAL_DEGREE_SPEED*delta;
            if (waveDegree < -WAVE_AMPLITUDE *getX()/SpaceGame.width)
                waveDirection = true;
        }
    }

    public void collideWithShip(){
        this.damage(15);
    }

    public void collideWithShoot(Shoot shoot){
        if(shoot instanceof Yellow)
            this.damage(3);

        if(shoot instanceof Basic)
            this.damage(1);
    }

    public void render(SpriteBatch batch){
        if(!isDefeated())
            super.renderRotate(batch, movement.angle());
        else
            super.render(batch);
    }
}
