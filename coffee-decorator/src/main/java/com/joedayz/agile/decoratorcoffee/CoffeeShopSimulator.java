package com.joedayz.agile.decoratorcoffee;

/**
 * Ejecuta ejemplo del patrón Decorator: mismas combinaciones sin subclases por cada mezcla.
 */
public final class CoffeeShopSimulator {

    private CoffeeShopSimulator() {}

    public static void main(String[] args) {
        Beverage cup1 = new HouseBlend();
        cup1 = new Milk(cup1);
        cup1 = new Soy(cup1);
        printOrder(cup1);

        Beverage cup2 = new Espresso();
        cup2 = new Mocha(cup2);
        cup2 = new Mocha(cup2);
        cup2 = new Whip(cup2);
        printOrder(cup2);

        Beverage cup3 = new DarkRoast();
        cup3 = new Soy(cup3);
        cup3 = new Mocha(cup3);
        cup3 = new Whip(cup3);
        printOrder(cup3);


    }

    private static void printOrder(Beverage drink) {
        System.out.printf("%s — %.2f €%n", drink.getDescription(), drink.cost());
    }
}
