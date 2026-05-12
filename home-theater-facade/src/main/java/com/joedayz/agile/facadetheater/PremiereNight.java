package com.joedayz.agile.facadetheater;

/**
 * Cliente: solo habla con la Facade (amigo inmediato). No importa cuántos subsistemas hay detrás.
 */
public final class PremiereNight {

    private PremiereNight() {}

    public static void main(String[] args) {
        HomeTheaterFacade theater = new HomeTheaterFacade(
                new TheaterLights(),
                new SurroundAmplifier(),
                new StreamingDeck(),
                new LaserProjector(),
                new PopcornMachine(),
                new Turntable());

        banner();

        theater.playMovie("Patrones de Diseño: La Venganza de los Diagramas UML");
        theater.endMovie();

        System.out.println();
        System.out.println("    ───  Cambio de formato: misma sala, otra operación (Facade distinta)  ───");

        theater.listenToVinyl("Refactoring Blues", "Extract Method en Fa menor");
        theater.endVinylSession();
    }

    private static void banner() {
        System.out.println();
        System.out.println("    ╔══════════════════════════════════════════╗");
        System.out.println("    ║   CINE EN CASA “PEC-AL” — ESTRENO HOY    ║");
        System.out.println("    ║   (Cliente: conoce solo HomeTheaterFacade)║");
        System.out.println("    ╚══════════════════════════════════════════╝");
    }
}
