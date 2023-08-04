/**
 * The ReadData class is responsible for reading game data from a file and populating the GameController and PlayerRoster accordingly.
 * It reads the game data from the file "tuctactoe.ser" and updates the player information, including names, scores, and recent game moves.
 * If the file does not exist, it creates a new WriteData instance to handle data writing.
 */
package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import controller.GameController;

public class ReadData {

    private GameController gc;
    private WriteData write;

    /**
     * Constructs a ReadData object with the specified GameController.
     *
     * @param gc The GameController instance associated with this ReadData object.
     */
    public ReadData(GameController gc) {
        this.gc = gc;
    }

    /**
     * Reads game data from the file "tuctactoe.ser" and updates the player information accordingly.
     * If the file does not exist, it creates a new WriteData instance to handle data writing.
     *
     * @throws FileNotFoundException if the file "tuctactoe.ser" is not found.
     */
    public void readFromFile() throws FileNotFoundException {
    	
        File file = new File("tuctactoe.ser");

        if (!file.exists()) {
            setWrite(new WriteData(gc));
            return;
        }

        try (Scanner input = new Scanner(file)) {
            gc.getPlayerRoster().setNumOfPlayers(input.nextInt());

            while (input.hasNext()) {
                for (int a = 0; a < gc.getPlayerRoster().getNumOfPlayers(); a++) {
                    String name = input.next();
                    int ties = input.nextInt();
                    int totalGames = input.nextInt();
                    int wins = input.nextInt();
                    float score = input.nextFloat();
                    gc.getPlayerRoster().getPlayer()[a].setName(name);
                    gc.getPlayerRoster().getPlayer()[a].setTies(ties);
                    gc.getPlayerRoster().getPlayer()[a].setTotalGames(totalGames);
                    gc.getPlayerRoster().getPlayer()[a].setWins(wins);
                    gc.getPlayerRoster().getPlayer()[a].setScore(score);

                    for (int b = 4; b >= 0; b--) {
                        String move = input.next();
                        String result = input.next();
                        gc.getPlayerRoster().getPlayer()[a].setRecentGame(move, result);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    /**
     * Gets the WriteData instance associated with this ReadData object.
     *
     * @return The WriteData instance.
     */
	public WriteData getWrite() {
		return write;
	}

    /**
     * Sets the WriteData instance associated with this ReadData object.
     *
     * @param write The WriteData instance to set.
     */
	public void setWrite(WriteData write) {
		this.write = write;
	}
}