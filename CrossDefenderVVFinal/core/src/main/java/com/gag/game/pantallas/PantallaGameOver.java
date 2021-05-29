package com.gag.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class PantallaGameOver extends Pantalla{
    private final Texture fondo;

    public PantallaGameOver(){
        fondo = am.get("GO.jpg");
    }

    @Override
    public void leerEntrada(float delta) {
        //Como salir de pantalla game over
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            juego.cambiarPantalla(this,new PantallaMenu());
        }if (Gdx.input.justTouched()){
            juego.cambiarPantalla(this,new PantallaMenu());
        }
    }

    @Override
    public void actualizar(float delta) {

    }

    @Override
    public void dibujar(float delta) {
        sb.begin();
        sb.draw(fondo,juego.getAncho()*.5f-fondo.getWidth()*.5f,
                juego.getAlto()*.5f-fondo.getHeight()*.5f);
        sb.end();
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
}