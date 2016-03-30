package com.tfg.spacegame.android;

import android.os.Bundle;

import android.view.WindowManager;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.tfg.spacegame.SpaceGame;

public class AndroidLauncher extends AndroidApplication implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{


	private GoogleApiClient googleApiClient;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		googleApiClient = new GoogleApiClient.Builder(this)
								.addConnectionCallbacks(this)
								.addOnConnectionFailedListener(this)
								.addApi(Games.API)
								.addScope(Games.SCOPE_GAMES)
								.build();

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		PlatformAndroid platform = new PlatformAndroid();
		platform.setActivity(this);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		initialize(new SpaceGame(platform), config);
	}

	@Override
	public void onConnected(Bundle bundle) {

	}

	@Override
	public void onConnectionSuspended(int i) {

	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {

	}
}
