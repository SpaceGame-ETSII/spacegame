package com.tfg.spacegame.utils.appwarp;


public interface WarpListener {

    public void onWaitingStarted(String message);

    public void onError(String message);

    public void onGameStarted(String message);

    public void onConnectedWithServer(String message);

    public void onJoinedToRoom(String message);

    public void onGetLiveRoomInfoDone(String message);

    public void onUserJoinedRoom(String message);

    public void onGameFinished(int code, boolean isRemote);

    public void onGameUpdateReceived(String message);
}
