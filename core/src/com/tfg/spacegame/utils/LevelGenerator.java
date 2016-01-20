package com.tfg.spacegame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.tfg.spacegame.gameObjects.Enemies.Type1;
import com.tfg.spacegame.gameObjects.Enemies.Type2;
import com.tfg.spacegame.gameObjects.Enemy;


/**
 * Created by gaems-dev on 20/01/16.
 */
public class LevelGenerator {

    private static class EnemyWrapper{
        public String name;
        public int x;
        public int y;
        public float time;
    }

    Array<EnemyWrapper> enemiesToGenerate;

    public static LevelGenerator loadLevel(String jsonFile){
        FileHandle file = Gdx.files.internal("levelscripts/"+jsonFile);
        Json json = new Json();

        return json.fromJson(LevelGenerator.class,file);
    }

    /**
     * Este método es el encargado de generar los enemigos
     * Para ello va a reducir en una cantidad delta el tiempo
     * requerido para que aparezca el enemigo.
     * @param enemies Los enemigos generados actualmente
     * @param delta
     * @return Los enemigos que estaban creados más los recien creados
     */
    public Array<Enemy> update(Array<Enemy> enemies,float delta){
        // Recorro los enemigos a poder generar
        for(EnemyWrapper wrapped: enemiesToGenerate){
            // Resto el tiempo necesario
            wrapped.time -= delta;
            // Si el tiempo se ha cumplido
            if(wrapped.time<0){
                if(wrapped.name.equals("type1")){
                    // Crea el enemigo
                    enemies.addAll(Type1.createSquadron(wrapped.x,wrapped.y));
                    // Eliminalo de la lista de enemigos a generar
                    enemiesToGenerate.removeValue(wrapped,false);
                }else if(wrapped.name.equals("type2")){
                    // Crea el enemigo
                    enemies.add(Type2.createEnemy(wrapped.x,wrapped.y));
                    // Eliminalo de la lista de enemigos a generar
                    enemiesToGenerate.removeValue(wrapped,false);
                }
            }
        }

        return enemies;
    }
}
