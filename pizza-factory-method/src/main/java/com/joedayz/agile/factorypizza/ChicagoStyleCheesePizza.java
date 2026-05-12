package com.joedayz.agile.factorypizza;

public class ChicagoStyleCheesePizza extends Pizza {
    public ChicagoStyleCheesePizza() {
        name = "Pizza de queso estilo Chicago (deep dish)";
    }

    @Override
    public void prepare() {
        System.out.println("Masa gruesa en molde profundo | queso primero | salsa de tomate arriba");
    }

    @Override
    public void cut() {
        System.out.println("Corte en cuadrados (estilo Chicago deep dish)");
    }
}
