package com.joedayz.agile.templatecaffeine;

/**
 * Cliente: usa la misma operación de alto nivel; el polimorfismo rellena los pasos variables.
 */
public final class BaristaSimulator {

    private BaristaSimulator() {}

    public static void main(String[] args) {
        System.out.println("--- Café ---");
        CaffeineBeverage cup1 = new Coffee();
        cup1.prepareRecipe();

        System.out.println();
        System.out.println("--- Té ---");
        CaffeineBeverage cup2 = new Tea();
        cup2.prepareRecipe();
    }
}
