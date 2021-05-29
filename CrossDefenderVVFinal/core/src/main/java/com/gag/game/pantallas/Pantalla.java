package com.gag.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gag.game.Juego;

public abstract class Pantalla implements Screen {
    //Para mantener siempre las referencias y facilitar la escritura
    protected Juego juego;
    protected SpriteBatch sb;
    protected AssetManager am;

    protected Pantalla(){
        this.juego=(Juego)Gdx.app.getApplicationListener();
        this.sb= juego.getSb();
        this.am=juego.getAm();
    }

    @Override
    public void render(float delta) {
        //Limpia la pantalla https://github.com/libgdx/libgdx/wiki/Spritebatch%2C-Textureregions%2C-and-Sprites#spritebatch
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*Para una mejor organización, descomponer el renderizado en:
        - Actualizar: cálculos preparatorios
        - Dibujar: renderizado en sí
        Cada pantalla los implementará a su manera
        */
        leerEntrada(delta);
        actualizar(delta);
        dibujar(delta);
    }

    public abstract void leerEntrada(float delta);

    public abstract void actualizar(float delta);

    public abstract void dibujar(float delta);

    @Override
    public void dispose() {
    }
}
