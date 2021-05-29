package com.gag.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gag.game.pantallas.Pantalla;
import com.gag.game.pantallas.PantallaMenu;
import com.gag.game.pantallas.PantallaSplash;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

/**
 * Esta es la clase que sirve de nexo para todo
 * **/
public class Juego extends Game {
	//public static final int ANCHO=800;
	//public static final int ALTO=480;
	public static final String TITULO="CrossDefender";

	private OrthographicCamera camara;
	private FitViewport vista;
	private SpriteBatch sb;
	private Music musica;
	private Pantalla actual;
	private AssetManager am;
	private int alto;
	private int ancho;

	@Override
	public void create() {
		camara = new OrthographicCamera();
		ancho=Gdx.graphics.getWidth();
		alto=Gdx.graphics.getHeight();
		vista= new FitViewport(ancho,alto,camara);
		sb=new SpriteBatch();
		am=new AssetManager();
//		cambiarPantalla(null,new PantallaMenu());
		cambiarPantalla(null,new PantallaSplash());
	}

	@Override
	public void resize(int width, int height) {
		vista.update(width, height,true);
	}

	@Override
	public void dispose() {
		super.dispose();
		sb.dispose();
		am.dispose();
	}

	// --------------- Getters y Setters ----------------------------
	public SpriteBatch getSb() {
		return sb;
	}

	public FitViewport getVista() {
		return vista;
	}

	public Pantalla getActual() {
		return actual;
	}

	public AssetManager getAm() {
		return am;
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	//---------------- Gestión de pantallas --------------------------------
	public void cambiarPantalla(Pantalla antigua, Pantalla nueva){
		if(antigua!=null){
			antigua.dispose();
		}
		setScreen(nueva);
		actual=nueva;
	}


}