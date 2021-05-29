package com.gag.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.gag.game.Juego;

public class Flecha extends Sprite{
    private final Body cuerpo;
    private final TextureRegion aspecto;
    private final Juego juego=(Juego) Gdx.app.getApplicationListener();
    private final Arco arco=new Arco();

    private int dmg;

    public Flecha(World mundo, TextureAtlas atlas, String flechaEquipada) {
        //Recoge textura y daño de flecha de pantalla menu
        super(atlas.findRegion(flechaEquipada));
        if (flechaEquipada.equals("Flecha1")){
            dmg =Tipoflecha.FLECHANORMAL.dmg;
        }else if(flechaEquipada.equals("Flecha2")){
            dmg =Tipoflecha.FLECHAPIEDRA.dmg;
        }else if(flechaEquipada.equals("Flecha3")){
            dmg =Tipoflecha.FLECHAFUEGO.dmg;
        }

        //Cuerpo
        BodyDef defCuerpo= new BodyDef();
        defCuerpo.position.set(0-(arco.getWidth()/3),juego.getAlto()/2.10f);
        defCuerpo.type=BodyDef.BodyType.KinematicBody;
        cuerpo=mundo.createBody(defCuerpo);

        //Componentes dentro del cuerpo
        FixtureDef defComponente= new FixtureDef();
        PolygonShape forma=new PolygonShape();
        forma.setAsBox(4,4);
        defComponente.shape=forma;
        defComponente.friction=0;
        defComponente.density=0;
        defComponente.restitution=0;

        //Sensor
        cuerpo.createFixture(defComponente).setUserData(this);

        //Aspecto
        aspecto=new TextureRegion(getTexture(),
                getRegionX(),getRegionY(),
                128,16);

        setRegion(aspecto);
        setOrigin(getWidth(),getHeight()/2);
        setBounds(defCuerpo.position.x,defCuerpo.position.y,128,16);



    }

    public void actualizar() {
        setPosition(cuerpo.getPosition().x - getOriginX(), cuerpo.getPosition().y - getOriginY() / 2);
    }

    public void colocar(){
        float x=(float)(arco.getWidth()/3);
        float y=(float)(juego.getAlto()/2.10f);
        cuerpo.setTransform(x,y,0);
        cuerpo.setLinearVelocity(0,0);
        cuerpo.setAwake(true);
    }
    //Getters y Setters
    public int getDmg() {
        return dmg;
    }

    public Body getBody() {
        return cuerpo;
    }



}
