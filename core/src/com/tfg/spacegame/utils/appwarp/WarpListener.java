package com.tfg.spacegame.utils.appwarp;


public interface WarpListener {

    void onError(String message);

    void onDidntFoundRoom(String message);

    void onGameStarted(String message);

    void onConnectedWithServer(String message);

    void onDisconnectedWithServer();

    void onJoinedToRoom(String message);

    void onGetLiveRoomInfoDone(String message);

    void onUserJoinedRoom(String message);

    void onGameUpdateReceived(String message);
}
