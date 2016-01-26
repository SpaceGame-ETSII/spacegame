package com.tfg.spacegame.gameObjects.enemies;

import com.badlogic.gdx.math.MathUtils;
import com.tfg.spacegame.gameObjects.Enemy;

public class Type5 extends Enemy{

    //Indica la velocidad a la que se moverá el enemigo hasta un punto fijo
    public static final int SPEED = 50;
    //Indicará el número de píxeles que deberá recorrer el enemigo hasta detenerse
    private int pixelsToMove;

    public Type5(int x, int y) {
        super("enemigo_basico_tipo5", x, y);
        pixelsToMove = 650;
    }

    public void update(float delta){
        //Mientras el enemigo tenga distancia que recorrer, vamos actualizando su posición
        if (pixelsToMove > 0) {
            this.setX(this.getX() - SPEED * delta);
            pixelsToMove -= SPEED*delta;
        }
    }

}
