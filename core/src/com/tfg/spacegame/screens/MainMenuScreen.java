package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;

public class MainMenuScreen implements Screen {

    GameObject campaing;
    GameObject arcade;
    GameObject multi;
    GameObject options;
    GameObject exit;

    //Variable que hace referencia al tiempo que dura el efecto de pulsado
    private float timeUntilExit;
    //Todos estos booleans son utilizados para saber si un determinado botón ha sido pulsado
    private boolean pulseCampaing;
    private boolean pulseArcade;
    private boolean pulseMulti;
    private boolean pulseOptions;
    private boolean pulseExit;

    final SpaceGame game;

    public MainMenuScreen(final SpaceGame gam) {
        //Creamos los botones para el menú principal
        campaing = new GameObject("button",290,315);
        arcade = new GameObject("button",290,255);
        multi = new GameObject("button",290,195);
        options = new GameObject("button",290,135);
        exit = new GameObject("button",290,75);

        //Inicializamos los booleans para saber si ha sido pulsado un botón
        pulseCampaing=false;
        pulseArcade=false;
        pulseMulti=false;
        pulseOptions=false;
        pulseExit=false;

        //Inicializamos el timer de espera para el efecto en los botones
        timeUntilExit=0.5f;

        game = gam;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        // Pintamos el fondo
        game.batch.draw(game.background, 0,0);
        // Pintamos cada uno de los botones, y si han sido pulsados lo vemos en pantalla gracias al método renderRotate
        if (pulseCampaing){
            campaing.renderRotate(game.batch,180);
        }else{
            campaing.render(game.batch);
        }
        if(pulseArcade){
            arcade.renderRotate(game.batch,180);
        }else {
            arcade.render(game.batch);
        }
        if(pulseMulti){
            multi.renderRotate(game.batch,180);
        }else {
            multi.render(game.batch);
        }
        if(pulseOptions){
            options.renderRotate(game.batch,180);
        }else{
            options.render(game.batch);
        }
        if(pulseExit){
            exit.renderRotate(game.batch, 180);
        }else {
            exit.render(game.batch);
        }

        // Pintamos los títulos de los botontes
        game.font.draw(game.batch, "SPACE GAME", 350, 400);
        game.font.draw(game.batch, "Modo Campaña", 345, 345);
        game.font.draw(game.batch, "Modo Arcade", 350, 285);
        game.font.draw(game.batch, "Modo Multijugador", 340, 225);
        game.font.draw(game.batch, "Opciones", 365, 165);
        game.font.draw(game.batch, "Salir", 380, 105);

        //Actualizamos los botones si han sido pulsados
        updateButton(delta,new CampaignScreen(game),pulseCampaing);
        updateButton(delta,new ArcadeScreen(game),pulseArcade);
        updateButton(delta,new MultijugadorScreen(game),pulseMulti);
        updateButton(delta,new OptionsScreen(game),pulseOptions);
        updateExit(delta,pulseExit);

        game.batch.end();

        //Comprobamos que botón ha sido pulsado, si ha sido así actualizamos el timer y ponemos a true el boolean correspondiente al botón
        if (Gdx.input.justTouched()) {

            Vector3 v = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            v = game.camera.unproject(v);

            if(campaing.isOverlapingWith(v.x, v.y)){
                timeUntilExit=0.5f;
                pulseCampaing=true;
            }
            if(arcade.isOverlapingWith(v.x, v.y)) {
                timeUntilExit=0.5f;
                pulseArcade=true;
            }
            if(multi.isOverlapingWith(v.x, v.y)){
                timeUntilExit=0.5f;
                pulseMulti=true;
            }
            if(options.isOverlapingWith(v.x, v.y)){
                timeUntilExit=0.5f;
                pulseOptions=true;
            }
            if(exit.isOverlapingWith(v.x, v.y) ){
                timeUntilExit=0.5f;
                pulseExit=true;
            }
        }

    }

    public void updateButton(float delta,Screen screen,boolean ex){
        if(timeUntilExit<=0 && ex){
            game.setScreen(screen);
            dispose();
        }else{
            timeUntilExit-=delta;
        }
    }

    public void updateExit(float delta,boolean ex){
        if(timeUntilExit<=0 && ex){
            Gdx.app.exit();
            dispose();
        }else{
            timeUntilExit-=delta;
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
        campaing.dispose();
        arcade.dispose();
        multi.dispose();
        options.dispose();
        exit.dispose();
	}

}
