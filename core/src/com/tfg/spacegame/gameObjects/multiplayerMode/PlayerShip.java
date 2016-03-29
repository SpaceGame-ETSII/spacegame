package com.tfg.spacegame.gameObjects.multiplayerMode;


import com.tfg.spacegame.gameObjects.campaignMode.CampaignShip;
import com.tfg.spacegame.utils.AssetsManager;

public class PlayerShip extends CampaignShip {



    public PlayerShip() {
        super("playerShip");
    }

    public void update(float delta, float y, boolean canShipMove){
        super.update(delta, y, canShipMove);
    }

    public void healHalfLife(){
        this.damageReceived-=damageReceived/2;
        if(damageReceived<0)
            damageReceived=0;
        cockpit = AssetsManager.loadTexture("cockpit_damage" + damageReceived);
    }

    public boolean isDefeated(){
        return this.damageReceived >= VITALITY;
    }


    public void render(){
        super.render();
    }

}
