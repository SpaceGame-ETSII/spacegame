package com.tfg.spacegame.utils;

import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.arcadeMode.Obstacle;
import com.tfg.spacegame.screens.ArcadeScreen;

public class ObstacleManager {

    //Escala de los obstáculos en la capa superior
    public static final float TOP_SCALE = 1;

    //Escala de los obstáculos en la capa inferior
    public static final float BOTTOM_SCALE = 0.5f;

    //Obstáculos de la capa superior
    public static Array<Obstacle> obstaclesInTop;

    //Obstáculos de la capa inferior
    public static Array<Obstacle> obstaclesInBottom;

    public static void load(){
        obstaclesInTop = new Array<Obstacle>();
        obstaclesInTop.add(new Obstacle("big_obstacle", 200, 1000, TOP_SCALE));
        obstaclesInTop.add(new Obstacle("medium_obstacle", 100, 1300, TOP_SCALE));
        obstaclesInTop.add(new Obstacle("small_obstacle", 300, 1600, TOP_SCALE));

        obstaclesInBottom = new Array<Obstacle>();
        obstaclesInBottom.add(new Obstacle("big_obstacle", 300, 1000, BOTTOM_SCALE));
        obstaclesInBottom.add(new Obstacle("medium_obstacle", 300, 1300, BOTTOM_SCALE));
        obstaclesInBottom.add(new Obstacle("small_obstacle", 100, 1600, BOTTOM_SCALE));
    }

    public static void update(float delta){
        for (Obstacle obstacle: obstaclesInTop) {
            obstacle.update(delta);

            //Se eliminan los obstáculos que se salgan de la pantalla
            if(obstacle.getX() < -obstacle.getHeight()){
                obstaclesInTop.removeValue(obstacle,false);
            }
        }
        for (Obstacle obstacle: obstaclesInBottom) {
            obstacle.update(delta);

            //Se eliminan los obstáculos que se salgan de la pantalla
            if(obstacle.getX() < -obstacle.getHeight()){
                obstaclesInBottom.removeValue(obstacle,false);
            }
        }
    }

    //Pintamos los obstáculos de forma normal o transparente según en la capa que estemos
    public static void render(){

        //Pintamos primero la capa de abajo
        for (Obstacle obstacle: obstaclesInBottom)
            if (ArcadeScreen.layer == 1)
                obstacle.renderTransparent();
            else
                obstacle.render();

        //Pintamos segundo la capa de arriba
        for (Obstacle obstacle: obstaclesInTop)
            if (ArcadeScreen.layer == 1)
                obstacle.render();
            else
                obstacle.renderTransparent();

    }

    //Comprueba si hay colisión entre el gameObject y algún obstáculo de la capa que esté activa en ArcadeScreen
    public static boolean existsCollision(GameObject gameObject) {
        boolean res = false;
        Array<Obstacle> obstacles;

        //Según la capa donde estemos, se comprobará una lista de obstáculos u otra
        if (ArcadeScreen.layer == 1)
            obstacles = obstaclesInTop;
        else
            obstacles = obstaclesInBottom;

        //Finalmente comprobamos si hay colisión
        for (Obstacle obstacle: obstacles) {
            if (gameObject.isOverlapingWith(obstacle)) {
                res = true;
                break;
            }
        }

        return res;
    }

}
