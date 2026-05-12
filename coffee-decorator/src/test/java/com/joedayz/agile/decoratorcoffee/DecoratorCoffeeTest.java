package com.joedayz.agile.decoratorcoffee;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecoratorCoffeeTest {

    @Test
    void house_blend_milk_and_soy_sums_prices_and_builds_description() {
        Beverage drink = new Soy(new Milk(new HouseBlend()));
        assertEquals("House Blend, leche, soja", drink.getDescription());
        assertEquals(1.14, drink.cost(), 0.001);
    }

    @Test
    void double_mocha_espresso_matches_expected_total() {
        Beverage drink = new Whip(new Mocha(new Mocha(new Espresso())));
        assertEquals(2.49, drink.cost(), 0.001);
    }
}
