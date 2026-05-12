package com.joedayz.agile.facadetheater;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HomeTheaterFacadeTest {

    @Test
    void playMovie_runs_subsystem_steps_without_client_touching_components() {
        String out = capture(() -> {
            HomeTheaterFacade facade = new HomeTheaterFacade(
                    new TheaterLights(),
                    new SurroundAmplifier(),
                    new StreamingDeck(),
                    new LaserProjector(),
                    new PopcornMachine());
            facade.playMovie("Test Película");
        });

        assertTrue(out.contains("FACHADA: playMovie()"));
        assertTrue(out.contains("Palomitas:"));
        assertTrue(out.contains("Luces: atenuando"));
        assertTrue(out.contains("Pantalla: bajando"));
        assertTrue(out.contains("Proyector:"));
        assertTrue(out.contains("Amplificador:"));
        assertTrue(out.contains("Lector:"));
        assertTrue(out.contains("Test Película"));
    }

    @Test
    void endMovie_shuts_down_in_facade_order() {
        String out = capture(() -> {
            HomeTheaterFacade facade = new HomeTheaterFacade(
                    new TheaterLights(),
                    new SurroundAmplifier(),
                    new StreamingDeck(),
                    new LaserProjector(),
                    new PopcornMachine());
            facade.endMovie();
        });

        assertTrue(out.contains("FACHADA: endMovie()"));
        int stop = out.indexOf("Lector: pausa");
        int offDeck = out.indexOf("Lector: apagado");
        int ampOff = out.indexOf("Amplificador: standby");
        assertTrue(stop < offDeck && offDeck < ampOff);
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
