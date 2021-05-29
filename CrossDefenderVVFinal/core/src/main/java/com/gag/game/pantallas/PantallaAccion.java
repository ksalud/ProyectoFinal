package com.gag.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gag.game.sprites.Arco;
import com.gag.game.sprites.Enemigo;
import com.gag.game.sprites.Flecha;
import com.gag.game.sprites.Items;

import java.util.ArrayList;

public class PantallaAccion extends Pantalla{
    //Enemigo variables
    private final Stage stage;
    private static int numEnemigos = 8;
    private final int enemigoAniadidos = 1;
    private int cont = 0;

    private final Texture fondo;
    private final Texture muro;
    private Texture muroRoto;
    private final TextureAtlas atlasFlecha=am.get("Flechas.atlas");
    public static Arco arco;
    private float tiempo,deltaTime;
    private float tiempoEnemigo;
    private float yInput;
    private float xInput;
    private final OrthographicCamera camara;
    private final FitViewport view;
    private float angle;
    private final Rectangle areaMuro;
    private final Vector2 posicionMuro;
    private Vector2 puntoClickeado;
    private final Vector2 posicionArco;
    private final Vector2 posicionFlecha;
    private Vector2 click;

    //Label
    private Label vidaMuro;
    BitmapFont fuente=am.get("fuente.fnt");
    Label.LabelStyle estilo=new Label.LabelStyle(fuente, Color.RED);

    //Fisicas
    private final World mundo;
    //private final Box2DDebugRenderer depurador;
    private final TextureAtlas atlasEnemigo;
    private Flecha flecha;
    private final ArrayList<Enemigo> enemigos;
    private final ArrayList<Body> cuerpoEnemigo;
    private final ArrayList<Enemigo> condenados;
    private final ArrayList<Flecha> municion;
    private final ArrayList<Body> cuerpoFlechas;
    private final ArrayList<Flecha> flechaImpactada;



    public PantallaAccion() {
        super();
        //Stage
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        //camara y view
        camara=new OrthographicCamera();
        camara.position.x=juego.getAncho()/2f;
        camara.position.y=juego.getAlto()/2f;
        view=new FitViewport(juego.getAncho(), juego.getAlto(),camara);
        view.setScreenBounds(0,0, juego.getAncho(), juego.getAlto());

        fondo=am.get("Fondo.jpg");
        muro=am.get("devil_wall.jpg");
        arco=new Arco();
        posicionArco= new Vector2(0-(arco.getWidth()/3),juego.getAlto()/2.5f);
        posicionFlecha= new Vector2(posicionArco.x,posicionArco.y);

        //Area de colision para el muro
        posicionMuro=new Vector2(juego.getAncho()/7f, juego.getAlto());
        areaMuro=new Rectangle((int)posicionMuro.x,(int)posicionMuro.y,muro.getWidth(),muro.getHeight());

        //Fisicas
        mundo=new World(new Vector2(0,0),true);
        //depurador=new Box2DDebugRenderer();

        //Creacion enemigos
        atlasEnemigo=am.get("FantasmaRun.atlas");
        enemigos = new ArrayList<>();
        cuerpoEnemigo = new ArrayList<>();
        for (int i = 0;i<numEnemigos;i++) {
            enemigos.add(new Enemigo(mundo, atlasEnemigo));
            cuerpoEnemigo.add(enemigos.get(i).getCuerpo());
        }
        //Flecha
        municion=new ArrayList<>();
        cuerpoFlechas = new ArrayList<>();
        for (int i = 0;i<100;i++) {
            municion.add(new Flecha(mundo, atlasFlecha,PantallaMenu.getFlecha()));
            cuerpoFlechas.add(municion.get(i).getBody());
        }
        flechaImpactada=new ArrayList<>();

        tiempo=0;

        //Muro
        BodyDef bd = new BodyDef();
        bd.position.set(juego.getAncho()/40f,juego.getAlto());
        bd.type= BodyDef.BodyType.StaticBody;
        PolygonShape shape= new PolygonShape();
        shape.setAsBox(juego.getAncho()/7f,juego.getAlto());
        FixtureDef fixDef= new FixtureDef();
        fixDef.shape= shape;
        Body body= mundo.createBody(bd);
        body.createFixture(fixDef).setUserData("muro");
        vidaMuro=new Label(String.valueOf(Arco.getHealth()),estilo);
        vidaMuro.setPosition(10,10);
        vidaMuro.setFontScale(3,3);

        //EnemigosCondenados
        condenados =new ArrayList<>();


        mundo.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture compA= contact.getFixtureA();
                Fixture compB= contact.getFixtureB();
                //Colisión Enemigo-Muro
                if(compA.getUserData().equals("muro") &&
                        (compB.getUserData() instanceof Enemigo)){
                    ((Enemigo)compB.getUserData()).setVelocidadEnemigo(0);
                    ((Enemigo)compB.getUserData()).setAtacando(true);
                }else if(compB.getUserData().equals("muro") &&
                        (compA.getUserData() instanceof Enemigo)){
                    ((Enemigo)compA.getUserData()).setVelocidadEnemigo(0);
                    ((Enemigo)compB.getUserData()).setAtacando(true);
                }

                //Colisión Flecha-Enemigo
                if(compA.getUserData() instanceof Flecha &&
                        (compB.getUserData() instanceof Enemigo)){
                    ((Enemigo)compB.getUserData()).setVida(((Enemigo)compB.getUserData()).getVida()-((Flecha)compA.getUserData()).getDmg());
                    if(((Enemigo)compB.getUserData()).getVida()<=0){
                        condenados.add(((Enemigo) compB.getUserData()));
                    }
                    flechaImpactada.add((Flecha) compA.getUserData());
                }else if(compB.getUserData() instanceof Flecha &&
                        (compA.getUserData() instanceof Enemigo)){
                    ((Enemigo)compA.getUserData()).setVida(((Enemigo)compA.getUserData()).getVida()-((Flecha)compB.getUserData()).getDmg());
                    if(((Enemigo)compA.getUserData()).getVida()<=0){
                        condenados.add(((Enemigo) compA.getUserData()));
                    }
                    flechaImpactada.add((Flecha) compB.getUserData());
                }

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        //Actor
        stage.addActor(vidaMuro);
    }

    @Override
    public void leerEntrada(float delta) {
        if(Gdx.input.isTouched()) {

            if(cont>=100) cont=0;
            click = juego.getVista().unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

            while(tiempo >=Arco.getDps()) {
                flecha= municion.get(cont);
                flecha.setRotation(angle);
                flecha.getBody().setLinearVelocity(click.x-flecha.getBody().getPosition().x,click.y-flecha.getBody().getPosition().y);
                flecha.actualizar();
                cont++;
                tiempo-=Arco.getDps();
            }


        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            //Antes de salir se limpia y se vuelven a crear 8 enemigos porque sino limpias te acomula enemigos
            enemigos.clear();
            numEnemigos=8;
            for(int i =0;i<numEnemigos;i++){
                enemigos.add(new Enemigo(mundo,atlasEnemigo));
            }
            juego.cambiarPantalla(this,new PantallaGameOver());

        }
    }

    @Override
    public void actualizar(float delta) {
        //Actualizacion de mundo y enemigo
        tiempoEnemigo+=delta;
        if(tiempoEnemigo>=10){
            generaEnemigos(mundo,atlasEnemigo);
            tiempoEnemigo=0;
        }
        for(int i=0;i<numEnemigos;i++) {
            enemigos.get(i).actualizar(delta);
        }

        tiempo += delta;
        yInput = Gdx.input.getY()+arco.getHeight()/2;
        xInput = Gdx.input.getX()+arco.getWidth()/2;
        arco.setPosicionArco(posicionArco);
        arco.setPosition(posicionArco.x,posicionArco.y);

        for(Flecha f:municion){
            f.actualizar();
        }

        //Tiempo

        deltaTime=Gdx.graphics.getDeltaTime();
        tiempo+=1*deltaTime;

        //Pos arco

        puntoClickeado=view.unproject(new Vector2(xInput,yInput));
        angle = MathUtils.radiansToDegrees * MathUtils.atan2(puntoClickeado.y - arco.getPosicionArco().y, puntoClickeado.x - arco.getPosicionArco().x);

        if(angle < 0){
            angle += 360;
        }

        if (Arco.getHealth()<=0){
            //Antes de salir se limpia y se vuelven a crear 8 enemigos porque sino limpias te acomula enemigos
            enemigos.clear();
            numEnemigos=8;
            for(int i =0;i<numEnemigos;i++){
                enemigos.add(new Enemigo(mundo,atlasEnemigo));
            }
            juego.cambiarPantalla(this,new PantallaGameOver());
        }
        vidaMuro.setText(String.valueOf(Arco.getHealth()));

    }

    @Override
    public void dibujar(float delta) {
        camara.update();
        sb.enableBlending();
        sb.setProjectionMatrix(camara.combined);

        view.apply();
        sb.setProjectionMatrix(camara.combined);
        sb.begin();
        sb.draw(fondo,0,0, juego.getAncho(), juego.getAlto());
        sb.draw(muro,0,0, juego.getAncho()/7f, juego.getAlto());

        if(arco.getHealth()<=Arco.getVida()/1.25) {
            muroRoto = am.get("muroroto1.png");
            sb.draw(muroRoto, 0, 0, juego.getAncho() / 7f, juego.getAlto());

            if(arco.getHealth()<=Arco.getVida()/4f){
                muroRoto=am.get("muroroto2.png");
                sb.draw(muroRoto,0,0,juego.getAncho()/7f, juego.getAlto());
            }
        }

        for(int i=0;i<numEnemigos;i++) {
            enemigos.get(i).draw(sb);
        }

        for(Flecha f:municion) {
            f.draw(sb);
        }

        arco.setRotation(angle);
        arco.draw(sb);
        sb.disableBlending();

        camara.update();
        //depurador.render(mundo, camara.combined);

        sb.end();

        // mundo
        mundo.step(delta,8,6);

        //Limpiar arrays
        for(Flecha f:flechaImpactada){
            f.colocar();
        }
        flechaImpactada.clear();

        for(Enemigo e: condenados){
            e.colocar();
        }
        condenados.clear();
    }
    public void generaEnemigos(World mundo,TextureAtlas atlasEnemigo){
        for (int i = 0;i<enemigoAniadidos;i++) {
            enemigos.add(new Enemigo(mundo, atlasEnemigo));
            cuerpoEnemigo.add(enemigos.get(i).getCuerpo());
        }
        numEnemigos+=enemigoAniadidos;
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

    @Override
    public void render(float delta) {
        //El render hace falta para poder usar los stage y
        super.render(delta);
        stage.act();
        stage.draw();

    }
}
