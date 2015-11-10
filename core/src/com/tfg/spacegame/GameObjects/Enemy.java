package com.tfg.spacegame.GameObjects;

import com.tfg.spacegame.GameObject;

public class Enemy extends GameObject {

    public boolean isDefeated;
    public float timeToRespawn;
    public float timeToLaunch;
    public float speed;

    public Enemy(int x, int y) {
        super("enemy", x, y);

        restart();
    }

    public void update(float delta) {
        //Retrocede el contador, y si éste llega a 0, vuelve el enemigo a la pantalla
        if (isDefeated) {
            timeToRespawn -= delta;
            if (timeToRespawn <= 0.0f)
                restart();
        } else {
            //Realizamos el movimiento de la nave
            if (this.getX() > 700)
                this.setX(this.getX() - speed);
            else if (this.getX() == 700 && timeToLaunch > 0.0)
                timeToLaunch -= delta;
            else {
                speed += 1;
                this.setX(this.getX() - speed);
            }

            //Controlamos si el enemigo se sale de la pantalla
            if (this.getX() < -40) {
                restart();
            }

        }

    }

    //Método que sirve para reiniciar el estado del enemigo
    public void restart() {
        //Por defecto el enemigo no está derrotado
        isDefeated = false;

        //Posición inicial
        this.setX(800);

        //Tiempo que tarda en aparecer una vez ha sido derrotado
        timeToRespawn = 3.0f;

        //Tiempo que tardará en salir disparado hacia la izquierda de la pantalla
        timeToLaunch = 2.0f;

        //Velocidad a la que se moverá el enemigo
        speed = 2.0f;
    }

    //Método que se llamará cuando el enemigo haya sido derrotado
    public void defeat() {
        isDefeated = true;
        timeToRespawn = 3.0f;
    }

}
