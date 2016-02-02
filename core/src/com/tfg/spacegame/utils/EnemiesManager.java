package com.tfg.spacegame.utils;


import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;

public class EnemiesManager {

    public static Array<Enemy> enemies;
    private static LevelGenerator level;

    public static void load(){
        enemies = new Array<Enemy>();

        level = LevelGenerator.loadLevel("scriptTest");
    }

    public static void update(float delta){
        //Actualizamos los tiempos de espera de aparición de los enemigos
        enemies = level.update(enemies,delta);

        for(Enemy enemy: enemies){

            enemy.update(delta);

            //Se eliminan los enemigos que se salgan de la parte izquierda de la pantalla
            if(enemy.getX() < 0){
                enemies.removeValue(enemy,false);
            }

            if(enemy.isDefeated()) {
                enemies.removeValue(enemy, false);
            }
        }
    }

    public static void render() {
        for(Enemy enemy: enemies)
            if(!enemy.isDefeated())
                enemy.render(SpaceGame.batch);
    }

    //Gestiona la reacción de la colisión del enemigo pasado por parámetro con la nave
    public static void manageCollisionWithShip(Enemy enemy) {
        enemy.collideWithShip();
    }

    //Gestiona la reacción de la colisión del enemigo con el arma
    public static void manageCollisionWithShoot(Pair<Shoot, Enemy> shootToEnemy) {
        Shoot shoot = shootToEnemy.getFirst();
        Enemy enemy = shootToEnemy.getSecond();

        enemy.collideWithShoot(shoot);
    }
}
