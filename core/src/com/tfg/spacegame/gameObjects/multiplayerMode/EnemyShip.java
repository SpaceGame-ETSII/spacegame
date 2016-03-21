package com.tfg.spacegame.gameObjects.multiplayerMode;


import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.screens.MultiplayerScreen;
import com.tfg.spacegame.utils.AssetsManager;

public class EnemyShip extends Enemy{

    //Indica la velocidad para el movimiento de la nave
    private  final float SPEED = 50;

    public EnemyShip() {
        super("enemyShip", SpaceGame.width - (80+100), SpaceGame.height/2, 5, AssetsManager.loadParticleEffect("ship_defeated"));
    }

    public void update(float delta){

        float accel = SPEED*delta;

        if(Math.abs(MultiplayerScreen.enemyYposition - this.getCenter().y) > accel ){
            float diffY = MultiplayerScreen.enemyYposition - this.getCenter().y;
            this.setY(this.getY() + diffY);
        }
    }
}
