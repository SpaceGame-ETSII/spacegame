package com.tfg.spacegame.utils.appwarp.listeners;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;
import com.tfg.spacegame.utils.appwarp.WarpController;


public class ConnectionListener implements ConnectionRequestListener {

    private WarpController callBack;

    public ConnectionListener(WarpController warpController){
        callBack = warpController;
    }

    @Override
    public void onConnectDone(ConnectEvent connectEvent) {
        System.out.println("onConnectDone: "+connectEvent.getResult());
        if(connectEvent.getResult() == WarpResponseResultCode.SUCCESS){
            callBack.onConnectDone(true);
        }else{
            callBack.onConnectDone(false);
        }
    }

    @Override
    public void onDisconnectDone(ConnectEvent connectEvent) {
        System.out.println("Desconectado del servidor");
    }

    @Override
    public void onInitUDPDone(byte b) {

    }
}
