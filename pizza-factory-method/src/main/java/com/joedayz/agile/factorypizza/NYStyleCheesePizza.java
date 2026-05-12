package com.joedayz.agile.factorypizza;

public class NYStyleCheesePizza extends Pizza {
    public NYStyleCheesePizza() {
        name = "Pizza de queso estilo Nueva York";
    }

    @Override
    public void prepare() {
        System.out.println("Masa fina | salsa de tomate marinara | mozzarella | orégano");
    }

    @Override
    public void cut() {
        System.out.println("Corte en porciones anchas (estilo NY)");
    }
}
