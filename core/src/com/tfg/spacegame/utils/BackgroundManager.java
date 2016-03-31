package com.tfg.spacegame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.SpaceGame;

public class BackgroundManager {

    //Almacena los fondos que se mostrarán
    private static Array<Texture> backgrounds;

    //Posición concreta que tendrán los fondos
    private static Array<Float> scrollingPositions;

    //Velocidad de scroll de los fondos
    private static Array<Float> scrollingSpeeds;

    private static boolean isLoaded;

    public static void load() {
        backgrounds = new Array<Texture>();
        scrollingPositions = new Array<Float>();
        scrollingSpeeds = new Array<Float>();

        backgrounds.add(AssetsManager.loadTexture("background"));
        backgrounds.add(AssetsManager.loadTexture("background2"));
        backgrounds.add(AssetsManager.loadTexture("background3"));

        scrollingPositions.add(0f);
        scrollingPositions.add(0f);
        scrollingPositions.add(0f);

        scrollingSpeeds.add(100f);
        scrollingSpeeds.add(150f);
        scrollingSpeeds.add(250f);

        isLoaded = true;
    }

    public static void update(float delta, boolean stopped) {
        //Si no se han cargado los fondos, lo hacemos
        if (!isLoaded)
            BackgroundManager.load();

        //Si no avanzamos el fondo, restamos sólo el valor del primero
        int decrease = (stopped) ? 3 : 1;

        //Recalculamos las posiciones de los fondos
        for (int i=0; i<backgrounds.size; i++) {
            scrollingPositions.set(i, scrollingPositions.get(i) - (delta * (scrollingSpeeds.get(i) / decrease)));
            if (scrollingPositions.get(i) <= -backgrounds.get(i).getWidth())
                scrollingPositions.set(i, 0f);
        }
    }

    public static void render() {
        //Si no se han cargado los fondos, lo hacemos
        if (!isLoaded)
            BackgroundManager.load();

        //Pintamos los fondos
        for (int i=0; i<backgrounds.size; i++)
            render(i);
    }

    //Pinta el fondo concreto que le indiquemos según su posición en el Array
    public static void render(int pos) {
        Texture b = backgrounds.get(pos);

        //Pintamos el fondo, que necesitará pintarse dos veces para el scrolling
        //Según si es landscape o portrait, se pintará de una forma u otra
        if (SpaceGame.orientation.equals("sensorLandscape")) {
            SpaceGame.batch.draw(b, scrollingPositions.get(pos), 0);
            SpaceGame.batch.draw(b, scrollingPositions.get(pos) + b.getWidth(), 0);
        } else {
            SpaceGame.batch.draw(new TextureRegion(b), 0, scrollingPositions.get(pos),
                    SpaceGame.width / 2, b.getHeight() / 2,
                    b.getWidth(), b.getHeight(), 1, 1, 90);
            SpaceGame.batch.draw(new TextureRegion(b), 0, b.getWidth() + scrollingPositions.get(pos),
                    SpaceGame.width / 2, b.getHeight() / 2,
                    b.getWidth(), b.getHeight(), 1, 1, 90);
        }

    }

    public static void dispose() {
        for (int i=0; i<backgrounds.size; i++)
            backgrounds.get(i).dispose();
    }

}
