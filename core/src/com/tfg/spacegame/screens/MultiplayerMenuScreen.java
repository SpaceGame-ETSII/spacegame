package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.tfg.spacegame.BasicScreen;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Button;
import com.tfg.spacegame.utils.BackgroundManager;
import com.tfg.spacegame.utils.FontManager;
import com.tfg.spacegame.utils.ScreenManager;
import com.tfg.spacegame.utils.TextInput;

public class MultiplayerMenuScreen extends BasicScreen {

    private final SpaceGame game;

	private Button invitePlayer;
	private Button quickGame;
	private Button seeInvitations;
	private Button back;

	private float timeUntilExit;

    public MultiplayerMenuScreen(final SpaceGame gam) {
        game = gam;

		//Avisamos al background manager de qué fondo queremos
		BackgroundManager.changeCurrentBackgrounds(BackgroundManager.BackgroundType.MULTIPLAYER);

		quickGame = new Button("button", 260, 315, "quickGame", true);
		invitePlayer = new Button("button", 260, 255, "invitePlayer", true);
		seeInvitations = new Button("button", 260, 195, "seeInvitations",true);
		back = new Button("arrow_back", 750, 430, null, true);

		timeUntilExit = 0.5f;
    }

	@Override
	public void mainRender(float delta) {
		quickGame.render();
		invitePlayer.render();
		seeInvitations.render();
		back.render();
	}

	public void update(float delta) {

		if (Gdx.input.justTouched()) {

			invitePlayer.update();
			quickGame.update();
			seeInvitations.update();
			back.update();

			if (invitePlayer.isPressed() || quickGame.isPressed() || seeInvitations.isPressed() || back.isPressed()){
				timeUntilExit=0.5f;
			}
		}

		if (timeUntilExit <= 0) {
			if(invitePlayer.isPressed()){
				ScreenManager.changeScreen(game, MultiplayerScreen.class, "INVITE");
			}
			else if(quickGame.isPressed()){
				ScreenManager.changeScreen(game, MultiplayerScreen.class, "QUICK");
			}
			else if(seeInvitations.isPressed()){
				ScreenManager.changeScreen(game, MultiplayerScreen.class ,"SEE_INVITATIONS");
			}else if (back.isPressed()){
				ScreenManager.changeScreen(game, MainMenuScreen.class);
			}
		}else{
			timeUntilExit-=delta;
		}
	}

	@Override
	public void dispose() {
		invitePlayer.dispose();
		quickGame.dispose();
		seeInvitations.dispose();
		back.dispose();
	}
}
