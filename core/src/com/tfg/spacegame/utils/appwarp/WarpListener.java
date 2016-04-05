package com.tfg.spacegame.utils.appwarp;


public interface WarpListener {

    void onError(String message);

    void onDidntFoundRoom(String message);

    void onGameStarted();

    void onConnectedWithServer(String message);

    void onDisconnectedWithServer();

    void onGameUpdateReceived(String message);

    void wonTheGame();

    void onWaitOtherPlayer();
}
