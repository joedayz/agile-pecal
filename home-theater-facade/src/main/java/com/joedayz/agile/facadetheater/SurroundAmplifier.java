package com.joedayz.agile.facadetheater;

/** Subsistema: audio multicanal. */
public class SurroundAmplifier {

    public void on() {
        System.out.println("Amplificador: arranque en frío, relés con un clic satisfactorio.");
    }

    public void setSurroundMode(String codec) {
        System.out.println("Amplificador: modo envolvente — " + codec + ".");
    }

    public void setVolume(int level) {
        System.out.println("Amplificador: volumen a " + level + "/100 (vecinos en alerta amarilla).");
    }

    public void off() {
        System.out.println("Amplificador: standby. El subwoofer exhala un último suspiro.");
    }
}
