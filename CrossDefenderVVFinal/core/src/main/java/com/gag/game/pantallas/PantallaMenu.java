package com.gag.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.gag.game.Guardado;
import com.gag.game.sprites.*;

import java.awt.*;


public class PantallaMenu extends Pantalla{

    //Declaracion del stage
    private final Stage stage;

    //String para el tipo de flecha
    private static String flecha;

    //Daño para el tipo de flecha
    private int dmg;

    //Texturas
    private final Texture fondo,fondoTienda,titulo,arco1,oro,madera,piedra,sello,muro,flecha1,flecha2,flecha3,separador,iluminacion,botonJugar;
    private Texture flechaEquipada;

    //Vectores
    private final Vector2 posicionJugar,posicionArco,posicionMuro,posicionFlecha1,posicionFlecha2,posicionFlecha3;

    //Rectangulo (para variar un poco el uso de botones)
    private final Rectangle areaJugar;

    //Botones
    private final ImageButton mejorar1;
    private final ImageButton mejorar2;
    private final ImageButton mejorar3;
    private final ImageButton mejorarArco;
    private final ImageButton mejorarMuro;
    private final ImageButton equipar1;
    private final ImageButton equipar2;
    private final ImageButton equipar3;

    //Etiquetas y estilo
    private final Label oroLabel,maderaLabel,piedraLabel,selloLabel,niveles,statsActual,precio;
    BitmapFont fuente=am.get("fuente.fnt");
    Label.LabelStyle estilo=new Label.LabelStyle(fuente, Color.WHITE);

    //Boolean para el desplegable de la tienda
    private boolean able=false,tiendalist=false;

    public PantallaMenu() {
        super();
        Music musica= am.get("pp2.mp3");
        musica.setVolume(0.25f);
        musica.setLooping(true);
        musica.play();

        //Stage

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        //Label

        oroLabel= new Label(String.valueOf(Items.oro),estilo);
        maderaLabel= new Label(String.valueOf(Items.madera),estilo);
        piedraLabel= new Label(String.valueOf(Items.piedra),estilo);
        selloLabel= new Label(String.valueOf(Items.sello),estilo);
        niveles= new Label("Nivel de mejoras"+"\n"+
                "Arco: "+Arco.getNivelArco()+"\n"+
                "Muro: "+Arco.getNivelMuro()+"\n"+
                "Flecha normal: "+Tipoflecha.FLECHANORMAL.getNivel()+"\n"+
                "Flecha de piedra: "+Tipoflecha.FLECHAPIEDRA.getNivel()+"\n"+
                "Flecha de fuego: "+Tipoflecha.FLECHAFUEGO.getNivel()+"\n"
                ,estilo);
        statsActual= new Label("Dmg-> "+dmg+" || Dps-> "+Arco.getDps()+" || Vida-> "+Arco.getVida(),estilo);
        precio= new Label("",estilo);

        //Aplicar texturas

        fondo=am.get("Fondo.jpg");
        fondoTienda=am.get("FondoTienda.jpg");
        titulo=am.get("Titulo.png");
        oro=am.get("gold.png");
        madera=am.get("madera.png");
        piedra=am.get("piedra.png");
        sello=am.get("sello.png");
        arco1=am.get("Arco.png");
        muro=am.get("MejorarMuro.png");
        flecha1=am.get("Flecha1.png");
        flecha2=am.get("Flecha2.png");
        flecha3=am.get("Flecha3.png");
        iluminacion=am.get("iluminacion.png");
        separador=am.get("separador.png");
        botonJugar=am.get("BotonPlay.png");

        flechaEquipada=Tipoflecha.FLECHANORMAL.getImagen();
        dmg=Tipoflecha.FLECHANORMAL.getDmg();
        flecha="Flecha1";

        //Botones

        equipar1 = new ImageButton(new TextureRegionDrawable((Texture)am.get("equip.png")),new TextureRegionDrawable((Texture)am.get("equip2.png")));
        equipar2 = new ImageButton(new TextureRegionDrawable((Texture)am.get("equip.png")),new TextureRegionDrawable((Texture)am.get("equip2.png")));
        equipar3 = new ImageButton(new TextureRegionDrawable((Texture)am.get("equip.png")),new TextureRegionDrawable((Texture)am.get("equip2.png")));

        mejorar1 = new ImageButton(new TextureRegionDrawable((Texture)am.get("upgrade.png")),new TextureRegionDrawable((Texture)am.get("upgrade2.png")));
        mejorar2 = new ImageButton(new TextureRegionDrawable((Texture)am.get("upgrade.png")),new TextureRegionDrawable((Texture)am.get("upgrade2.png")));
        mejorar3 = new ImageButton(new TextureRegionDrawable((Texture)am.get("upgrade.png")),new TextureRegionDrawable((Texture)am.get("upgrade2.png")));
        mejorarArco = new ImageButton(new TextureRegionDrawable((Texture)am.get("upgrade.png")),new TextureRegionDrawable((Texture)am.get("upgrade2.png")));
        mejorarMuro = new ImageButton(new TextureRegionDrawable((Texture)am.get("upgrade.png")),new TextureRegionDrawable((Texture)am.get("upgrade2.png")));

        ImageButton tienda = new ImageButton(new TextureRegionDrawable((Texture) am.get("shop.png")));

        //Listener

        tienda.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                able= tiendalist;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                tiendalist= !able;
                return true;
            }
        });

        equipar1.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                flechaEquipada=Tipoflecha.FLECHANORMAL.getImagen();
                dmg=Tipoflecha.FLECHANORMAL.getDmg();
                flecha="Flecha1";
                return true;
            }
        });

        equipar2.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                flechaEquipada=Tipoflecha.FLECHAPIEDRA.getImagen();
                dmg=Tipoflecha.FLECHAPIEDRA.getDmg();
                flecha="Flecha2";
                return true;
            }
        });

        equipar3.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                flechaEquipada=Tipoflecha.FLECHAFUEGO.getImagen();
                dmg=Tipoflecha.FLECHAFUEGO.getDmg();
                flecha="Flecha3";
                return true;
            }
        });

        mejorarArco.addListener(new InputListener(){
            @Override
            public boolean mouseMoved (InputEvent event, float x, float y) {
                precio.setText("Coste: -"+Arco.getCosteArco()+" oro");
                precio.setPosition(juego.getAncho()/3.8f, juego.getAlto()/1.75f);
                return false;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Arco.mejoraArco();
                precio.setText("Coste: -"+Arco.getCosteArco()+" oro");
                precio.setPosition(juego.getAncho()/3.8f,juego.getAlto()/1.75f);
                return true;
            }
        });

        mejorarMuro.addListener(new InputListener(){
            @Override
            public boolean mouseMoved (InputEvent event, float x, float y) {
                precio.setText("Coste: -"+Arco.getCosteMuro()+" oro");
                precio.setPosition(juego.getAncho()/2.01f,juego.getAlto()/1.75f);
                return true;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Arco.mejoraMuro();
                precio.setText("Coste: -"+Arco.getCosteMuro()+" oro");
                precio.setPosition(juego.getAncho()/2.01f,juego.getAlto()/1.75f);
                return true;
            }
        });

        mejorar1.addListener(new InputListener(){

            @Override
            public boolean mouseMoved (InputEvent event, float x, float y) {
                precio.setText("Coste: -"+Tipoflecha.FLECHANORMAL.getCoste()+" Madera");
                precio.setPosition(juego.getAncho()/3.8f, juego.getAlto()/4f);
                return true;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Tipoflecha.mejoraFlechanormal();
                dmg=Tipoflecha.FLECHANORMAL.getDmg();
                precio.setText("Coste: -"+Tipoflecha.FLECHANORMAL.getCoste()+" Madera");
                precio.setPosition(juego.getAncho()/3.8f,juego.getAlto()/4f);
                return true;
            }
        });

        mejorar2.addListener(new InputListener(){
            @Override
            public boolean mouseMoved (InputEvent event, float x, float y) {
                precio.setText("Coste: -"+Tipoflecha.FLECHAPIEDRA.getCoste()+" Piedra");
                precio.setPosition(juego.getAncho()/2.01f,juego.getAlto()/4f);
                return true;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Tipoflecha.mejoraFlechapiedra();
                dmg=Tipoflecha.FLECHAPIEDRA.getDmg();
                precio.setText("Coste: -"+Tipoflecha.FLECHAPIEDRA.getCoste()+" Piedra");
                precio.setPosition(juego.getAncho()/2.01f,juego.getAlto()/4f);
                return true;
            }
        });

        mejorar3.addListener(new InputListener(){
            @Override
            public boolean mouseMoved (InputEvent event, float x, float y) {
                precio.setText("Coste: -"+Tipoflecha.FLECHAFUEGO.getCoste()+" Sellos");
                precio.setPosition(juego.getAncho()/1.37f,juego.getAlto()/4f);
                return true;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Tipoflecha.mejoraFlechafuego();
                dmg=Tipoflecha.FLECHAFUEGO.getDmg();
                precio.setText("Coste: -"+Tipoflecha.FLECHAFUEGO.getCoste()+" Sellos");
                precio.setPosition(juego.getAncho()/1.37f,juego.getAlto()/4f);
                return true;
            }
        });

        //Actors

        stage.addActor(tienda);
        stage.addActor(equipar1);
        stage.addActor(equipar2);
        stage.addActor(equipar3);
        stage.addActor(mejorar1);
        stage.addActor(mejorar2);
        stage.addActor(mejorar3);
        stage.addActor(mejorarArco);
        stage.addActor(mejorarMuro);
        stage.addActor(oroLabel);
        stage.addActor(maderaLabel);
        stage.addActor(piedraLabel);
        stage.addActor(selloLabel);
        stage.addActor(niveles);
        stage.addActor(statsActual);
        stage.addActor(precio);

        //Algunas ubicaciones

        posicionJugar=new Vector2(juego.getAncho()/22.5f,juego.getAlto()/1.2f);
        posicionArco=new Vector2(juego.getAncho()/3.8f,juego.getAlto()/1.6f);
        posicionMuro=new Vector2(juego.getAncho()/2.18f,juego.getAlto()/1.6f);
        posicionFlecha1=new Vector2(juego.getAncho()/3.8f,juego.getAlto()/2.6f);
        posicionFlecha2=new Vector2(juego.getAncho()/2.01f,posicionFlecha1.y);
        posicionFlecha3=new Vector2(juego.getAncho()/1.37f,posicionFlecha1.y);

        oroLabel.setPosition(juego.getAncho()/20f, juego.getAlto()/3f);
        maderaLabel.setPosition(juego.getAncho()/20f, juego.getAlto()/4f);
        piedraLabel.setPosition(juego.getAncho()/20f,juego.getAlto()/6f);
        selloLabel.setPosition(juego.getAncho()/20f,juego.getAlto()/13f);

        tienda.setPosition(juego.getAncho()/16f,juego.getAlto()/1.31f);

        //Para definir "área de click"

        areaJugar=new Rectangle((int)posicionJugar.x,(int)posicionJugar.y,botonJugar.getWidth(),botonJugar.getHeight());
    }

    @Override
    public void show() {

    }

    @Override
    public void leerEntrada(float delta) {
        if(Gdx.input.justTouched()){
            //Debo hacer el unproject porque la vista puede estar escalada!!
            Vector2 puntoClickado=juego.getVista().unproject(new Vector2(Gdx.input.getX(),Gdx.input.getY()));
            if(areaJugar.contains(puntoClickado.x, puntoClickado.y)){
                juego.cambiarPantalla(this,new PantallaAccion());
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_9)){
            //hack de recursos
            Items.oro=999999;
            Items.madera=999999;
            Items.piedra=999999;
            Items.sello=999999;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            //Guardar
            guardar();
            Gdx.app.exit();
        }
    }

    @Override
    public void actualizar(float delta) {
        niveles.setText("Nivel de mejoras"+"\n"+
                "Arco: "+Arco.getNivelArco()+"\n"+
                "Muro: "+Arco.getNivelMuro()+"\n"+
                "Flecha normal: "+Tipoflecha.FLECHANORMAL.getNivel()+"\n"+
                "Flecha de piedra: "+Tipoflecha.FLECHAPIEDRA.getNivel()+"\n"+
                "Flecha de fuego: "+Tipoflecha.FLECHAFUEGO.getNivel()+"\n"
        );
        oroLabel.setText(String.valueOf(Items.oro));
        maderaLabel.setText(String.valueOf(Items.madera));
        piedraLabel.setText(String.valueOf(Items.piedra));
        selloLabel.setText(String.valueOf(Items.sello));

        statsActual.setText("Dmg-> "+dmg+" || Dps-> "+Arco.getDps()+" || Vida-> "+Arco.getVida());
    }

    @Override
    public void dibujar(float delta) {
        //Cursor
        Pixmap pm = new Pixmap(Gdx.files.internal("puntero1.png"));
        int xHotSpot = pm.getWidth() / 2;
        int yHotSpot = pm.getHeight() / 2;
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, xHotSpot, yHotSpot));

        //Dibujo de menu
        sb.enableBlending();
        sb.begin();
        sb.draw(fondo,0,0, juego.getAncho(), juego.getAlto());
        sb.draw(titulo,juego.getAncho()/3.7f,juego.getAlto()/10f);
        sb.draw(botonJugar, posicionJugar.x,posicionJugar.y);
        sb.draw(separador,-110,juego.getAlto()/1.35f);
        sb.draw(separador,-110,juego.getAlto()/2.5f);
        sb.draw(oro,juego.getAncho()/90f,juego.getAlto()/3.2f);
        sb.draw(madera,juego.getAncho()/90f ,juego.getAlto()/4.3f);
        sb.draw(piedra,juego.getAncho()/90f ,juego.getAlto()/6.8f);
        sb.draw(sello,juego.getAncho()/90f ,juego.getAlto()/17f);
        sb.draw(iluminacion,juego.getAncho()/60f,juego.getAlto()/2.4f);
        sb.draw(arco1,juego.getAncho()/25f,juego.getAlto()/2.1f);
        sb.draw(flechaEquipada,posicionJugar.x,juego.getAlto()/1.55f);
        statsActual.setPosition(5,juego.getAlto()/1.45f);

        if(able){
            //Dibujo de tienda
                sb.draw(fondoTienda,juego.getAncho()/4.5f,juego.getAlto()/10f);
                sb.draw(arco1,posicionArco.x,posicionArco.y);
                sb.draw(muro,posicionMuro.x,posicionMuro.y);
                sb.draw(flecha1,posicionFlecha1.x,posicionFlecha1.y);
                sb.draw(flecha2,posicionFlecha2.x,posicionFlecha2.y);
                sb.draw(flecha3,posicionFlecha3.x,posicionFlecha3.y);
                //Botones
                equipar1.setPosition(posicionFlecha1.x-5,posicionFlecha1.y-120);
                mejorar1.setPosition(posicionFlecha1.x-5,posicionFlecha1.y-70);
                equipar2.setPosition(posicionFlecha2.x-5,posicionFlecha2.y-120);
                mejorar2.setPosition(posicionFlecha2.x-5,posicionFlecha2.y-70);
                equipar3.setPosition(posicionFlecha3.x-5,posicionFlecha3.y-120);
                mejorar3.setPosition(posicionFlecha3.x-5,posicionFlecha3.y-70);
                mejorarArco.setPosition(posicionArco.x,posicionArco.y-50);
                mejorarMuro.setPosition(posicionMuro.x+65,posicionMuro.y-50);
                niveles.setPosition(juego.getAncho()/1.4f, posicionMuro.y);

        }else{
            //Ocultar botones al ocultar tienda
            equipar1.setPosition(-200,-200);
            mejorar1.setPosition(-200,-200);
            equipar2.setPosition(-200,-200);
            mejorar2.setPosition(-200,-200);
            equipar3.setPosition(-200,-200);
            mejorar3.setPosition(-200,-200);
            mejorarArco.setPosition(-200,-200);
            mejorarMuro.setPosition(-200,-200);
            niveles.setPosition(-200,-200);
            precio.setPosition(-200,-200);
        }
        sb.end();
        sb.disableBlending();

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

    @Override
    public void render(float delta) {
        //El render hace falta para poder usar los stage y
        super.render(delta);
        stage.act();
        stage.draw();

    }

    public void guardar(){

        Guardado guardar=new Guardado(Items.oro,Items.madera,Items.piedra,Items.sello,Arco.getNivelArco(),Arco.getDps(),Arco.getNivelMuro(),Arco.getVida(),Tipoflecha.FLECHANORMAL.getNivel(),Tipoflecha.FLECHANORMAL.getDmg(),Tipoflecha.FLECHAPIEDRA.getNivel(),Tipoflecha.FLECHAPIEDRA.getDmg(),Tipoflecha.FLECHAFUEGO.getNivel(),Tipoflecha.FLECHAFUEGO.getDmg());
        //Variable Json para trabajar con el archivo de guardado
        Json json = new Json();
        String datos = json.toJson(guardar);
        FileHandle file = Gdx.files.local("partida.json");
        file.writeString(datos, false);
    }

    //Getter
    public static String getFlecha() {
        return flecha;
    }
}
