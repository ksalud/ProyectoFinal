package com.gag.game.sprites;

import com.badlogic.gdx.graphics.Texture;

public enum Tipoflecha {

    FLECHANORMAL(5,1,new Texture("Flecha1.png"),1),
    FLECHAPIEDRA(10,1,new Texture("Flecha2.png"),2),
    FLECHAFUEGO(20,1,new Texture("Flecha3.png"),2);

    int dmg,nivel,coste;
    Texture imagen;

    Tipoflecha(int dmg, int nivel, Texture imagen,int coste){
        this.dmg=dmg;
        this.nivel=nivel;
        this.imagen=imagen;
        this.coste=coste;
    }

    //Funcion de mejora de cada flecha
    public static void mejoraFlechanormal(){
        int costeMadera=FLECHANORMAL.nivel;

        if(Items.madera<costeMadera){

            System.out.println("No tienes recursos");

        }else if(FLECHANORMAL.nivel==10 || FLECHANORMAL.nivel == 20 || FLECHANORMAL.nivel == 30){

            costeMadera=FLECHANORMAL.nivel*2;
            Items.costeRecurso(costeMadera,3);
            FLECHANORMAL.nivel++;
            FLECHANORMAL.dmg+= FLECHANORMAL.dmg+5;
            FLECHANORMAL.coste=FLECHANORMAL.nivel*2;
        }
        else {
            Items.costeRecurso(costeMadera,3);
            FLECHANORMAL.nivel++;
            FLECHANORMAL.dmg+= FLECHANORMAL.dmg*0.75;
            FLECHANORMAL.coste=FLECHANORMAL.nivel;
        }
    }

    public static void mejoraFlechapiedra(){
        int costePiedra=FLECHAPIEDRA.nivel*2;

        if(Items.piedra<costePiedra){

            System.out.println("No tienes recursos");

        }else if(FLECHAPIEDRA.nivel==10 || FLECHAPIEDRA.nivel == 20 || FLECHAPIEDRA.nivel == 30){

            costePiedra=FLECHAPIEDRA.nivel*3;
            Items.costeRecurso(costePiedra,4);
            FLECHAPIEDRA.nivel++;
            FLECHAPIEDRA.dmg+= FLECHAPIEDRA.dmg+10;
            FLECHAPIEDRA.coste=FLECHAPIEDRA.nivel*3;

        }
        else {
            Items.costeRecurso(costePiedra,4);
            FLECHAPIEDRA.nivel++;
            FLECHAPIEDRA.dmg+= FLECHAPIEDRA.dmg*0.75;
            FLECHAPIEDRA.coste=FLECHAPIEDRA.nivel*2;
        }
    }

    public static void mejoraFlechafuego(){
        int costeSello=FLECHAFUEGO.nivel*2;

        if(Items.sello<costeSello){

            System.out.println("No tienes recursos");

        }else if(FLECHAFUEGO.nivel==10 || FLECHAFUEGO.nivel == 20 || FLECHAFUEGO.nivel == 30){

            costeSello=FLECHAFUEGO.nivel*4;
            Items.costeRecurso(costeSello,5);
            FLECHAFUEGO.nivel++;
            FLECHAFUEGO.dmg+= FLECHAFUEGO.dmg+20;
            FLECHAFUEGO.coste=FLECHAFUEGO.nivel*4;
        }
        else {
            Items.costeRecurso(costeSello,5);
            FLECHAFUEGO.nivel++;
            FLECHAFUEGO.dmg+= FLECHAFUEGO.dmg*0.75;
            FLECHAFUEGO.coste=FLECHAFUEGO.nivel*2;
        }
    }

    //Getters y Setters
    public Texture getImagen() {
        return imagen;
    }

    public int getCoste() {
        return coste;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

}

