package com.joedayz.agile.templatecaffeine;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TemplateMethodBeverageTest {

    @Test
    void coffee_follows_template_order() {
        String out = capture(() -> new Coffee().prepareRecipe());
        int boil = out.indexOf("Hervir agua");
        int brew = out.indexOf("Filtrar café");
        int pour = out.indexOf("Servir en taza");
        int cond = out.indexOf("Añadir azúcar y leche");
        assertTrue(boil < brew && brew < pour && pour < cond);
    }

    @Test
    void tea_follows_template_order() {
        String out = capture(() -> new Tea().prepareRecipe());
        int boil = out.indexOf("Hervir agua");
        int brew = out.indexOf("Infusionar té");
        int pour = out.indexOf("Servir en taza");
        int cond = out.indexOf("Añadir limón");
        assertTrue(boil < brew && brew < pour && pour < cond);
    }

    @Test
    void shared_steps_identical_for_both_beverages() {
        String coffee = capture(() -> new Coffee().prepareRecipe());
        String tea = capture(() -> new Tea().prepareRecipe());
        assertTrue(coffee.contains("Hervir agua") && coffee.contains("Servir en taza"));
        assertTrue(tea.contains("Hervir agua") && tea.contains("Servir en taza"));
    }

    private static String capture(Runnable action) {
        PrintStream prev = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos, true, StandardCharsets.UTF_8));
        try {
            action.run();
            return baos.toString(StandardCharsets.UTF_8);
        } finally {
            System.setOut(prev);
        }
    }
}
