package com.tfg.spacegame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShapeRendererManager;

public class GameObject {

    //Textura asociada al objeto
    private Texture texture;

    //Almacena el centro de la textura
    private Vector2 center;

    //Distancia entre el centro y el vértice más alejado
    private float radio;

    //Objeto lógico, con el que trabajaremos para interactuar con los demás elementos
    private Polygon logicShape;

    private float width;
    private float height;

    public GameObject(String textureName, int x, int y) {
        texture = AssetsManager.loadTexture(textureName);
        center = new Vector2();

        float[] vertices = SpaceGame.loadShape(textureName);


        if(vertices == null){

            vertices = new float[8];

            vertices[0] = 0;
            vertices[1] = 0;

            vertices[2] = 0;
            vertices[3] = texture.getHeight();

            vertices[4] = texture.getWidth();
            vertices[5] = texture.getHeight();

            vertices[6] = texture.getWidth();
            vertices[7] = 0;
        }
        logicShape = new Polygon(vertices);

        logicShape.setPosition(x,y);

        this.loadWidthAndHeight();
        this.relocateCenter();
        this.calculateRadio();

        //Recolocamos el origen del logic shape para cuando se realice un giro con setRotation
        this.getLogicShape().setOrigin(this.getWidth() / 2, this.getHeight() / 2);
    }

    private void loadWidthAndHeight(){

        float widthLowestPoint = logicShape.getVertices()[0];
        float widthGreaterPoint = logicShape.getVertices()[0];

        float heightLowestPoint = logicShape.getVertices()[1];
        float heightGreaterPoint = logicShape.getVertices()[1];

        for(int i = 2; i < logicShape.getVertices().length; i++){

            if(i%2 == 0){
                if(logicShape.getVertices()[i] < widthLowestPoint)
                    widthLowestPoint = logicShape.getVertices()[i];

                if(logicShape.getVertices()[i] > widthGreaterPoint)
                    widthGreaterPoint = logicShape.getVertices()[i];
            }else{
                if(logicShape.getVertices()[i] < heightLowestPoint)
                    heightLowestPoint = logicShape.getVertices()[i];

                if(logicShape.getVertices()[i] > heightGreaterPoint)
                    heightGreaterPoint = logicShape.getVertices()[i];
            }
        }

        width = widthGreaterPoint - widthLowestPoint;
        height = heightGreaterPoint - heightLowestPoint;
    }


    public void relocateCenter() {

        center.set(this.getX() + (this.getWidth() / 2),
                   this.getY() + (this.getHeight() / 2));
    }

    public void calculateRadio() {
        float[] vertices = this.getLogicShape().getVertices();
        float distance = 0.0f;
        float aux;

        for (int i=0; i<vertices.length; i+=2) {
            aux = Vector2.dst(vertices[i], vertices[i+1], this.getCenter().x, this.getCenter().y);
            if (distance < aux)
                distance = aux;
        }

        radio = distance;
    }

    public Vector2 getCenter(){ return center; }

    public float getRadio() { return radio; }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getX() {
        return logicShape.getX();
    }

    public float getY() {
        return logicShape.getY();
    }

    public void setX(float x) {
        logicShape.setPosition( x , logicShape.getY());
        this.relocateCenter();
    }

    public void setY(float y) {
        logicShape.setPosition(logicShape.getX(), y);
        this.relocateCenter();
    }

    public Texture getTexture(){
        return texture;
    }

    public void setTexture(String textureName) { texture = AssetsManager.loadTexture(textureName); }

    public Polygon getLogicShape() {
        return logicShape;
    }

    public void setScale(float x, float y){
        logicShape.setScale(x,y);
    }
    public void setRotation(float angle){
        logicShape.setRotation(angle);
    }
    public void setOrigin(float x,float y){
        logicShape.setOrigin(x,y);
    }

    public void render(){
        if (this.getLogicShape().getScaleX() != 1 || this.getLogicShape().getScaleY() != 1)
            this.renderScale(this.getLogicShape().getScaleX(), this.getLogicShape().getScaleY(), 0);
        else
            SpaceGame.batch.draw(texture, getX(), getY());

        //ShapeRendererManager.renderPolygon(this.getLogicShape().getTransformedVertices(), Color.BLUE);
    }

    //Método para pintar un objeto rotando N grados su textura
    public void renderRotate(float n){
        SpaceGame.batch.draw(new TextureRegion(texture), getX(), getY(), getWidth()/2, getHeight()/2,
                                getWidth(), getHeight(),
                                this.getLogicShape().getScaleX(), this.getLogicShape().getScaleY(), n);

        //ShapeRendererManager.renderPolygon(this.getLogicShape().getTransformedVertices(), Color.BLUE);
    }

    //Método para pintar un objeto a razón de su escalado, lo llama el render normal si se tiene escalado
    private void renderScale(float scaleX, float scaleY, float n){
        SpaceGame.batch.draw(new TextureRegion(texture), getX(), getY(), getWidth()/2, getHeight()/2, getWidth(), getHeight(), scaleX, scaleY, n);
    }

    public void dispose() {
        texture.dispose();
    }

    //Indica si hay una colisión con el objeto pasado por parámetro
    public boolean isOverlapingWith(GameObject g) {
        boolean result = false;

        float distance = Vector2.dst(this.getCenter().x, this.getCenter().y, g.getCenter().x, g.getCenter().y);
        float totalRadios = this.getRadio() + g.getRadio();

        //Solo se comprueba la colisión si la distancia entre los centros es menor a la suma de los radios
        if (distance < totalRadios)
            result = Intersector.overlapConvexPolygons(this.getLogicShape(), g.getLogicShape());

        return result;
    }

    //Indica si el objeto está sobre el píxel indicado por parámetro
    public boolean isOverlapingWith(float x, float y) {
        return getLogicShape().contains(x,y);
    }
}
