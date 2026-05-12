package com.joedayz.agile.decoratorcoffee;

public class DarkRoast extends Beverage {

    @Override
    public String getDescription() {
        return "Tostado oscuro";
    }

    @Override
    public double cost() {
        return 0.99;
    }
}
