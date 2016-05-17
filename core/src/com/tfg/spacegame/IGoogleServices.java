package com.tfg.spacegame;

import com.tfg.spacegame.utils.MultiplayerMessage;
import com.tfg.spacegame.utils.enums.MultiplayerState;

//Se usará para conectarnos a Google Services
public interface IGoogleServices
{
    void submitScore(long score);
    boolean isSignedIn();
    void unlockAchievement(String achievementId);
    void showAchievements();

    void startQuickGame();
    void invitePlayer();
    void seeMyInvitations();
    void leaveRoom();

    MultiplayerState getMultiplayerState();

    void sendGameMessage(String message);
    MultiplayerMessage receiveGameMessage();


}