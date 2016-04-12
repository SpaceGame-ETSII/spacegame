package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.tfg.spacegame.BasicScreen;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Button;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.FontManager;
import com.tfg.spacegame.utils.ScreenManager;
import com.tfg.spacegame.utils.TextInput;

public class MultiplayerMenuScreen extends BasicScreen {

    private final SpaceGame game;

	private Button createGame;
	private Button quickGame;
	private Button joinGame;

	private float timeUntilExit;

	private TextInput gameNameInput;

	public Texture background;

    public MultiplayerMenuScreen(final SpaceGame gam) {
        game = gam;

		background = AssetsManager.loadTexture("background");

		quickGame = new Button("button", 260, 315, "quickGame", true);
		createGame = new Button("button", 260, 255, "createGame", true);
		joinGame = new Button("button", 260, 195, "joinGame",true);

		timeUntilExit = 0.5f;

		gameNameInput = new TextInput(FontManager.getFromBundle("askGameName"),"");
    }

	@Override
	public void mainRender(float delta) {
		quickGame.render();
		createGame.render();
		joinGame.render();
	}

	public void update(float delta) {

		if (Gdx.input.justTouched()) {

			createGame.update();
			quickGame.update();
			joinGame.update();

			if (createGame.isPressed() || quickGame.isPressed() || joinGame.isPressed()){
				timeUntilExit=0.5f;
			}
		}

		if (timeUntilExit <= 0) {
			if(createGame.isPressed()){
				gameNameInput.show();
				if(gameNameInput.acceptInputDialog()){
					ScreenManager.changeScreen(game,MultiplayerScreen.class, gameNameInput.getText(), false);
				}

			}
			else if(quickGame.isPressed()){
				ScreenManager.changeScreen(game,MultiplayerScreen.class,"",false);
			}
			else if(joinGame.isPressed()){
				gameNameInput.show();
				if(gameNameInput.acceptInputDialog()){
					ScreenManager.changeScreen(game, MultiplayerScreen.class ,gameNameInput.getText(),false);
				}
			}
		}else{
			timeUntilExit-=delta;
		}
	}

	@Override
	public void dispose() {
		createGame.dispose();
		quickGame.dispose();
		joinGame.dispose();
	}
}
