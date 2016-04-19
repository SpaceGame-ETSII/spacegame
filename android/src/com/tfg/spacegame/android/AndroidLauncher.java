package com.tfg.spacegame.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.WindowManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMultiplayer;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.example.games.basegameutils.GameHelper;
import com.tfg.spacegame.IGoogleServices;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.android.multiplayerListeners.MessageReceived;
import com.tfg.spacegame.android.multiplayerListeners.RoomStatusUpdate;
import com.tfg.spacegame.android.multiplayerListeners.RoomUpdate;
import com.tfg.spacegame.utils.MultiplayerMessage;

import java.util.ArrayList;

public class AndroidLauncher extends AndroidApplication implements IGoogleServices {

	private final static int REQUEST_CODE_UNUSED = 9002;
	// Código que usa google internamente para saber que es una ventana de espera
	private final static int REQUEST_CODE_WAITING_ROOM = 10002;

	// Variable que guarderemos el ID de la habitación para futuras consultas
	public String roomId;

	// Variable que guardaremos nuestro ID dentro de la habitación para futuras consultas
	public String myId;

	// Los participantes que existen en la habitación actual
	public ArrayList<Participant> participants;

	// Mensaje recibido desde el Listener MessageReceived que se pasará a la Screen
	// Este mensaje va por UDP y corresponde a la inforamción del juego
	public MultiplayerMessage gameMessage;

	// Comprobaremos si puede iniciarse el multijugador o no
	// Basicamente esto se hace comprobando si ambos jugadores están en la habitación creada
	public boolean startMultiplayerGame;

	private GameHelper _gameHelper;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Create the GameHelper.
		_gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		_gameHelper.enableDebugLog(false);

		resetMultiplayerProperties();

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
		Gdx.app.log("multi","RequestCode: "+requestCode+"  - ResultCode: "+resultCode);
		switch (requestCode){
			case REQUEST_CODE_WAITING_ROOM:{
				// Obtenemos el resultado que coincide con el de la petición
				if (resultCode == Activity.RESULT_OK) {
					// Empezamos el juego multijugador
					Gdx.app.log("multi","Requested players connected, starting game");
					startMultiplayerGame = true;
				} else if (resultCode == GamesActivityResultCodes.RESULT_LEFT_ROOM) {
					// Un jugador se va
					Gdx.app.log("multi","Oponent left the room");
					leaveRoom();
				} else if (resultCode == Activity.RESULT_CANCELED) {
					// El jugador cancela la partida, esto para nosotros se transforma en una
					// solicitud de abandono de habitación
					Gdx.app.log("multi","Search canceled");
					leaveRoom();
				}
				break;
			}

		}
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

	@Override
	public void startQuickGame() {
		// Reseteamos la variable de control
		startMultiplayerGame = false;
		// Creamos la configuración de nuestra habitación ( minimo, máximo, ? )
		// TODO Quizas sea bonito hacerlas variables finales arriba (minimo máximo)
		Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(1,
				1, 0);
		// Ubicamos los listeners
		RoomConfig.Builder rtmConfigBuilder = RoomConfig.builder(new RoomUpdate(this));
		rtmConfigBuilder.setMessageReceivedListener(new MessageReceived(this));
		rtmConfigBuilder.setRoomStatusUpdateListener(new RoomStatusUpdate(this));
		rtmConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);
		// Creamos la partida
		Games.RealTimeMultiplayer.create(_gameHelper.getApiClient(), rtmConfigBuilder.build());
	}

	// Cada vez que en algún listener se modifique en algo la habitación activará
	// este método que se usa para actualizar la lista de participantes
	public void updateRoomProperties(Room room){
		if (room != null) {
			participants = room.getParticipants();
		}
	}

	@Override
	// Método usado para que la Screen pregunte si se puede empezar la partida
	public boolean canMultiplayerGameStart() {
		return startMultiplayerGame;
	}

	@Override
	// Método que envia un mensaje por UDP a todos los participantes (menos a uno mismo)
	// Antes de que toni pregunte: Aunque ponga que te lo envies a ti mismo no funciona
	// leído en StackOverflow y comprobado empiricamente
	public void sendGameMessage(String message) {
		for(Participant p : participants){
			if(!p.getParticipantId().equals(myId))
				Games.RealTimeMultiplayer.sendUnreliableMessage(_gameHelper.getApiClient(),message.getBytes(),roomId,p.getParticipantId());
		}
	}

	@Override
	// Método usado por la Screen para obtener el mensaje que usa el juego
	public MultiplayerMessage receiveGameMessage() {
		return gameMessage;
	}

	// Obtiene de una Room el ID nuestro
	public void setMyId(Room room){
		myId = room.getParticipantId(Games.Players.getCurrentPlayerId(_gameHelper.getApiClient()));
	}

	// Muestra la ventana de espera
	public void showWaitingRoom(Room room) {
		// minimum number of players required for our game
		// For simplicity, we require everyone to join the game before we start it
		// (this is signaled by Integer.MAX_VALUE).
		final int MIN_PLAYERS = Integer.MAX_VALUE;
		Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(_gameHelper.getApiClient(), room, MIN_PLAYERS);

		// show waiting room UI
		startActivityForResult(i, REQUEST_CODE_WAITING_ROOM);
	}

	// Abandonar la habitación
	@Override
	public void leaveRoom() {
		Gdx.app.log("multi","Leave room");

		if (roomId != "") {
			Games.RealTimeMultiplayer.leave(_gameHelper.getApiClient(), new RoomUpdate(this), roomId);
			roomId = "";
		}

		resetMultiplayerProperties();
	}

	// Método privado usado para resetear las propiedades del multijugador
	private void resetMultiplayerProperties(){
		startMultiplayerGame 	= false;
		gameMessage = null;
		roomId 					= "";
		participants 			= new ArrayList<Participant>();
		gameMessage 			= new MultiplayerMessage();
	}
}
