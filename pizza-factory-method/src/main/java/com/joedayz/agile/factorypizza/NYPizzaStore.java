package com.joedayz.agile.factorypizza;

public class NYPizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza(String type) {
        return switch (type.toLowerCase()) {
            case "cheese" -> new NYStyleCheesePizza();
            case "pepperoni" -> new NYStylePepperoniPizza();
            default -> throw new IllegalArgumentException("Tipo no disponible en NY: " + type);
        };
    }
}
