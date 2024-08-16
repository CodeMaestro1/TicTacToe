package model;

/**
 * Factory class for creating instances of PlayerAction based on player type.
 */
public class PlayerActionFactory {

    /**
     * Enum representing the different player types.
     */
    public enum PlayerName {
        HAL,
        MR_BEAN
    }
    
    private final static String BEST_PLAYER = "Hal";
    private final static String RANDOM_PLAYER = "Mr.Bean";

    /**
     * Creates a PlayerAction instance based on the player name.
     *
     * @param playerName The name of the player (must be one of the values defined in PlayerName enum).
     * @param symbol The symbol used by the player.
     * @param opponentSymbol The symbol used by the opponent.
     * @return The corresponding PlayerAction instance.
     * @throws IllegalArgumentException if the playerName does not match any known type.
     */
    public static PlayerAction createPlayerAction(String playerName, String symbol) {
        switch (playerName) {
            case BEST_PLAYER:
                return new AiPlayerAction(symbol);
            case RANDOM_PLAYER:
                return new MrBeanPlayerAction();
            default:
                throw new IllegalArgumentException("Unknown player name: " + playerName);
        }
    }
}