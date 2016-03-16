package com.tfg.spacegame.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.arcadeMode.Obstacle;
import com.tfg.spacegame.screens.ArcadeScreen;
import com.tfg.spacegame.utils.enums.TypeObstacle;

public class ObstacleManager {

    //Escala de los obstáculos en la capa superior
    public static final float TOP_SCALE = 1;

    //Escala de los obstáculos en la capa inferior
    public static final float BOTTOM_SCALE = 0.5f;

    //Obstáculos de la capa superior
    public static Array<Obstacle> obstaclesInTop;

    //Obstáculos de la capa inferior
    public static Array<Obstacle> obstaclesInBottom;

    public static Array<Obstacle> deletedSmallObstacles;
    public static Array<Obstacle> deletedMediumObstacles;
    public static Array<Obstacle> deletedBigObstacles;

    public static float bottomProbability;
    public static float topProbability;

    public static final float MIN_BOTTOM_PROBABILITY = 1f;
    public static final float MIN_TOP_PROBABILITY = 0.5f;

    public static float time;

    public static void load(){
        obstaclesInTop = new Array<Obstacle>();
        obstaclesInBottom = new Array<Obstacle>();
        deletedSmallObstacles = new Array<Obstacle>();
        deletedMediumObstacles = new Array<Obstacle>();
        deletedBigObstacles = new Array<Obstacle>();

        bottomProbability = MIN_BOTTOM_PROBABILITY;
        topProbability = MIN_TOP_PROBABILITY;

        time = 0;
    }

    public static void update(float delta){
        time += delta;
        generateObstacles(delta);
        updateProbabilities(delta);
        updateObstacles(delta, obstaclesInTop);
        updateObstacles(delta, obstaclesInBottom);
    }

    private static void generateObstacles(float delta) {
        createObstacle(obstaclesInBottom, BOTTOM_SCALE, bottomProbability);
        createObstacle(obstaclesInTop, TOP_SCALE, topProbability);
    }

    private static void createObstacle(Array<Obstacle> obstacles, float scale, float probability) {
        if (MathUtils.random(0, 100) <= probability) {
            Obstacle obstacle;

            float probabilityType = MathUtils.random(0, 100);

            if (probabilityType >= 98) {
                if (deletedBigObstacles.size != 0) {
                    obstacle = deletedBigObstacles.get(0);
                    deletedBigObstacles.removeIndex(0);
                } else {
                    obstacle = new Obstacle("big_obstacle", 0, 0, TypeObstacle.BIG);
                }
            } else if (probabilityType >= 90) {
                if (deletedMediumObstacles.size != 0) {
                    obstacle = deletedMediumObstacles.get(0);
                    deletedMediumObstacles.removeIndex(0);
                } else {
                    obstacle = new Obstacle("medium_obstacle", 0, 0, TypeObstacle.MEDIUM);
                }
            } else {
                if (deletedSmallObstacles.size != 0) {
                    obstacle = deletedSmallObstacles.get(0);
                    deletedSmallObstacles.removeIndex(0);
                } else {
                    obstacle = new Obstacle("small_obstacle", 0, 0, TypeObstacle.SMALL);
                }
            }

            obstacle.setX(MathUtils.random(0, SpaceGame.width - obstacle.getWidth()));
            obstacle.setY(950);
            obstacle.setScale(scale, scale);

            if (!existsCollision(obstacle, ArcadeScreen.layer)) {
                obstacles.add(obstacle);
            }
        }
    }

    private static void updateProbabilities(float delta) {
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
    private static void updateObstacles(float delta, Array<Obstacle> obstacles) {
        for (Obstacle obstacle: obstacles) {
            //Actualizamos el obstáculo concreto
            obstacle.update(delta);

            //Aumentamos la velocidad del obstáculo
            obstacle.changeSpeed(delta);

            //Eliminamos el obstáculo si se ha salido de la pantalla
            if(obstacle.getY() < -obstacle.getHeight()){
                removeObstacle(obstacle, obstacles);
            }
        }
    }

    //Borra un obstáculo de la lista pasada por parámetro y lo incluye en su correspondiente lista de borrados
    private static void removeObstacle(Obstacle obstacle, Array<Obstacle> obstacles) {
        obstacles.removeValue(obstacle,false);
        if (obstacle.getType().equals(TypeObstacle.BIG)) {
            deletedBigObstacles.add(obstacle);
        } else if (obstacle.getType().equals(TypeObstacle.MEDIUM)) {
            deletedMediumObstacles.add(obstacle);
        } else if  (obstacle.getType().equals(TypeObstacle.SMALL)) {
            deletedSmallObstacles.add(obstacle);
        }
    }

    //Pintamos los obstáculos de arriba forma normal o transparente según en la capa que estemos
    public static void renderTop(float alpha){
        for (Obstacle obstacle: obstaclesInTop)
            obstacle.renderTransparent(alpha);
        //FontManager.draw("Obstacles ingame: " + (obstaclesInBottom.size + obstaclesInTop.size) , 50, 400);
        //FontManager.draw("Obstacles deleted: " + (deletedSmallObstacles.size), 50, 350);
        //FontManager.draw("Obstacles created: " + (numObstacles), 50, 300);
        //FontManager.draw("Top: " + (topProbability) + "%", 50, 250);
        //FontManager.draw("Bottom: " + (bottomProbability) + "%", 50, 200);
        FontManager.draw("Time: " + ((int) time), 300, 700);
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
