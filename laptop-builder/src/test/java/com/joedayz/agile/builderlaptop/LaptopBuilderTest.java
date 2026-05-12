package com.joedayz.agile.builderlaptop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LaptopBuilderTest {

    @Test
    void fluent_chain_builds_expected_laptop() {
        Laptop lap = new Laptop.LaptopBuilder()
                .setCPU("Test CPU")
                .setRAM(32)
                .setSSD(500)
                .addRGBKeyboard()
                .addTouchScreen()
                .build();

        assertEquals("Test CPU", lap.getCpu());
        assertEquals(32, lap.getRam());
        assertEquals(500, lap.getSsd());
        assertTrue(lap.isRgbKeyboard());
        assertTrue(lap.isTouchScreen());
    }

    @Test
    void optional_flags_default_false() {
        Laptop lap = new Laptop.LaptopBuilder()
                .setCPU("X")
                .setRAM(8)
                .setSSD(256)
                .build();

        assertFalse(lap.isRgbKeyboard());
        assertFalse(lap.isTouchScreen());
    }

    @Test
    void build_without_cpu_fails() {
        assertThrows(IllegalStateException.class, () ->
                new Laptop.LaptopBuilder()
                        .setRAM(16)
                        .setSSD(512)
                        .build());
    }
}
