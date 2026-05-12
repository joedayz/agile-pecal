package com.joedayz.agile.builderlaptop;

/**
 * Patrón Builder (clásico con builder estático interno): el constructor de {@link Laptop}
 * es privado; la creación pasa por {@link LaptopBuilder} con API fluida (encadenamiento).
 */
public class Laptop {

    private final String cpu;
    private final int ram;
    private final int ssd;
    private final boolean rgbKeyboard;
    private final boolean touchScreen;

    private Laptop(LaptopBuilder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.ssd = builder.ssd;
        this.rgbKeyboard = builder.rgbKeyboard;
        this.touchScreen = builder.touchScreen;
    }

    public String getCpu() {
        return cpu;
    }

    public int getRam() {
        return ram;
    }

    public int getSsd() {
        return ssd;
    }

    public boolean isRgbKeyboard() {
        return rgbKeyboard;
    }

    public boolean isTouchScreen() {
        return touchScreen;
    }

    @Override
    public String toString() {
        return "Laptop{"
                + "cpu='" + cpu + '\''
                + ", ram=" + ram
                + ", ssd=" + ssd
                + ", rgbKeyboard=" + rgbKeyboard
                + ", touchScreen=" + touchScreen
                + '}';
    }

    public static class LaptopBuilder {

        private String cpu;
        private int ram;
        private int ssd;
        private boolean rgbKeyboard;
        private boolean touchScreen;

        public LaptopBuilder setCPU(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public LaptopBuilder setRAM(int ram) {
            this.ram = ram;
            return this;
        }

        public LaptopBuilder setSSD(int ssd) {
            this.ssd = ssd;
            return this;
        }

        public LaptopBuilder addRGBKeyboard() {
            this.rgbKeyboard = true;
            return this;
        }

        public LaptopBuilder addTouchScreen() {
            this.touchScreen = true;
            return this;
        }

        /**
         * Punto único de construcción: aquí puedes validar invariantes antes de devolver el producto.
         */
        public Laptop build() {
            if (cpu == null || cpu.isBlank()) {
                throw new IllegalStateException("CPU obligatoria");
            }
            if (ram <= 0 || ssd <= 0) {
                throw new IllegalStateException("RAM y SSD deben ser positivos");
            }
            return new Laptop(this);
        }
    }
}
