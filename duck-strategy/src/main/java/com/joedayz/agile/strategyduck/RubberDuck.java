package com.joedayz.agile.strategyduck;

public class RubberDuck extends Duck {
    public RubberDuck() {
        flyBehavior = new FlyNotAllowed();
        quackBehavior = new Squeak();
    }

    @Override
    public void display() {
        System.out.println("Soy un pato de goma.");
    }
}
