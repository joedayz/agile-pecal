package com.joedayz.agile.facadetheater;

/** Subsistema: tocadiscos (solo entra en el flujo vinilo; el cliente no lo toca directamente). */
public class Turntable {

    public void on() {
        System.out.println("Tocadiscos: correa tensada; plato gira a 33⅓ RPM (en tu imaginación, perfecto).");
    }

    public void cueNeedle() {
        System.out.println("Tocadiscos: brazo baja sin drama; clic anti-estático fingido.");
    }

    public void play(String album, String track) {
        System.out.println("Tocadiscos: \"" + track + "\" — lado A de \"" + album + "\".");
    }

    public void liftAndStop() {
        System.out.println("Tocadiscos: aguja arriba; plato frena con inercia melancólica.");
    }

    public void off() {
        System.out.println("Tocadiscos: tapa abajo; guardar el ritual para otro viernes.");
    }
}
