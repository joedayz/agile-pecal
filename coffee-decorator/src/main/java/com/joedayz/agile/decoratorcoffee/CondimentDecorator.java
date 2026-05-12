package com.joedayz.agile.decoratorcoffee;

/**
 * Decorador base: todos los extras envuelven a otra {@link Beverage}.
 */
public abstract class CondimentDecorator extends Beverage {

    protected final Beverage wrapped;

    protected CondimentDecorator(Beverage wrapped) {
        this.wrapped = wrapped;
    }
}
