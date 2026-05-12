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

    public HomeTheaterFacade(
            TheaterLights lights,
            SurroundAmplifier amp,
            StreamingDeck deck,
            LaserProjector projector,
            PopcornMachine popcorn) {
        this.lights = lights;
        this.amp = amp;
        this.deck = deck;
        this.projector = projector;
        this.popcorn = popcorn;
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
}
