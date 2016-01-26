package com.tfg.spacegame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.tfg.spacegame.gameObjects.enemies.Type1;
import com.tfg.spacegame.gameObjects.enemies.Type2;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.enemies.Type3;

public class LevelGenerator {

    //Clase que se representa un enemigo recogido del script
    private static class EnemyWrapper {
        public String type;
        public int x;
        public int y;
        public float timeToSpawn;
    }

    //Cadena de enemigos que se generan desde el script
    public Array<EnemyWrapper> enemiesToGenerate;

    //Convierte los enemigos del json en objetos
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
        for(EnemyWrapper wrapper: enemiesToGenerate){

            // Resto el tiempo necesario
            wrapper.timeToSpawn -= delta;

            // Si el tiempo se ha cumplido, generamos el enemigo correspondiente
            if(wrapper.timeToSpawn < 0){

                //Añadimos el nuevo enemigo y lo eliminamos de la lista a generar
                this.addEnemy(enemies, wrapper);
                enemiesToGenerate.removeValue(wrapper,false);
            }
        }

        return enemies;
    }

    private void addEnemy(Array<Enemy> enemies, EnemyWrapper wrapper) {
        if (wrapper.type.equals("TYPE1")) {
            enemies.addAll(this.createSquadron(wrapper.x, wrapper.y));
        } else if (wrapper.type.equals("TYPE2")) {
            enemies.add(new Type2(wrapper.x, wrapper.y));
        } else if (wrapper.type.equals("TYPE3")) {
            enemies.add(new Type3(wrapper.x, wrapper.y));
        } else {
            throw new IllegalArgumentException("Se ha tratado de genera un enemigo de tipo inexistente");
        }
    }

    /**
     * Crea un escuadrón de enemigos tipo 1 (5). Los parámetros de entrada son el lugar exacto
     * de aparición del primer enemigo. Todos irán retrasados respecto a él.
     * @return Un Array de Type1 con 5 elementos
     */
    private static Array<Type1> createSquadron(int x, int y){
        Array<Type1> result = new Array<Type1>();
        result.add(new Type1(x,y,0.0f));
        result.add(new Type1(x,y,0.35f));
        result.add(new Type1(x,y,0.65f));
        result.add(new Type1(x,y,0.95f));
        result.add(new Type1(x,y,1.25f));

        return result;
    }
}
