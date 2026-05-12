package com.joedayz.agile.factorypizza;

public class ChicagoStylePepperoniPizza extends Pizza {
    public ChicagoStylePepperoniPizza() {
        name = "Pizza pepperoni estilo Chicago (deep dish)";
    }

    @Override
    public void prepare() {
        System.out.println("Masa alta en molde | capas generosas de queso | pepperoni | salsa encima");
    }

    @Override
    public void cut() {
        System.out.println("Corte en cuadrados (estilo Chicago deep dish)");
    }
}
