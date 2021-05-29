package com.gag.game.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.gag.game.Juego;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
	public static void main(String[] args) {
		createApplication();
	}

	private static Lwjgl3Application createApplication() {
		return new Lwjgl3Application(new Juego(), getDefaultConfiguration());
	}

	private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
		Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
//		configuration.setTitle("FlappyBird");
//		configuration.setWindowedMode(640, 480);
		configuration.setTitle(Juego.TITULO);
		configuration.setResizable(false);
		configuration.setFullscreenMode(configuration.getDisplayMode());
		//configuration.setWindowedMode(Juego.ANCHO, Juego.ALTO);
		configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
		return configuration;
	}
}