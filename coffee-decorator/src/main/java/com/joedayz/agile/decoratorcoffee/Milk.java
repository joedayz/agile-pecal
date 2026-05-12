package com.joedayz.agile.decoratorcoffee;

public class Milk extends CondimentDecorator {

    private static final double PRICE = 0.10;

    public Milk(Beverage wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + ", leche";
    }

    @Override
    public double cost() {
        return wrapped.cost() + PRICE;
    }
}
