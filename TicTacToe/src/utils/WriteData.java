/**
 * The WriteData class is responsible for writing game data to a file "tuctactoe.ser".
 * It takes the GameController instance and writes player information, including names, scores, and recent game moves, to the file.
 */
package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import controller.GameController;

public class WriteData {

    private static final String FILE_NAME = "tuctactoe.ser";

    private final GameController gameController;

    /**
     * Constructs a WriteData object with the specified GameController.
     *
     * @param gameController The GameController instance associated with this WriteData object.
     */
    public WriteData(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Writes game data to the file "tuctactoe.ser" based on the information from the GameController.
     * The method writes the number of players, player names, scores, and recent game moves to the file.
     *
     * @throws IOException if an error occurs while writing data to the file.
     */
    public void writeData() throws IOException {
        File file = new File(FILE_NAME);

        try (FileWriter writer = new FileWriter(file)) {
        	
            writer.write(gameController.getPlayerRoster().getNumOfPlayers() + "\n");

            for (int g = 0; g < gameController.getPlayerRoster().getNumOfPlayers(); g++) {
                writer.write(gameController.getPlayerRoster().getPlayer()[g].getName() + " " +
                        gameController.getPlayerRoster().getPlayer()[g].getTies() + " " +
                        gameController.getPlayerRoster().getPlayer()[g].getTotalGames() + " " +
                        gameController.getPlayerRoster().getPlayer()[g].getWins() + " " +
                        gameController.getPlayerRoster().getPlayer()[g].getScore() + "\n");

                for (int a = 4; a >= 0; a--) {
                    writer.write(gameController.getPlayerRoster().getPlayer()[g].getRecentGame(a, 0) + " " +
                            gameController.getPlayerRoster().getPlayer()[g].getRecentGame(a, 1) + "\n");
                }

                writer.write("\n");
            }
        }
    }
}