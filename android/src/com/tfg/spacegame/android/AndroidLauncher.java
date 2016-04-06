package com.tfg.spacegame.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.WindowManager;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.tfg.spacegame.IGoogleServices;
import com.tfg.spacegame.SpaceGame;

public class AndroidLauncher extends AndroidApplication implements IGoogleServices {

	private final static int REQUEST_CODE_UNUSED = 9002;
	private GameHelper _gameHelper;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Create the GameHelper.
		_gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		_gameHelper.enableDebugLog(false);

		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		{
			@Override
			public void onSignInSucceeded()
			{
			}

			@Override
			public void onSignInFailed()
			{
			}
		};

		_gameHelper.setup(gameHelperListener);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		PlatformAndroid platform = new PlatformAndroid();
		platform.setActivity(this);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		initialize(new SpaceGame(platform, this), config);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		_gameHelper.onStart(this);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		_gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		_gameHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void signIn()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				//@Override
				public void run()
				{
					_gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				//@Override
				public void run()
				{
					_gameHelper.signOut();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame()
	{
		// Replace the end of the URL with the package of your game
		String str ="https://play.google.com/store/apps/details?id=org.fortheloss.plunderperil";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}

	@Override
	public void submitScore(long score)
	{
		if (isSignedIn() == true) {
			Games.Leaderboards.submitScore(_gameHelper.getApiClient(), getString(R.string.leaderboard_id), score);
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
		} else {
			// Maybe sign in here then redirect to submitting score?
		}
	}

	@Override
	public void showScores() {
		if (isSignedIn())
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
		else {
			// Maybe sign in here then redirect to showing scores?
		}
	}

	@Override
	public boolean isSignedIn()
	{
		return _gameHelper.isSignedIn();
	}

	@Override
	public void unlockAchievement(String achievementId) {
		if (isSignedIn()) {
			Games.Achievements.unlock(_gameHelper.getApiClient(), achievementId);
		} else {

		}
	}

	@Override
	public void showAchievements() {
		if (isSignedIn()) {
			startActivityForResult(Games.Achievements.getAchievementsIntent(_gameHelper.getApiClient()),
					REQUEST_CODE_UNUSED);
		} else {

		}
	}

}
