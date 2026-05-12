package com.joedayz.agile.builderlaptop;

/**
 * Cliente de demostración: distintas configuraciones sin constructor telescópico ni muchos parámetros.
 */
public final class LaptopShowroom {

    private LaptopShowroom() {}

    public static void main(String[] args) {
        Laptop dev = new Laptop.LaptopBuilder()
                .setCPU("Apple M3 Pro")
                .setRAM(36)
                .setSSD(1024)
                .addTouchScreen()
                .build();

        Laptop gamer = new Laptop.LaptopBuilder()
                .setCPU("AMD Ryzen 9")
                .setRAM(64)
                .setSSD(2048)
                .addRGBKeyboard()
                .build();

        Laptop basic = new Laptop.LaptopBuilder()
                .setCPU("Intel Core i5")
                .setRAM(16)
                .setSSD(512)
                .build();

        System.out.println("--- Configuración desarrollo ---");
        System.out.println(dev);
        System.out.println();
        System.out.println("--- Configuración gaming ---");
        System.out.println(gamer);
        System.out.println();
        System.out.println("--- Configuración oficina ---");
        System.out.println(basic);
    }
}
