package com.tfg.spacegame.utils.appwarp.listeners;

import com.shephertz.app42.gaming.multiplayer.client.events.*;
import com.shephertz.app42.gaming.multiplayer.client.listener.NotifyListener;
import com.tfg.spacegame.utils.appwarp.WarpController;

import java.util.HashMap;


public class NotificationListener implements NotifyListener {

    private WarpController callback;

    public NotificationListener(WarpController warpController){
        callback = warpController;
    }




    @Override
    public void onRoomCreated(RoomData roomData) {

    }

    @Override
    public void onRoomDestroyed(RoomData roomData) {

    }

    @Override
    public void onUserLeftRoom(RoomData roomData, String s) {

    }

    @Override
    public void onUserJoinedRoom(RoomData roomData, String s) {
        System.out.println("asdsa");
        callback.onUserJoinedRoom(roomData.getId(),s);
    }

    @Override
    public void onUserLeftLobby(LobbyData lobbyData, String s) {

    }

    @Override
    public void onUserJoinedLobby(LobbyData lobbyData, String s) {

    }

    @Override
    public void onChatReceived(ChatEvent chatEvent) {

    }

    @Override
    public void onPrivateChatReceived(String s, String s1) {

    }

    @Override
    public void onPrivateUpdateReceived(String s, byte[] bytes, boolean b) {

    }

    @Override
    public void onUpdatePeersReceived(UpdateEvent updateEvent) {
        callback.onGameUpdateReceived(new String(updateEvent.getUpdate()));
    }

    @Override
    public void onUserChangeRoomProperty(RoomData roomData, String s, HashMap<String, Object> hashMap, HashMap<String, String> hashMap1) {

    }

    @Override
    public void onMoveCompleted(MoveEvent moveEvent) {

    }

    @Override
    public void onGameStarted(String s, String s1, String s2) {

    }

    @Override
    public void onGameStopped(String s, String s1) {

    }

    @Override
    public void onUserPaused(String s, boolean b, String s1) {

    }

    @Override
    public void onUserResumed(String s, boolean b, String s1) {

    }

    @Override
    public void onNextTurnRequest(String s) {

    }
}
