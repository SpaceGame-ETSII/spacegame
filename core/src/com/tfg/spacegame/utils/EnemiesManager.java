package com.tfg.spacegame.utils;


import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;

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

            /*
            // El jugador habrá recibido daño por un enemigo
            // si chocan
            if (ship.isOverlapingWith(enemy) && !ship.isUndamagable())
                ship.receiveDamage();

            for(Iterator<Weapon> s = shoots.iterator(); s.hasNext();){
                Weapon shoot = s.next();

                //Se realizará cuando el disparo dé en el enemigo
                if (!enemy.isDefeated() && shoot.isOverlapingWith(enemy)) {
                    shoots.removeValue(shoot,false);
                    enemy.defeat();
                }

            }*/
            // Destruir los enemigos que se salgan de la parte izquierda de la pantalla
            if(enemy.getX() < 0){
                enemies.removeValue(enemy,false);
            }

            if(enemy.isDefeated())
                enemies.removeValue(enemy,false);
        }
    }

    public static void render() {
        for(Enemy enemy: enemies)
            if(!enemy.isDefeated())
                enemy.render(SpaceGame.batch);
    }
}
