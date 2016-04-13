package com.tfg.spacegame.android.multiplayerListeners;


import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.tfg.spacegame.android.AndroidLauncher;
import com.tfg.spacegame.utils.MultiplayerMessage;

public class MessageReceived implements RealTimeMessageReceivedListener{

    private AndroidLauncher callback;
    private MultiplayerMessage income;

    public MessageReceived(AndroidLauncher androidLauncher){
        callback = androidLauncher;
        income = new MultiplayerMessage();
    }

    @Override
    public void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) {
        if(realTimeMessage.isReliable())
            callback.tcpMessage = new String(realTimeMessage.getMessageData());
        else{
            income.setPropertiesFromMessage(new String(realTimeMessage.getMessageData()));
            income.swapMessageAuthors();
            callback.gameMessage = income;
        }

    }
}
