package controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.GameModel;
import model.Player;
import model.PlayerRoster;
import model.TicTacToeTutorial;
import utils.ReadData;
import utils.WriteData;
import view.GameBoard;
import view.MainWindow;
import view.PlayerPanel;

/**
 * The GameController class controls the flow of the game,
 * handling user interactions and updating the model and view accordingly.
 */
public class GameController {
	
	private final MainWindow main;
	private final GameModel model;
	private final PlayerRoster playerRoster;
	private final GameBoard gameBoard;
	private final WriteData writeData;
	private final ReadData readData;

	/**
	 * Constructs a GameController object and initializes all the necessary components.
	 * @throws IOException if an error occurs while reading data.
	 */
	public GameController() throws IOException {
		this.playerRoster = new PlayerRoster();
		this.main = new MainWindow(this);
		this.gameBoard = new GameBoard(this, main);
		this.readData = new ReadData(this);
		this.writeData = new WriteData(this);
		this.model = new GameModel(this);
	}

	/**
	 * Writes the game data and quits the game.
	 * @throws IOException if an error oïccurs while writing data.
	 */
	public void quit() throws IOException {
		writeData.writeData();
		System.exit(1);
	}

	/**
	 * Starts the game by making the main window visible.
	 */
	public void start() {
		main.setVisible(true);
	}

    /**
     * Starts a new game by initializing the game state and enabling the necessary buttons and controls.
     */
	public void startGame() {
		
		Player leftPlayer = main.getLeftPlayer().getPlayer();
	    Player rightPlayer = main.getRightPlayer().getPlayer();

	    // Check if both players are different
	    if (leftPlayer.equals(rightPlayer)) {
	        main.printSamePlayerError();
	        return;
	    }
		
		model.setInGame(true);
		model.setMover(0);
		startGameButtons();
	}

    /**
     * Quits the current game and returns to the main menu by resetting the game state,
     * enabling the necessary buttons, and refreshing the UI components.
     */
	public void quitGame() {
		PlayerPanel leftPlayer = main.getLeftPlayer();
		PlayerPanel rightPlayer = main.getRightPlayer();
		
		quitGameButtons(leftPlayer, rightPlayer);
		model.resetBoard();
		main.getMainAreaPanel().remove(gameBoard);
		main.getMainAreaPanel().getHallOfFame().refresh();
		leftPlayer.refresh();
		rightPlayer.refresh();
		main.getMainAreaPanel().add(main.getMainAreaPanel().getHallOfFame());
	}

    /**
     * Enables the game-related buttons and controls.
     */
	private void startGameButtons() {
		Player leftPlayer = main.getLeftPlayer().getPlayer();
		Player rightPlayer = main.getRightPlayer().getPlayer();
		
		leftPlayer.setRecentGame(leftPlayer.getName(), rightPlayer.getName());
		rightPlayer.setRecentGame(leftPlayer.getName(), rightPlayer.getName());
		
		main.getBannerPanel().getStartGameButton().setEnabled(false);
		main.getMainAreaPanel().remove(this.getMain().getMainAreaPanel().getHallOfFame());
		main.getMainAreaPanel().add(gameBoard);
		main.getBannerPanel().getAddPlayerButton().setEnabled(false);
		main.getBannerPanel().getDoneButton().setEnabled(true);
		main.getLeftPlayer().getPlayerSelectButton().setEnabled(false);
		main.getRightPlayer().getPlayerSelectButton().setEnabled(false);
		main.getBannerPanel().getDoneButton().setEnabled(false);
		main.getBannerPanel().getDeletePlayerButton().setEnabled(false);
	}

    /**
     * Disables the game-related buttons and controls.
     */
	private void quitGameButtons(PlayerPanel leftPlayer, PlayerPanel rightPlayer) {
		model.setInGame(false);
		main.getBannerPanel().getStartGameButton().setEnabled(true);
		main.getBannerPanel().getDoneButton().setEnabled(false);
		main.getBannerPanel().getAddPlayerButton().setEnabled(true);
		leftPlayer.getPlayerSelectButton().setEnabled(true);
		rightPlayer.getPlayerSelectButton().setEnabled(true);
		main.getBannerPanel().getDeletePlayerButton().setEnabled(true);
	}
	

	/**
	 * Prompts the user to enter a player's name and adds the player to the player roster if the name is valid and not already in the roster.
	 * 
	 * If the user cancels the input or closes the input dialog, the method terminates without any further actions.
	 * If the entered player name is already in the player roster, the method displays an error message indicating that the player already exists.
	 * If the entered player name is valid and not found in the player roster, the player is added to the roster, and a success message is displayed.
	 * 
	 * The method continues to prompt for a player name until either a valid player name is provided and added, or the user cancels the input.
	 */
	public void addPlayer() {
	    String playerName;
	    boolean shouldContinue = true;

	    do {
	        playerName = JOptionPane.showInputDialog("Enter Player's Name:");

	        if (playerName != null) {
	            // Use a regular expression to check if the playerName contains only alphanumeric characters
	            if (Pattern.matches("^[a-zA-Z0-9]*$", playerName)) {
	                if (!playerRoster.checkPlayer(playerName)) {
	                    Player player = new Player(playerName);
	                    playerRoster.addPlayer(player);
	                    main.printAddSuccessMessage();
	                    shouldContinue = false;
	                } else {
	                    main.printInvalidPlayer();
	                }
	            } else {
	                main.printInvalidCharacter();
	            }
	        } else {
	            // User canceled the input, break out of the loop
	            shouldContinue = false;
	        }
	    } while (shouldContinue);
	}

	/**
	 * Prompts the user to enter the name of a player they want to delete and removes the player from the player roster if applicable.
	 * 
	 * If the user cancels the input or closes the input dialog, the method terminates without any further actions.
	 * If the entered player name matches one of the predefined players ("Hal" or "Mr.Bean"), the method displays an error message.
	 * If the entered player name is not found in the player roster, the method displays a message indicating that the player does not exist.
	 * If the entered player is currently in a game (either as the left or right player), the method displays a message that the player cannot be deleted during the game.
	 * If the entered player name is valid and not in a game, the player is removed from the player roster, and a success message is displayed.
	 * 
	 * The method continues to prompt for a player name until either a valid player name is provided and deleted, or the user cancels the input.
	 */
	public void deletePlayer() {
	    String playerName;
	    Player leftPlayer = main.getLeftPlayer().getPlayer();
	    Player rightPlayer = main.getRightPlayer().getPlayer();

	    boolean shouldContinue = true;
	    while (shouldContinue) {
	        playerName = JOptionPane.showInputDialog("Enter Player's Name");
	        if (playerName == null) {
	            // User canceled the input, break out of the loop
	            shouldContinue = false;
	        } else if (!playerName.equals("Hal") && !playerName.equals("Mr.Bean") &&
	                playerRoster.checkPlayer(playerName)) {

	            // Check if the player being deleted is currently in a game
	            if ((leftPlayer != null && leftPlayer.getName().equals(playerName)) ||
	                (rightPlayer != null && rightPlayer.getName().equals(playerName))) {
	                main.printDeletePlayerInGame();
	            } else {
	                playerRoster.deletePlayer(playerName);
	                main.printDeleteSuccessMessage();
	            }
	            shouldContinue = false;

	        } else if (playerName.equals("Hal") || playerName.equals("Mr.Bean")) {
	            main.printDeleteBasicPlayers();
	        } else {
	            main.printDeleteNonExistingPlayer();
	        }
	    }
	}
	
	/**
	 * Displays the Tic-Tac-Toe tutorial in a new window.
	 * The tutorial provides explanations on the game rules, controls, and strategies.
	 * Players can refer to this tutorial section for assistance while playing the game.
	 * The tutorial is displayed in a user-friendly and visually appealing interface,
	 * with sections divided into game rules, controls, and strategies for easy navigation.
	 * The content of each section is presented with clear and concise explanations.
	 * Visual aids, such as diagrams or animations, may be included to illustrate concepts and strategies.
	 * The tutorial window will remain open until the user closes it, allowing the player to access
	 * the tutorial whenever they need assistance or wish to review the game's instructions.
	 * The method uses a lambda expression to execute the tutorial display on the Swing Event Dispatch Thread,
	 * ensuring that the GUI updates are performed correctly and without blocking the main application thread.
	 */
	public void showTutorial() {
	    SwingUtilities.invokeLater(() -> {
	        TicTacToeTutorial tutorial = new TicTacToeTutorial();
	        tutorial.setVisible(true);
	    });
	}




	public MainWindow getMain() {
		return main;
	}

	public GameModel getModel() {
		return model;
	}

	public PlayerRoster getPlayerRoster() {
		return playerRoster;
	}

	public WriteData getWriteData() {
		return writeData;
	}

	public ReadData getReadData() {
		return readData;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}
}
