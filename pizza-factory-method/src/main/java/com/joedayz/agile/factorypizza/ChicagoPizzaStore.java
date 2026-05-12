package com.joedayz.agile.factorypizza;

public class ChicagoPizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza(String type) {
        return switch (type.toLowerCase()) {
            case "cheese" -> new ChicagoStyleCheesePizza();
            case "pepperoni" -> new ChicagoStylePepperoniPizza();
            default -> throw new IllegalArgumentException("Tipo no disponible en Chicago: " + type);
        };
    }
}
