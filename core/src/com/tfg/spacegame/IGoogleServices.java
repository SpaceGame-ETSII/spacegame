package com.tfg.spacegame;

import com.tfg.spacegame.utils.MultiplayerMessage;

//Se usará para conectarnos a Google Services
public interface IGoogleServices
{
    void signIn();
    void signOut();
    void rateGame();
    void submitScore(long score);
    void showScores();
    boolean isSignedIn();
    void unlockAchievement(String achievementId);
    void showAchievements();

    void startQuickGame();
    void leaveRoom();

    boolean canMultiplayerGameStart();

    void sendGameMessage(String message);
    void sendTCPMessage(String message);
    MultiplayerMessage receiveGameMessage();
    String receiveTCpMessage();
    boolean calculateMasterSlave();


}