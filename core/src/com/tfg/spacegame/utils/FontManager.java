package com.tfg.spacegame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.tfg.spacegame.SpaceGame;

public class FontManager {

    //Tipos de fuentes que se usarán
    public static BitmapFont text;
    public static BitmapFont title;

    // Objeto encargado de la internacionalización del juego
    public static I18NBundle bundle;

    public static void initialize(int width) {
        FreeTypeFontGenerator generator;
        FreeTypeFontGenerator.FreeTypeFontParameter parameter;

        generator = new FreeTypeFontGenerator(Gdx.files.internal("pirulen.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = width / 44;
        text = generator.generateFont(parameter);
        parameter.size = width / 20;
        title = generator.generateFont(parameter);

        generator.dispose();

        // Cargamos el bundle desde su correspondiente método del asset manager
        bundle = AssetsManager.loadBundle();
    }

    public static void drawText(String string, float x, float y){
        text.draw(SpaceGame.batch,bundle.get(string),x,y);
    }

    public static void drawTitle(String string, float x, float y){
        title.draw(SpaceGame.batch,bundle.get(string),x,y);
    }

    public static String getFromBundle(String string) {
        return bundle.get(string);
    }

    public void dispose() {
        text.dispose();
        title.dispose();
    }

}