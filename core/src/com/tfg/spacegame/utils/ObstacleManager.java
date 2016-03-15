package com.tfg.spacegame.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
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

    public static float bottomCounter;
    public static float topCounter;
    public static float bottomFrequency;
    public static float topFrequency;
    public static float bottomProbability;
    public static float topProbability;

    public static final float MIN_BOTTOM_PROBABILITY = 1f;
    public static final float MIN_TOP_PROBABILITY = 0.5f;

    public static void load(){
        obstaclesInTop = new Array<Obstacle>();
        obstaclesInBottom = new Array<Obstacle>();

        bottomCounter = 0;
        topCounter = 0;

        bottomFrequency = 100;
        topFrequency = 100;

        bottomProbability = MIN_BOTTOM_PROBABILITY;
        topProbability = MIN_TOP_PROBABILITY;
    }

    public static void update(float delta){
        createNewObstacles(delta);
        updateObstacles(delta);
    }

    private static void createNewObstacles(float delta) {
        Obstacle obstacle;

        bottomCounter += delta;
        topCounter += delta;

        if (MathUtils.random(bottomCounter, bottomFrequency) > bottomFrequency - bottomProbability) {
            obstacle = new Obstacle("small_obstacle", 0, 950, BOTTOM_SCALE);
            obstacle.setX(MathUtils.random(0, SpaceGame.width - obstacle.getWidth()));

            if (!existsCollision(obstacle, -1)) {
                obstaclesInBottom.add(obstacle);
                bottomCounter = 0;
            }
        }

        if (MathUtils.random(topCounter, topFrequency) > topFrequency - topProbability) {
            obstacle = new Obstacle("small_obstacle", 0, 950, TOP_SCALE);
            obstacle.setX(MathUtils.random(0, SpaceGame.width - obstacle.getWidth()));

            if (!existsCollision(obstacle, 1)) {
                obstaclesInTop.add(obstacle);
                topCounter = 0;
            }
        }

        if (ArcadeScreen.layer == 1) {
            topProbability += (delta * 0.05);
            if (bottomProbability > MIN_BOTTOM_PROBABILITY)
                bottomProbability -= (delta * 0.025);
        } else {
            bottomProbability += (delta * 0.1);
            if (topProbability > MIN_TOP_PROBABILITY)
                topProbability -= (delta * 0.013);
        }
    }

    //Actualizamos la posición de los obstáculos que están creados
    private static void updateObstacles(float delta) {
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

    //Pintamos los obstáculos de arriba forma normal o transparente según en la capa que estemos
    public static void renderTop(float alpha){
        for (Obstacle obstacle: obstaclesInTop)
            obstacle.renderTransparent(alpha);
        FontManager.draw("Top: " + (topProbability) + "%", 50, 250);
        FontManager.draw("Bottom: " + (bottomProbability) + "%", 50, 200);
    }

    //Pintamos los obstáculos de abajo forma normal o transparente según en la capa que estemos
    public static void renderBottom(float alpha){
        for (Obstacle obstacle: obstaclesInBottom)
            obstacle.renderTransparent(alpha);
    }

    //Comprueba si hay colisión entre el gameObject y algún obstáculo de la capa que esté activa en ArcadeScreen
    public static boolean existsCollision(GameObject gameObject, float layer) {
        boolean res = false;
        Array<Obstacle> obstacles;

        //Según la capa donde estemos, se comprobará una lista de obstáculos u otra
        if (layer == 1)
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
