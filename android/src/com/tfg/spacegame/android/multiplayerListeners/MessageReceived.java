package com.tfg.spacegame.android.multiplayerListeners;


import com.badlogic.gdx.Gdx;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.tfg.spacegame.android.AndroidLauncher;

    public class MessageReceived implements RealTimeMessageReceivedListener{

    private AndroidLauncher callback;

    public MessageReceived(AndroidLauncher androidLauncher){
        callback = androidLauncher;
    }

    @Override
    public void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) {
        callback.enemyMessage = realTimeMessage;
    }
}
