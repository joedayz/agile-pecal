package com.joedayz.agile.decoratorcoffee;

public class Soy extends CondimentDecorator {

    private static final double PRICE = 0.15;

    public Soy(Beverage wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + ", soja";
    }

    @Override
    public double cost() {
        return wrapped.cost() + PRICE;
    }
}
