package com.joedayz.agile.factorypizza;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PizzaStoreFactoryMethodTest {

    @Test
    void ny_store_creates_ny_products() {
        PizzaStore ny = new NYPizzaStore();
        assertInstanceOf(NYStyleCheesePizza.class, ny.createPizza("cheese"));
        assertInstanceOf(NYStylePepperoniPizza.class, ny.createPizza("PEPPERONI"));
    }

    @Test
    void chicago_store_creates_chicago_products() {
        PizzaStore chicago = new ChicagoPizzaStore();
        assertInstanceOf(ChicagoStyleCheesePizza.class, chicago.createPizza("cheese"));
        assertInstanceOf(ChicagoStylePepperoniPizza.class, chicago.createPizza("pepperoni"));
    }
}
