package com.tfg.spacegame.utils;


import com.tfg.spacegame.SpaceGame;

public class MultiplayerMessage {

    public final int MASK_NO_OPT;
    public final int MASK_LEAVE;
    public final int MASK_SHOOT;
    public final int MASK_REG_LIFE;
    public final int MASK_BURST;
    public final int MASK_ACK;

    private int playerOperations;
    private float playerPositionY;

    private int rivalOperations;
    private float rivalPositionY;

    public MultiplayerMessage(){
        // 00000
        MASK_NO_OPT     = 0;
        // 00001
        MASK_LEAVE      = 1;
        // 00010
        MASK_SHOOT      = 2;
        // 00100
        MASK_REG_LIFE   = 4;
        // 01000
        MASK_BURST      = 8;
        // 10000
        MASK_ACK        = 16;

        playerOperations = MASK_NO_OPT;
        rivalOperations  = MASK_NO_OPT;

        playerPositionY = SpaceGame.height/2;
        rivalPositionY  = SpaceGame.height/2;
    }

    public int getRivalOperations(){
        return rivalOperations;
    }
    public void setRivalOperations(int i){
        rivalOperations = i;
    }

    public void resetPlayerOperations(){
        // Operation = 00000
        playerOperations = MASK_NO_OPT;
    }
    public void resetRivalOperations(){
        // Operation = 00000
        playerOperations = MASK_NO_OPT;
    }

    public void setPlayerOperation(int i){
        playerOperations = playerOperations | i;
    }

    public void setRivalOperation(int i){
        rivalOperations = rivalOperations | i;
    }

    public boolean checkPlayerOperation(int i){
        return (playerOperations & i) == i;
    }

    public boolean checkRivalOperation(int i){
        return (rivalOperations & i) == i;
    }

    public float getPlayerPositionY() {
        return playerPositionY;
    }

    public void setPlayerPositionY(float playerPositionY) {
        this.playerPositionY = playerPositionY;
    }

    public float getRivalPositionY() {
        return rivalPositionY;
    }

    public void setRivalPositionY(float rivalPositionY) {
        this.rivalPositionY = rivalPositionY;
    }

    public void setPropertiesFromMessage(String s){
        if(!s.isEmpty() || !s.equals("")){
            String[] result = s.split(":");
            playerPositionY = Float.parseFloat(result[0]);
            playerOperations = Integer.parseInt(result[1]);

            rivalPositionY = Float.parseFloat(result[2]);
            rivalOperations = Integer.parseInt(result[3]);
        }
    }

    public void swapMessageAuthors(){
        int auxOperations = playerOperations;
        float auxPosition = playerPositionY;

        playerOperations  = rivalOperations;
        playerPositionY   = rivalPositionY;

        rivalOperations   = auxOperations;
        rivalPositionY    = auxPosition;
    }

    public String getForSendMessage(){
        return playerPositionY+":"+playerOperations+":"+rivalPositionY+":"+rivalOperations;
    }

}
