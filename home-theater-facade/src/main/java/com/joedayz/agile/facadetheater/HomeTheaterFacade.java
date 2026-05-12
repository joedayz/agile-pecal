package com.joedayz.agile.facadetheater;

/**
 * Facade: una interfaz unificada al subsistema. El cliente no encadena cinco objetos
 * ni conoce el orden correcto — principio de menor conocimiento (Ley de Demeter).
 */
public class HomeTheaterFacade {

    private final TheaterLights lights;
    private final SurroundAmplifier amp;
    private final StreamingDeck deck;
    private final LaserProjector projector;
    private final PopcornMachine popcorn;
    private final Turntable turntable;

    public HomeTheaterFacade(
            TheaterLights lights,
            SurroundAmplifier amp,
            StreamingDeck deck,
            LaserProjector projector,
            PopcornMachine popcorn,
            Turntable turntable) {
        this.lights = lights;
        this.amp = amp;
        this.deck = deck;
        this.projector = projector;
        this.popcorn = popcorn;
        this.turntable = turntable;
    }

    /** Un solo “Play” para el usuario: detrás, todos los engranajes. */
    public void playMovie(String title) {
        System.out.println();
        System.out.println("═══  FACHADA: playMovie() — el botón verde  ═══");
        popcorn.warmUp();
        popcorn.pop("mantequilla clarificada y un pizca de sal marina");

        lights.on();
        lights.dimTo(12);

        projector.lowerScreen();
        projector.on();
        projector.wideCinemaMode();

        amp.on();
        amp.setSurroundMode("Dolby Atmos — demo imaginaria");
        amp.setVolume(42);

        deck.on();
        deck.connect("StreamZona+");
        deck.playTitle(title);
        System.out.println();
        System.out.println("… silencio en la sala. Solo queda el rugido del subwoofer y la culpa del spoiler.");
    }

    public void endMovie() {
        System.out.println();
        System.out.println("═══  FACHADA: endMovie() — cierre orquestado  ═══");
        deck.stop();
        deck.off();
        amp.off();
        projector.standby();
        projector.raiseScreen();
        lights.fullBright();
        popcorn.coolDown();
        System.out.println();
        System.out.println("Fin. El gato vuelve a ser el protagonista de la habitación.");
    }

    /**
     * Otro flujo de alto nivel: misma Facade, distinto subsistema activo (sin pantalla ni streaming).
     */
    public void listenToVinyl(String album, String track) {
        System.out.println();
        System.out.println("═══  FACHADA: listenToVinyl() — sala de escucha  ═══");
        lights.on();
        lights.dimTo(28);

        amp.on();
        amp.setStereoDirect();
        amp.setVolume(24);

        turntable.on();
        turntable.cueNeedle();
        turntable.play(album, track);
        System.out.println();
        System.out.println("… sin explosiones en pantalla: solo surcos, polvo estelar y culpa de no limpiar el disco.");
    }

    public void endVinylSession() {
        System.out.println();
        System.out.println("═══  FACHADA: endVinylSession() — cierre minimalista  ═══");
        turntable.liftAndStop();
        turntable.off();
        amp.off();
        lights.fullBright();
        System.out.println();
        System.out.println("Fin del vinilo. El silencio también tiene compresión dinámica.");
    }
}
