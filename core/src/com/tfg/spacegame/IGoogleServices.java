package com.tfg.spacegame;

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
}