package com.tfg.spacegame.utils.appwarp.listeners;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;
import com.tfg.spacegame.utils.appwarp.WarpController;


public class ConnectionListener implements ConnectionRequestListener {

    @Override
    public void onConnectDone(ConnectEvent connectEvent) {
        if(connectEvent.getResult() == WarpResponseResultCode.SUCCESS){
            WarpController.connectDone(true);
        }else{
            System.out.println("Error en onConnectDone : "+connectEvent.getResult());
            WarpController.connectDone(false);
        }
    }

    @Override
    public void onDisconnectDone(ConnectEvent connectEvent) {
        WarpController.disconnectDone();
    }

    @Override
    public void onInitUDPDone(byte b) {

    }
}
