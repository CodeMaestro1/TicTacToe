package model;

/**
 * Factory class for creating instances of PlayerAction based on player type.
 */
public class PlayerActionFactory {
    
    private static final String BEST_PLAYER = "Hal";
    private static final String RANDOM_PLAYER = "Mr.Bean";

    /**
     * Creates a PlayerAction instance based on the player name.
     *
     * @param playerName The name of the player (must be one of the values defined in PlayerName enum).
     * @param symbol The symbol used by the player.
     * @param opponentSymbol The symbol used by the opponent.
     * @return The corresponding PlayerAction instance.
     * @throws IllegalArgumentException if the playerName does not match any known type.
     */
    public static Player createPlayerAction(String playerName, String symbol) {
        switch (playerName) {
            case BEST_PLAYER:
                return new AiPlayer(BEST_PLAYER, symbol);
            case RANDOM_PLAYER:
                return new RandomPlayer();
            default:
                throw new IllegalArgumentException("Unknown player name: " + playerName);
        }
    }
}