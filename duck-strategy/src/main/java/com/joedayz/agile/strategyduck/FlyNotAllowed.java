package com.joedayz.agile.strategyduck;

/**
 * “Pato plug-and-play” sin vuelo (comportamiento concreto inyectable).
 */
public class FlyNotAllowed implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("No puede volar.");
    }
}
