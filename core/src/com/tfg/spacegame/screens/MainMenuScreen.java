package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Button;
import com.tfg.spacegame.utils.AssetsManager;

public class MainMenuScreen implements Screen {

    private final SpaceGame game;

    //Representan las opciones a elegir en el Menú
    private Button campaign;
    private Button arcade;
    private Button multiplayer;
    private Button options;
    private Button exit;

    //Representa el tiempo que dura el efecto visual de pulsado sobre una opción
    private float timeUntilExit;

    public Texture background;

    public MainMenuScreen(final SpaceGame game) {
        this.game = game;

        background = AssetsManager.loadTexture("background");

        //Creamos los botones para el menú principal
        campaign = new Button("button", new CampaignScreen(game), 290, 315);
        arcade = new Button("button", new ArcadeScreen(game), 290, 255);
        multiplayer = new Button("button", new MultiplayerScreen(game), 290, 195);
        options = new Button("button", new OptionsScreen(game), 290, 135);
        exit = new Button("button", null, 290, 75);

        //Inicializamos el timer de espera para el efecto en los botones
        timeUntilExit = 0.5f;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpaceGame.camera.update();
        SpaceGame.batch.setProjectionMatrix(SpaceGame.camera.combined);

        SpaceGame.batch.begin();

        // Pintamos el fondo y el título del juego
        SpaceGame.batch.draw(background, 0,0);
        SpaceGame.drawText(SpaceGame.title,"titleGame",229,420);

        // Delegamos el render de los botones
        campaign.render(SpaceGame.batch);
        arcade.render(SpaceGame.batch);
        multiplayer.render(SpaceGame.batch);
        options.render(SpaceGame.batch);
        exit.render(SpaceGame.batch);

        // Pintamos los títulos de los botontes
        SpaceGame.drawText(SpaceGame.text,"campaignTitle",330,345);
        SpaceGame.drawText(SpaceGame.text,"arcadeTitle",335,285);
        SpaceGame.drawText(SpaceGame.text,"multiplayerTitle",298,225);
        SpaceGame.drawText(SpaceGame.text,"optionsTitle",330,165);
        SpaceGame.drawText(SpaceGame.text,"exitTitle",355,105);

        SpaceGame.batch.end();

        //Si se ha tocado algún botón, lo marcamos como pulsado
        if (Gdx.input.justTouched()) {

            Vector3 v = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            v = SpaceGame.camera.unproject(v);

            if (campaign.press(v.x, v.y) ||
                    arcade.press(v.x, v.y) ||
                    multiplayer.press(v.x, v.y) ||
                    options.press(v.x, v.y) ||
                    exit.press(v.x, v.y)) {
                timeUntilExit=0.5f;
            }
        }

        //Actualizamos los botones si fueron pulsados previamente
        updateButton(delta, campaign);
        updateButton(delta, arcade);
        updateButton(delta, multiplayer);
        updateButton(delta, options);
        updateButton(delta, exit);

    }

    //Si se ha acabado el tiempo de la pulsación, hacemos la función del botón correspondiente
    public void updateButton(float delta, Button button){
        if (timeUntilExit <= 0 && button.isPressed()) {

            //Si el screen del botón no es nulo, nos vamos ahí. Si lo es, significa que es el exit y debemos salir
            if (button.getScreen() != null) {
                game.setScreen(button.getScreen());
            } else {
                Gdx.app.exit();
            }

            dispose();
        } else {
            timeUntilExit -= delta;
        }
    }

    @Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
        campaign.dispose();
        arcade.dispose();
        multiplayer.dispose();
        options.dispose();
        exit.dispose();
	}

}
