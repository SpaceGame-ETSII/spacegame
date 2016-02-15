package com.tfg.spacegame.utils;

import com.badlogic.gdx.utils.ArrayMap;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Button;

public class DialogBox {

    // Los elementos del cuadro de dialogo
    private ArrayMap<String, GameObject> dialogElements;

    // Indica el estado del cuadro de dialogo, si se tiene que ver o no
    private boolean dialogIn =false;

    public DialogBox(){
        dialogElements = new ArrayMap<String, GameObject>();
    }

    /**
     * Añade un elemento de tipo GameObject (También vale Button) al mapa de elementos
     * @param key
     * @param gameObject
     */
    public void addElement(String key, GameObject gameObject){
        dialogElements.put(key,gameObject);
    }

    /**
     * Obtiene un elemento de tipo GameObject
     * @param key
     * @return
     */
    public GameObject getElement(String key){
        return dialogElements.get(key);
    }

    public Button getElementButton(String key){
        return (Button) dialogElements.get(key);
    }

    public void renderElement(String key){
        dialogElements.get(key).render(SpaceGame.batch);
    }

    public void setDialogIn(boolean b){
        dialogIn=b;
    }

    public boolean isDialogIn(){
        return dialogIn;
    }

    public void dispose(){
        for(GameObject g: dialogElements.values){
            g.dispose();
        }
    }
}
