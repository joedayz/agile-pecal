package com.joedayz.agile.factorypizza;

public final class PizzaStoreSimulator {
    private PizzaStoreSimulator() {}

    public static void main(String[] args) {
        PizzaStore ny = new NYPizzaStore();
        PizzaStore chicago = new ChicagoPizzaStore();

        System.out.println("=== Pedido en Nueva York ===");
        Pizza nyPizza = ny.orderPizza("pepperoni");
        System.out.println("Cliente recibe: " + nyPizza.getName());
        System.out.println();

        System.out.println("=== Pedido en Chicago ===");
        Pizza chiPizza = chicago.orderPizza("cheese");
        System.out.println("Cliente recibe: " + chiPizza.getName());
    }
}
