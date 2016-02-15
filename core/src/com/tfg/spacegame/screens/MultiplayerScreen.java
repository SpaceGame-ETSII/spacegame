package com.tfg.spacegame.screens;

import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.GameScreen;

public class MultiplayerScreen extends GameScreen {

    private final SpaceGame game;

    public MultiplayerScreen(final SpaceGame gam) {
        game = gam;
    }

	@Override
	public void renderEveryState(float delta) {
		game.text.draw(game.batch, "MODO MULTIJUGADOR", 290, 400);
	}

	@Override
	public void updateEveryState(float delta) {

	}

	@Override
	public void renderReady(float delta) {

	}

	@Override
	public void updateReady(float delta) {

	}

	@Override
	public void renderStart(float delta) {

	}

	@Override
	public void updateStart(float delta) {

	}

	@Override
	public void renderPause(float delta) {

	}

	@Override
	public void updatePause(float delta) {

	}

	@Override
	public void renderWin(float delta) {

	}

	@Override
	public void updateWin(float delta) {

	}

	@Override
	public void renderLose(float delta) {

	}

	@Override
	public void updateLose(float delta) {

	}

	@Override
	public void disposeScreen() {

	}
}
