package com.joedayz.agile.strategyduck;

public class MuteQuack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("<< silencio >>");
    }
}
