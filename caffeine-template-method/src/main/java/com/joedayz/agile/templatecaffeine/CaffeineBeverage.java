package com.joedayz.agile.templatecaffeine;

/**
 * Superclase: la receta {@link #prepareRecipe()} está fijada aquí; las subclases solo rellenan los huecos.
 */
public abstract class CaffeineBeverage {

    /**
     * Template method: el algoritmo no se reordena en subclases (por eso es {@code final}).
     */
    public final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        addCondiments();
    }

    protected void boilWater() {
        System.out.println("Hervir agua");
    }

    /** Hueco 1: preparar la infusión (café filtrado vs té). */
    protected abstract void brew();

    protected void pourInCup() {
        System.out.println("Servir en taza");
    }

    /** Hueco 2: condimentos específicos. */
    protected abstract void addCondiments();
}
