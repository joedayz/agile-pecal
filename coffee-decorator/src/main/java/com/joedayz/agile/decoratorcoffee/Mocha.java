package com.joedayz.agile.decoratorcoffee;

public class Mocha extends CondimentDecorator {

    private static final double PRICE = 0.20;

    public Mocha(Beverage wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + ", moca";
    }

    @Override
    public double cost() {
        return wrapped.cost() + PRICE;
    }
}
