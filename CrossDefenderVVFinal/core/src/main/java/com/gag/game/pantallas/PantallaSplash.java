package com.gag.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.gag.game.Guardado;

public class PantallaSplash extends Pantalla{
    Vector2 posicionBarra;
    Vector2 posicionPiloto;
    int incrementoPosicion;
    Texture fondoCarga;
    Texture barra;
    Texture piloto;

    public PantallaSplash() {
        super();
        //Pongo en cola los assets necesarios para esta pantalla
        am.load("Splash.jpeg", Texture.class);
        am.load("BarraCarga.png", Texture.class);
        am.load("PilotoCarga.png", Texture.class);
        //Espera hasta que haya cargado de manera efectiva estos tres assets -> carga síncrona
        am.finishLoading();
        //Asigno esos assets que ya están cargados
        fondoCarga=am.get("Splash.jpeg");
        barra=am.get("BarraCarga.png");
        piloto=am.get("PilotoCarga.png");
        posicionBarra=new Vector2(juego.getAncho()/2f-barra.getWidth()/2f, juego.getAlto()/2f-barra.getHeight()/2f);
        posicionPiloto=new Vector2(juego.getAncho()/2f-piloto.getWidth()/2f, juego.getAlto()/2f-piloto.getHeight()/2f);
        incrementoPosicion=1;//comenzará moviéndose a la derecha

        //El resto de assets del juego se cargarán en sucesivas pasadas del "render" -> carga asíncrona
        am.load("Flechas.png",Texture.class);
        am.load("GO.jpg", Texture.class);
        am.load("Flechas.atlas",TextureAtlas.class);
        am.load("Arcos.png", Texture.class);
        am.load("Arcos.atlas", TextureAtlas.class);
        am.load("pp2.mp3", Music.class);
        am.load("FantasmaRun.png",Texture.class);
        am.load("FantasmaRun.atlas",TextureAtlas.class);
	    am.load("Fondo.jpg", Texture.class);
        am.load("FondoTienda.jpg", Texture.class);
        am.load("Arco.png", Texture.class);
        am.load("Titulo.png", Texture.class);
        am.load("shop.png", Texture.class);
        am.load("separador.png", Texture.class);
        am.load("iluminacion.png", Texture.class);
        am.load("equip.png", Texture.class);
        am.load("upgrade.png", Texture.class);
        am.load("equip2.png", Texture.class);
        am.load("upgrade2.png", Texture.class);
        am.load("gold.png", Texture.class);
        am.load("madera.png", Texture.class);
        am.load("piedra.png", Texture.class);
        am.load("sello.png", Texture.class);
        am.load("fuente.fnt", BitmapFont.class);
        am.load("Flecha1.png", Texture.class);
        am.load("Flecha2.png", Texture.class);
        am.load("Flecha3.png", Texture.class);
        am.load("MejorarMuro.png", Texture.class);
        am.load("BotonPlay.png", Texture.class);
        am.load("pp2.mp3", Music.class);
        am.load("alien_06.ogg", Sound.class);
        am.load("bug_01.ogg", Sound.class);
        am.load("cute_02.ogg", Sound.class);
        am.load("1.png", Texture.class);
        am.load("Titulo.png", Texture.class);
        am.load("equip.png", Texture.class);
        am.load("upgrade.png", Texture.class);
        am.load("Items.png", Texture.class);
        am.load("Flecha1.png", Texture.class);
        am.load("Flecha2.png", Texture.class);
        am.load("Flecha3.png", Texture.class);
        am.load("MejorarMuro.png", Texture.class);
        am.load("muroroto1.png", Texture.class);
        am.load("muroroto2.png", Texture.class);
        am.load("devil_wall.jpg", Texture.class);
        cargarPartida();

    }

    @Override
    public void leerEntrada(float delta) {

    }

    @Override
    public void actualizar(float delta) {
        //Si ha terminado la carga, paso a la siguiente pantalla
        if(am.update()){
            juego.cambiarPantalla(juego.getActual(),new PantallaMenu());
        }
        //En caso contrario, sigo actualizando la animación de la barra de carga
        posicionPiloto.x+=incrementoPosicion;
        if(posicionPiloto.x-posicionBarra.x >= barra.getWidth() - 75){
            incrementoPosicion=-1;
        }else if(posicionPiloto.x-posicionBarra.x <= 2){
            incrementoPosicion=1;
        }
    }

    @Override
    public void dibujar(float delta) {
        sb.begin();
        sb.draw(fondoCarga,
                juego.getAncho()*.5f-fondoCarga.getWidth()*.5f,
                juego.getAlto()*.5f-fondoCarga.getHeight()*.5f);
        sb.draw(barra,posicionBarra.x,posicionBarra.y);
        sb.draw(piloto,posicionPiloto.x,posicionPiloto.y);
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

    public void cargarPartida(){
        FileHandle file = Gdx.files.local("partida.json");
        if (file.exists()){
            String datos=file.readString();
            Json json= new Json();
            Guardado partida=json.fromJson(Guardado.class,datos);
            partida.colocarDatos();
        }
    }
}
