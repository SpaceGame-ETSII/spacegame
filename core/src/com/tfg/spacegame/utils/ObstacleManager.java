package com.tfg.spacegame.utils;

import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.arcadeMode.Obstacle;

public class ObstacleManager {

    public static Array<Obstacle> obstacles;

    public static void load(){
        obstacles = new Array<Obstacle>();
        //obstacles.add(new Obstacle("big_obstacle", 200, 1000));
        //obstacles.add(new Obstacle("medium_obstacle", 100, 1300));
        //obstacles.add(new Obstacle("small_obstacle", 300, 1600));

        obstacles.add(new Obstacle("medium_obstacle", 300, 600, true));
        obstacles.add(new Obstacle("medium_obstacle", 100, 400, false));
    }

    public static void update(float delta){
        for (Obstacle obstacle: obstacles) {
            obstacle.update(delta);

            //Se eliminan los obstáculos que se salgan de la pantalla
            if(obstacle.getX() < -obstacle.getHeight()){
                obstacles.removeValue(obstacle,false);
            }
        }
    }

    public static void render(){
        for (Obstacle obstacle: obstacles)
            obstacle.render(SpaceGame.batch);
    }

    public static boolean existsCollision(GameObject gameObject) {
        boolean res = false;

        for (Obstacle obstacle: obstacles) {
            if (gameObject.isOverlapingWith(obstacle)) {
                res = true;
                break;
            }
        }

        return res;
    }

}
