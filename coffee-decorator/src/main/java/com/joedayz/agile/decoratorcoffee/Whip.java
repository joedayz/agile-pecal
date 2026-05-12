package com.joedayz.agile.decoratorcoffee;

public class Whip extends CondimentDecorator {

    private static final double PRICE = 0.10;

    public Whip(Beverage wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + ", crema batida";
    }

    @Override
    public double cost() {
        return wrapped.cost() + PRICE;
    }
}
