package com.joedayz.agile.strategyduck;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DuckRuntimeSwapTest {

    @Test
    void rubberDuck_can_swap_fly_behavior_at_runtime() {
        RubberDuck duck = new RubberDuck();

        duck.setFlyBehavior(new FlyWithWings());

        String out = capture(() -> duck.performFly());
        assertTrue(out.contains("Volando"));
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
