package fr.quatrevieux.araknemu.game.exploration.interaction;

/**
 * Handle exploration player interactions
 */
final public class InteractionHandler {
    private Interaction current;

    /**
     * Check if the player is interacting
     */
    public boolean interacting() {
        return current != null;
    }

    /**
     * Check if the player is busy
     *
     * @todo Add game actions check
     */
    public boolean busy() {
        return interacting();
    }

    /**
     * Stop interaction if player is interacting
     */
    public void stop() {
        if (current != null) {
            current.stop();
            current = null;
        }
    }

    /**
     * Start the interaction
     */
    public void start(Interaction interaction) {
        if (busy()) {
            throw new IllegalStateException("Player is busy");
        }

        current = interaction.start();
    }

    /**
     * Get the current interaction
     *
     * @param interaction The interaction type
     */
    public <T extends Interaction> T get(Class<T> interaction) {
        if (current == null || !interaction.isInstance(current)) {
            throw new IllegalArgumentException("Invalid interaction type");
        }

        return (T) current;
    }

    /**
     * Remove the current interaction
     */
    public Interaction remove() {
        if (current == null) {
            throw new IllegalStateException("No interaction found");
        }

        Interaction interaction = current;
        current = null;

        return interaction;
    }
}
