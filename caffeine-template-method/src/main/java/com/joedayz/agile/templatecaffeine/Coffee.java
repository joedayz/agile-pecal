package com.joedayz.agile.templatecaffeine;

public class Coffee extends CaffeineBeverage {

    @Override
    protected void brew() {
        System.out.println("Filtrar café");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Añadir azúcar y leche");
    }
}
