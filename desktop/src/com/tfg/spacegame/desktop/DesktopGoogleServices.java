package com.tfg.spacegame.desktop;

import com.tfg.spacegame.IGoogleServices;
import com.tfg.spacegame.utils.MultiplayerMessage;
import com.tfg.spacegame.utils.enums.MultiplayerState;

//Será una clase cuyo único fin es permitirnos ejecutar la aplicación en escritorio sin que dé fallo
public class DesktopGoogleServices implements IGoogleServices
{
    @Override
    public void signIn()
    {
        System.out.println("DesktopGoogleServices: signIn()");
    }

    @Override
    public void signOut()
    {
        System.out.println("DesktopGoogleServices: signOut()");
    }

    @Override
    public void rateGame()
    {
        System.out.println("DesktopGoogleServices: rateGame()");
    }

    @Override
    public void submitScore(long score)
    {
        System.out.println("DesktopGoogleServices: submitScore(" + score + ")");
    }

    @Override
    public void showScores()
    {
        System.out.println("DesktopGoogleServices: showScores()");
    }

    @Override
    public boolean isSignedIn()
    {
        System.out.println("DesktopGoogleServices: isSignedIn()");
        return false;
    }

    @Override
    public void unlockAchievement(String achievementId) {
        System.out.println("DesktopGoogleServices: unlockAchievement()");
    }

    @Override
    public void showAchievements() {
        System.out.println("DesktopGoogleServices: showAchievements()");
    }

    @Override
    public void startQuickGame() {

    }

    @Override
    public void invitePlayer() {

    }

    @Override
    public void seeMyInvitations() {

    }

    @Override
    public void leaveRoom() {

    }

    @Override
    public MultiplayerState getMultiplayerState() {
        return null;
    }

    @Override
    public void sendGameMessage(String message) {

    }

    @Override
    public MultiplayerMessage receiveGameMessage() {
        return null;
    }
}
