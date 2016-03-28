package com.tfg.spacegame.utils.appwarp;


public interface WarpListener {

    void onWaitingStarted(String message);

    void onError(String message);

    void onDidntFoundRoom(String message);

    void onGameStarted(String message);

    void onConnectedWithServer(String message);

    void onJoinedToRoom(String message);

    void onGetLiveRoomInfoDone(String message);

    void onUserJoinedRoom(String message);

    void onGameFinished(int code, boolean isRemote);

    void onGameUpdateReceived(String message);

    void onUserLeaveRoom();
}
