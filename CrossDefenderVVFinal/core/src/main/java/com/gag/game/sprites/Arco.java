package com.gag.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Arco extends Sprite {
    private final Texture arcoTextura;
    private static int nivelArco=1;
    private static int nivelMuro=1;
    private static float dps=3.0f;
    private static int vida=1000;
    private static int health;
    private static int costeArco=50,costeMuro=50;
    private Vector2 posicionArco;

    public Arco() {
        //Aspecto
        super(new Texture("1.png"));
        arcoTextura=new Texture("1.png");
        setTexture(arcoTextura);

        //Vida dentro de la partida
        health=vida;

        //Posicion y Origen
        posicionArco= new Vector2(0,0);
        setPosition(posicionArco.x, posicionArco.y);
        setOrigin(getWidth()/2,getHeight()/2);

    }

    public static void mejoraArco(){
        int costeOro=nivelArco*50;

        if(Items.oro<costeOro){

            System.out.println("No tienes recursos");

        }else if (nivelArco==10 || nivelArco == 20 || nivelArco == 30){

            costeOro=nivelArco*150;
            Items.costeRecurso(costeOro,1);
            nivelArco++;
            dps -= 0.05f;
            costeArco=nivelArco*150;
        }
        else{
            Items.costeRecurso(costeOro,1);

            nivelArco++;
            dps -= 0.1f;
            costeArco=nivelArco*50;
        }
    }

    public static void mejoraMuro(){
        int costeOro=nivelMuro*50;

        if(Items.oro<costeOro){

            System.out.println("No tienes recursos");

        }else if(nivelMuro==10 || nivelMuro == 20 || nivelMuro == 30) {

            costeOro = nivelMuro * 150;
            Items.costeRecurso(costeOro,2);
            nivelMuro++;
            vida+=1000;
            costeMuro=nivelMuro*150;
        }
        else {
            Items.costeRecurso(costeOro,2);
            nivelMuro++;
            vida+=100;
            costeMuro=nivelMuro*50;
        }
    }

    //Getters y Setters
    public static int getNivelArco() {
        return nivelArco;
    }

    public static int getNivelMuro() {
        return nivelMuro;
    }

    public static void setNivelArco(int nivelArco) {
        Arco.nivelArco = nivelArco;
    }

    public static void setNivelMuro(int nivelMuro) {
        Arco.nivelMuro = nivelMuro;
    }

    public static float getDps() {
        return dps;
    }

    public static void setDps(float dps){
        Arco.dps=dps;
    }

    public static int getHealth() {
        return health;
    }

    public static void setHealth(int health) {
       Arco.health=health;
    }

    public static int getVida() {
        return vida;
    }

    public static void setVida(int vida){
        Arco.vida=vida;
    }

    public static int getCosteArco() {
        return costeArco;
    }

    public static int getCosteMuro() {
        return costeMuro;
    }

    public Vector2 getPosicionArco() {
        return posicionArco;}

    public void setPosicionArco(Vector2 posicionArco) {
        this.posicionArco = posicionArco;
    }

}


