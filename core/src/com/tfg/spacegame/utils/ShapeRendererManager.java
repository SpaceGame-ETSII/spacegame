package com.tfg.spacegame.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.SpaceGame;

/**
 * Created by gaems-dev on 22/02/16.
 */
public class ShapeRendererManager {

    private static ShapeRenderer renderer;
    private static Vector3 valuesToProjectFromCamera;

    public static void initialize(){
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        valuesToProjectFromCamera = new Vector3();
    }


    public static void renderCircle(float x, float y, float radius, Color color){

        valuesToProjectFromCamera.set(x,y,0);
        SpaceGame.camera.project(valuesToProjectFromCamera);

        SpaceGame.batch.end();

        renderer.begin(ShapeRenderer.ShapeType.Line);

        renderer.setColor(color);

        renderer.circle(valuesToProjectFromCamera.x,valuesToProjectFromCamera.y,radius);

        renderer.end();

        SpaceGame.batch.begin();
    }

    public static void renderRectangle(float x, float y, float width, float height, Color color){
        valuesToProjectFromCamera.set(x,y,0);
        SpaceGame.camera.project(valuesToProjectFromCamera);

        SpaceGame.batch.end();

        renderer.begin(ShapeRenderer.ShapeType.Line);

        renderer.setColor(color);

        renderer.rect(x,y,width,height);

        renderer.end();

        SpaceGame.batch.begin();
    }

    public static void renderFillCircle(float x, float y, float radius, Color color){

        valuesToProjectFromCamera.set(x,y,0);
        SpaceGame.camera.project(valuesToProjectFromCamera);

        SpaceGame.batch.end();

        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(color);

        renderer.circle(valuesToProjectFromCamera.x,valuesToProjectFromCamera.y,radius);

        renderer.end();

        SpaceGame.batch.begin();
    }
}
