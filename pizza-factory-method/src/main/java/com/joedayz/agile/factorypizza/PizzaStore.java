package com.joedayz.agile.factorypizza;

/**
 * Creador abstracto: define el factory method y aplaza la decisión de qué {@link Pizza} instanciar
 * a {@link NYPizzaStore}, {@link ChicagoPizzaStore}, etc.
 */
public abstract class PizzaStore {

    /** Factory method que cada franquicia implementa según su “familia” de productos. */
    protected abstract Pizza createPizza(String type);

    /**
     * Flujo de alto nivel estable; la parte que varía (qué clase concreta) queda encapsulada
     * en {@link #createPizza(String)}.
     */
    public Pizza orderPizza(String type) {
        Pizza pizza = createPizza(type);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }
}
