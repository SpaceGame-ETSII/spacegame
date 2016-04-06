package com.tfg.spacegame.desktop;

import com.tfg.spacegame.IGoogleServices;

//Será una clase cuyo único fin es permitirnos ejecutar la aplicación en escritorio sin que dé fallo
public class DesktopGoogleServices implements IGoogleServices
{
    @Override
    public void signIn()
    {
        System.out.println("DesktopGoogleServies: signIn()");
    }

    @Override
    public void signOut()
    {
        System.out.println("DesktopGoogleServies: signOut()");
    }

    @Override
    public void rateGame()
    {
        System.out.println("DesktopGoogleServices: rateGame()");
    }

    @Override
    public void submitScore(long score)
    {
        System.out.println("DesktopGoogleServies: submitScore(" + score + ")");
    }

    @Override
    public void showScores()
    {
        System.out.println("DesktopGoogleServies: showScores()");
    }

    @Override
    public boolean isSignedIn()
    {
        System.out.println("DesktopGoogleServies: isSignedIn()");
        return false;
    }

    @Override
    public void unlockAchievement() {
        System.out.println("DesktopGoogleServies: unlockAchievement()");
    }

    @Override
    public void showAchievements() {
        System.out.println("DesktopGoogleServies: showAchievemnts()");
    }
}
