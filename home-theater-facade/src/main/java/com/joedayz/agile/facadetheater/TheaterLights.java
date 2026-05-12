package com.joedayz.agile.facadetheater;

/** Subsistema: iluminación (el cliente no debería pelearse con cada circuito). */
public class TheaterLights {

    public void on() {
        System.out.println("Luces: circuito general encendido.");
    }

    public void dimTo(int percent) {
        System.out.println("Luces: atenuando a " + percent + "% (modo cine).");
    }

    public void fullBright() {
        System.out.println("Luces: vuelta a brillo normal.");
    }
}
