package com.joedayz.agile.strategyduck;

/**
 * Simulador mínimo: muestra composición, delegación y cambio de estrategia en tiempo de ejecución.
 */
public final class MiniDuckSimulator {
    private MiniDuckSimulator() {}

    public static void main(String[] args) {
        Duck mallard = new MallardDuck();
        mallard.display();
        mallard.performQuack();
        mallard.performFly();

        System.out.println();
        Duck rubber = new RubberDuck();
        rubber.display();
        rubber.performQuack();
        rubber.performFly();

        System.out.println();
        System.out.println("--- Cambio plug-and-play en runtime ---");
        rubber.setFlyBehavior(new FlyWithWings());
        rubber.performFly();
        rubber.setQuackBehavior(new MuteQuack());
        rubber.performQuack();
    }
}
