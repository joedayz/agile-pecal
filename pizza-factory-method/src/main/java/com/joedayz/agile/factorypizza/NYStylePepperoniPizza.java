package com.joedayz.agile.factorypizza;

public class NYStylePepperoniPizza extends Pizza {
    public NYStylePepperoniPizza() {
        name = "Pizza pepperoni estilo Nueva York";
    }

    @Override
    public void prepare() {
        System.out.println("Masa fina crujiente | salsa liviana | mozzarella | pepperoni en rodajas");
    }

    @Override
    public void cut() {
        System.out.println("Corte en porciones anchas (estilo NY)");
    }
}
