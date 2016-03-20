package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Button;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.FontManager;
import com.tfg.spacegame.utils.TextInput;

public class MultiplayerMenuScreen implements Screen{


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
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		SpaceGame.camera.update();
		SpaceGame.batch.setProjectionMatrix(SpaceGame.camera.combined);

		SpaceGame.batch.begin();

		SpaceGame.batch.draw(background, 0,0);

		quickGame.render();
		createGame.render();
		joinGame.render();

		SpaceGame.batch.end();

		this.update(delta);

	}

	private void update(float delta) {

		if (Gdx.input.justTouched()) {

			Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			v = SpaceGame.camera.unproject(v);

			if (createGame.press(v.x, v.y) || quickGame.press(v.x, v.y) || joinGame.press(v.x, v.y)){
				timeUntilExit=0.5f;
			}
		}

		if (timeUntilExit <= 0) {
			if(createGame.isPressed()){
				gameNameInput.show();
				if(gameNameInput.acceptInputDialog()){
					game.setScreen(new MultiplayerScreen(game,gameNameInput.getText(),true));
				}

			}
			else if(quickGame.isPressed()){
				game.setScreen(new MultiplayerScreen(game,"",false));
			}
			else if(joinGame.isPressed()){
				gameNameInput.show();
				if(gameNameInput.acceptInputDialog()){
					game.setScreen(new MultiplayerScreen(game,gameNameInput.getText(),false));
				}
			}
		}else{
			timeUntilExit-=delta;
		}
	}



	@Override
	public void show() {

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		createGame.dispose();
		quickGame.dispose();
		joinGame.dispose();
	}
}
