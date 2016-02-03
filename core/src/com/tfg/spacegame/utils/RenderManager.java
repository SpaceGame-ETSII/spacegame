package com.tfg.spacegame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.enemies.PartOfEnemy;

public class RenderManager {

    private static FrameBuffer canvasOffScreen;
    private static FrameBuffer canvasVerticalBlur;
    private static FrameBuffer canvasHorizontallBlur;
    private static FrameBuffer canvasBlend;
    private static FrameBuffer canvasScaleDown;
    private static FrameBuffer canvasScaleUp;

    private static TextureRegion offScreenTexture;
    private static TextureRegion horizontalBlurTexture;
    private static TextureRegion verticalBlurTexture;
    private static TextureRegion blendTexture;
    private static TextureRegion scaleDownTexture;
    private static TextureRegion scaleUpTexture;

    private static ShaderProgram filterColor;
    private static ShaderProgram blurHShader;
    private static ShaderProgram blurVShader;


    public static void initialize(){

        filterColor = new ShaderProgram(Gdx.files.internal("shaders/filterColor.vertex.glsl"),Gdx.files.internal("shaders/filterColor.fragment.glsl"));

        if(!filterColor.isCompiled())
            throw new IllegalArgumentException("Error al cargar el Shader: "+ filterColor.getLog());

        blurHShader = new ShaderProgram(Gdx.files.internal("shaders/blur.vertex.glsl"),Gdx.files.internal("shaders/blur.fragment.glsl"));

        if(!blurHShader.isCompiled())
            throw new IllegalArgumentException("Error al cargar el Shader: "+blurHShader.getLog());

        blurVShader = new ShaderProgram(Gdx.files.internal("shaders/blur.vertex.glsl"),Gdx.files.internal("shaders/blur.fragment.glsl"));

        if(!blurVShader.isCompiled())
            throw new IllegalArgumentException("Error al cargar el Shader: "+blurVShader.getLog());


        blurHShader.begin();
        blurHShader.setUniformf("dir", 1f, 0f);
        blurHShader.setUniformf("resolution", 1024);
        blurHShader.setUniformf("radius", 1f);
        blurHShader.end();

        blurVShader.begin();
        blurVShader.setUniformf("dir", 0f, 1f);
        blurVShader.setUniformf("resolution", 1024);
        blurVShader.setUniformf("radius", 1f);
        blurVShader.end();

        canvasOffScreen         = new FrameBuffer(Pixmap.Format.RGBA8888, 800, 480, false);
        canvasHorizontallBlur   = new FrameBuffer(Pixmap.Format.RGBA8888, 800, 480, false);
        canvasVerticalBlur      = new FrameBuffer(Pixmap.Format.RGBA8888, 800, 480, false);
        canvasBlend             = new FrameBuffer(Pixmap.Format.RGBA8888, 800, 480, false);
        canvasScaleDown         = new FrameBuffer(Pixmap.Format.RGBA8888, 800, 480, false);
        canvasScaleUp           = new FrameBuffer(Pixmap.Format.RGBA8888, 800, 480, false);

        offScreenTexture = new TextureRegion();
        blendTexture = new TextureRegion();
        verticalBlurTexture = new TextureRegion();
        horizontalBlurTexture = new TextureRegion();
        scaleDownTexture = new TextureRegion();
        scaleUpTexture = new TextureRegion();
    }

    public static void render(){

        SpaceGame.batch.setShader(null);

        makeOffScreenTexture();

        makeHorizontalBlur(offScreenTexture);

        makeVerticalBlur(horizontalBlurTexture);

        makeBlendTexture();
/*
        makeVerticalBlur(blendTexture);

        makeBlendTexture();
*/
        // ------------ RENDERIZAR EN LA PANTALLA EL RESULTADO -----------------------------

        // Desacoplamos el shader para volverlo a poner por defecto
        SpaceGame.batch.setShader(null);

        SpaceGame.batch.draw(blendTexture,0,0);
    }

    private static void makeScaleDown(TextureRegion texture, float scaleFactorX, float scaleFactorY){

        SpaceGame.batch.setShader(null);
        canvasScaleDown.begin();
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            SpaceGame.batch.draw(texture,0,0,0,0,800,480,scaleFactorX,scaleFactorY,0);
            SpaceGame.batch.flush();
        canvasScaleDown.end();

        scaleDownTexture = assignNewTexture(canvasScaleDown.getColorBufferTexture());
    }

    private static void makeScaleUp(TextureRegion texture, float scaleFactorX, float scaleFactorY){
        SpaceGame.batch.setShader(null);
        canvasScaleUp.begin();
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            SpaceGame.batch.draw(texture,0,0,0,0,800,480,800/(800*scaleFactorX),480/(480*scaleFactorY),0);
            SpaceGame.batch.flush();
        canvasScaleUp.end();

        scaleUpTexture = assignNewTexture(canvasScaleUp.getColorBufferTexture());
    }

    private static TextureRegion assignNewTexture(Texture texture){
        TextureRegion result = new TextureRegion(texture);
        result.flip(false, true);
        return result;
    }

    private static void makeBlendTexture(){
        // ---------- UNIR LOS COLORES DE LAS DOS TEXTURAS
        SpaceGame.batch.setShader(null);

        canvasBlend.begin();
        //Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        SpaceGame.batch.draw(verticalBlurTexture,0,0);
        SpaceGame.batch.setBlendFunction(GL20.GL_ONE,GL20.GL_ONE);
        SpaceGame.batch.draw(offScreenTexture,0,0);

        SpaceGame.batch.flush();
        canvasBlend.end();

        SpaceGame.batch.setBlendFunction(SpaceGame.default_blending[0], SpaceGame.default_blending[1]);

        blendTexture = assignNewTexture(canvasBlend.getColorBufferTexture());
        // ---------------------------------------------------------------------------------
    }

    private static void makeOffScreenTexture(){

        // -------------- RENDER LAS COSAS EN EL BUFFER PARA EL OFFSCREEN ----------------

        // Vamos a pintar primero la textura que queremos en un fondo, sin nada
        SpaceGame.batch.setShader(null);

        canvasOffScreen.begin();
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for(Enemy enemy: EnemiesManager.enemies)
            //El enemigo se pintará si no está marcado para borrar y no es parte de un enemigo mayor
            if(!enemy.isDeletable() && !(enemy instanceof PartOfEnemy))
                enemy.render(SpaceGame.batch);

        SpaceGame.batch.flush();

        canvasOffScreen.end();

        offScreenTexture = assignNewTexture(canvasOffScreen.getColorBufferTexture());

        // ---------------------------------------------------------------------------------
    }

    private static void makeHorizontalBlur(TextureRegion texture){
        // Vamos a usar el blur shader
        SpaceGame.batch.setShader(blurHShader);

        // Aplicamos al canvas anterior el shader horizontal
        canvasHorizontallBlur.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        SpaceGame.batch.draw(texture,0,0);
        SpaceGame.batch.flush();
        canvasHorizontallBlur.end();

        SpaceGame.batch.setShader(null);

        horizontalBlurTexture = assignNewTexture(canvasHorizontallBlur.getColorBufferTexture());
    }

    private static void makeVerticalBlur(TextureRegion texture){
        SpaceGame.batch.setShader(blurVShader);

        // Aplicamos al canvas anterior el shader vertical
        canvasVerticalBlur.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        SpaceGame.batch.draw(texture,0,0);
        SpaceGame.batch.flush();
        canvasVerticalBlur.end();

        SpaceGame.batch.setShader(null);

        verticalBlurTexture = assignNewTexture(canvasVerticalBlur.getColorBufferTexture());

        // --------------------------------------------------------------------------------

    }

}
