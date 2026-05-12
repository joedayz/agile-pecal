package com.joedayz.agile.templatecaffeine;

public class Tea extends CaffeineBeverage {

    @Override
    protected void brew() {
        System.out.println("Infusionar té");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Añadir limón");
    }
}
