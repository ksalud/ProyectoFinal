package com.gag.game;

import com.gag.game.sprites.Arco;
import com.gag.game.sprites.Items;
import com.gag.game.sprites.Tipoflecha;

public class Guardado {
    private int oro;
    private int madera;
    private int piedra;
    private int sello;
    private int nivelArco;
    private float dps;
    private int nivelMuro;
    private int vida;
    private int nivelFlechaNormal;
    private int dmgFlechaNormal;
    private int nivelFlechaPiedra;
    private int dmgFlechaPiedra;
    private int nivelFlechaFuego;
    private int dmgFlechaFuego;

    public Guardado(){

    }

    public Guardado(int oro, int madera,int piedra, int sello, int nivelArco,float dps, int nivelMuro,int vida, int nivelFlechaNormal,int dmgFlechaNormal,int nivelFlechaPiedra,int dmgFlechaPiedra,int nivelFlechaFuego,int dmgFlechaFuego) {
        this.oro = oro;
        this.madera = madera;
        this.piedra=piedra;
        this.sello = sello;
        this.nivelArco = nivelArco;
        this.dps=dps;
        this.nivelMuro = nivelMuro;
        this.vida=vida;
        this.nivelFlechaNormal = nivelFlechaNormal;
        this.dmgFlechaNormal = dmgFlechaNormal;
        this.nivelFlechaPiedra = nivelFlechaPiedra;
        this.dmgFlechaPiedra = dmgFlechaPiedra;
        this.nivelFlechaFuego = nivelFlechaFuego;
        this.dmgFlechaFuego = dmgFlechaFuego;
    }

    public void colocarDatos(){
        Items.oro=oro;
        Items.madera=madera;
        Items.piedra=piedra;
        Items.sello=sello;
        Arco.setNivelArco(nivelArco);
        Arco.setDps(dps);
        Arco.setNivelMuro(nivelMuro);
        Arco.setVida(vida);
        Tipoflecha.FLECHANORMAL.setNivel(nivelFlechaNormal);
        Tipoflecha.FLECHANORMAL.setDmg(dmgFlechaNormal);
        Tipoflecha.FLECHAPIEDRA.setNivel(nivelFlechaPiedra);
        Tipoflecha.FLECHAPIEDRA.setDmg(dmgFlechaPiedra);
        Tipoflecha.FLECHAFUEGO.setNivel(nivelFlechaFuego);
        Tipoflecha.FLECHAFUEGO.setDmg(dmgFlechaFuego);
    }
}
