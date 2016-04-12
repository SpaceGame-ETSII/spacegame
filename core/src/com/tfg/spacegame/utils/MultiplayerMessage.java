package com.tfg.spacegame.utils;


import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.utils.enums.TypePowerUp;

public class MultiplayerMessage {

    private final int MASK_NO_OPT;
    private final int MASK_LEAVE;
    private final int MASK_SHOOT;
    private final int MASK_REG_LIFE;
    private final int MASK_BURST;

    public static enum TypeMessage{
        POSITION,LEAVE,SHOOT,BURSTPOWERUP,REGLIFEPOWERUP;
    }

    private int operations;

    private float positionY;

    public MultiplayerMessage(){
        // 0000
        MASK_NO_OPT = 0;
        // 0001
        MASK_LEAVE = 1;
        // 0010
        MASK_SHOOT = 2;
        // 0100
        MASK_REG_LIFE = 4;
        // 1000
        MASK_BURST = 8;

        operations = MASK_NO_OPT;

        positionY           = SpaceGame.height/2;
    }

    public void resetOperations(){
        // Operation = 0000
        operations = MASK_NO_OPT;
    }

    public void setLeaveOperation(){
        // Operation = XXX0 | 0001 -> XXX1
        operations = operations | MASK_LEAVE;
    }

    public void setShootOperation(){
        // Operation = XX0X | 0010 -> XX1X
        operations = operations | MASK_SHOOT;
    }

    public void setRefLifeOperation(){
        // Operation = X0XX | 0100 -> X1XX
        operations = operations | MASK_REG_LIFE;
    }

    public void setBurstOperation(){
        // Operation = 0XXX | 1000 -> 1XXX
        operations = operations | MASK_BURST;
    }

    public boolean isLeaveOperationActive(){
        // (XXXX & 0001) -> 0001 En caso de que X en el primer bit sea uno
        return (operations & MASK_LEAVE) == MASK_LEAVE;
    }

    public boolean isShootOperationActive(){
        return (operations & MASK_SHOOT) == MASK_SHOOT;
    }

    public boolean isRefLifeOperationActive(){
        return (operations & MASK_REG_LIFE) == MASK_REG_LIFE;
    }

    public boolean isBurstOperationActive(){
        return (operations & MASK_BURST) == MASK_BURST;
    }

    public void setPositionY(float positionY){
        this.positionY = positionY;
    }

    public void setPropertiesFromMessage(String s){
        if(!s.isEmpty() || !s.equals("")){
            String[] result = s.split(":");
            positionY = Float.parseFloat(result[0]);
            operations = Integer.parseInt(result[1]);
        }
    }

    public float getPositionY() {
        return positionY;
    }

    public String getForSendMessage(){
        return positionY+":"+operations;
    }

}
