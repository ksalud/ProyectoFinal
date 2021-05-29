package com.gag.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.gag.game.Juego;
import com.gag.game.pantallas.PantallaAccion;

import java.util.Random;

public class Enemigo extends Sprite {
    private final Juego juego;
    public static final int ATAQUE=10;
    public static final int ALTO=156;
    public static final int ANCHO=128;
    public static final int VIDA=20;
    public int velocidadEnemigo =-5000;
    private int dmg;
    private int mejoraVida=25;
    private int vida;
    protected Body cuerpo;
    protected TextureRegion aspecto;
    protected Animation<TextureRegion> animacion;
    private float lapso;
    private boolean atacando;
    private float tiempo;


    public Enemigo(World mundo, TextureAtlas atlas) {
        super(atlas.findRegion("Fantasma"));
        lapso=0;
        dmg=ATAQUE;
        vida=VIDA;
        this.juego=(Juego) Gdx.app.getApplicationListener();

        //Cuerpo físico
        aspecto=new TextureRegion(getTexture(),
                getRegionX(),getRegionY(),
                ANCHO,ALTO);
        Array<TextureRegion> fotogramas=new Array<>();
        for(int i=1;i<8;i++){
            fotogramas.add(new TextureRegion(getTexture(),getRegionX()+ANCHO*i,getRegionY(),ANCHO,ALTO));
        }
        animacion=new Animation<>(0.1f,fotogramas);

        //Cuerpo
        BodyDef defCuerpo= new BodyDef();
        defCuerpo.position.set(posicionAleatoria());
        defCuerpo.type=BodyDef.BodyType.DynamicBody;
        cuerpo=mundo.createBody(defCuerpo);

        //Componentes dentro del cuerpo
        FixtureDef defComponente= new FixtureDef();
        CircleShape forma=new CircleShape();
        forma.setRadius(60f);
        defComponente.shape=forma;
        defComponente.friction=0;
        defComponente.density=0;
        defComponente.restitution=0;

        //Sensor
        defComponente.isSensor=true;
        cuerpo.createFixture(defComponente).setUserData(this);

        //Dar un aspecto
        setRegion(aspecto);
        setBounds(defCuerpo.position.x,defCuerpo.position.y,128,156);
        setOrigin(getWidth()/2,getHeight()/2);
        atacando=false;
        tiempo=0;

    }

    public void actualizar(float delta){
        setPosition(cuerpo.getPosition().x-getOriginX(),cuerpo.getPosition().y-getOriginY());
        cuerpo.setLinearVelocity(velocidadEnemigo *delta,0);
        setRegion(decidirAspecto(delta));

        if(atacando){
            tiempo+=delta;
            if(tiempo>=5) {
                atacar(PantallaAccion.arco);
                tiempo=0;
            }
        }

    }

    public TextureRegion decidirAspecto(float delta){
        aspecto=animacion.getKeyFrame(lapso,true);
        lapso+=delta;
        return aspecto;
    }

    public Vector2 posicionAleatoria(){
        Random random = new Random();
        int altoCentrado=random.nextInt((juego.getAlto()-100)- 100) + 100;
        float altoAleatoria = (float) altoCentrado;
        int anchoCentrado= random.nextInt((juego.getAncho()+500)- juego.getAncho()+30) + juego.getAncho()+30;
        float anchoAleatorio =(float) anchoCentrado;


        return new Vector2(anchoAleatorio,altoAleatoria);
    }

    public void atacar(Arco arco){
        arco.setHealth(arco.getHealth()- dmg);
    }

    public  void colocar(){
        float x=(float)(juego.getAncho()+(Math.random()*juego.getAncho()));
        float y=(float)(100+Math.random()*juego.getAlto()-100);
        cuerpo.setTransform(x,y,0);
        cuerpo.setAwake(true);
        this.setVida(VIDA);
        this.setVida(VIDA+mejoraVida);
        mejoraVida+=25;
        drop();
    }

    public void drop(){
        Items.oro+=100;
        Items.madera+=6;
        Items.piedra+=4;
        Items.sello+=3;
    }

    //Getters y Setters
    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public Body getCuerpo() {
        return cuerpo;
    }

    public void setAtacando(boolean atacando) {
        this.atacando = atacando;
    }

    public void setVelocidadEnemigo(int velocidadEnemigo) {
        this.velocidadEnemigo = velocidadEnemigo;
    }

}
