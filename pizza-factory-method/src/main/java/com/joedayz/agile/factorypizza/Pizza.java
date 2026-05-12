package com.joedayz.agile.factorypizza;

/**
 * Producto abstracto: la creación concreta la deciden las subclases de {@link PizzaStore}.
 */
public abstract class Pizza {
    protected String name;

    public String getName() {
        return name;
    }

    public void prepare() {
        System.out.println("Preparando " + name);
    }

    public void bake() {
        System.out.println("Horneando 25 minutos a 175°C");
    }

    public void cut() {
        System.out.println("Cortando en triángulos");
    }

    public void box() {
        System.out.println("Empaquetando en caja oficial de la franquicia");
    }
}
