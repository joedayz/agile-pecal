package com.joedayz.agile.facadetheater;

/** Subsistema: lector / app de streaming (login, CDN, DRM… aquí simplificado). */
public class StreamingDeck {

    public void on() {
        System.out.println("Lector: arranca el dongle 4K; parpadean LEDs como árbol de Navidad.");
    }

    public void connect(String service) {
        System.out.println("Lector: sesión en \"" + service + "\" (saltando anuncios en el alma).");
    }

    public void playTitle(String title) {
        System.out.println("Lector: buffer mágico — reproduciendo \"" + title + "\".");
    }

    public void stop() {
        System.out.println("Lector: pausa. Aparece el menú con caras que ya viste mil veces.");
    }

    public void off() {
        System.out.println("Lector: apagado suave.");
    }
}
