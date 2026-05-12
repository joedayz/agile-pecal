package com.joedayz.agile.facadetheater;

/** Subsistema extra: olor a cine (el Facade puede orquestarlo sin que el invitado toque válvulas). */
public class PopcornMachine {

    public void warmUp() {
        System.out.println("Palomitas: resistencia al rojo; el aire huele a viernes.");
    }

    public void pop(String flavorNote) {
        System.out.println("Palomitas: ¡POP! Tanda lista (" + flavorNote + ").");
    }

    public void coolDown() {
        System.out.println("Palomitas: apagado; queda un grano rebelde en el fondo.");
    }
}
