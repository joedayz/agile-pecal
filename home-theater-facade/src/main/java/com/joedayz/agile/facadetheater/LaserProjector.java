package com.joedayz.agile.facadetheater;

/** Subsistema: pantalla y proyector. */
public class LaserProjector {

    public void lowerScreen() {
        System.out.println("Pantalla: bajando lienzo motorizado (ruido de nostalgia de VHS).");
    }

    public void on() {
        System.out.println("Proyector: láser alineado; el gato sale del haz de luz.");
    }

    public void wideCinemaMode() {
        System.out.println("Proyector: relación 2.39:1 — bandas negras, drama instantáneo.");
    }

    public void standby() {
        System.out.println("Proyector: enfriando DMD; fan aún rugiendo 10 s.");
    }

    public void raiseScreen() {
        System.out.println("Pantalla: enrollando hacia el techo.");
    }
}
