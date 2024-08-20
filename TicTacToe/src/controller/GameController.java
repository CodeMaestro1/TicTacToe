package controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.GameModel;
import model.Player;
import model.PlayerActionFactory;
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
     * @throws IOException if an error occurs while writing data.
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

        setupSpecialPlayers(leftPlayer, "X");
        setupSpecialPlayers(rightPlayer, "O");

        if (leftPlayer.equals(rightPlayer)) {
            main.printSamePlayerError();
            return;
        }

        model.setInGame(true);
        model.setMover(0);
        configureStartGameUI(leftPlayer, rightPlayer);
    }

    private void setupSpecialPlayers(Player player, String symbol) {
        if ("Hal".equals(player.getName()) || "Mr.Bean".equals(player.getName())) {
            PlayerActionFactory.createPlayerAction(player.getName(), symbol);
        }
    }

    private void configureStartGameUI(Player leftPlayer, Player rightPlayer) {
        leftPlayer.setRecentGame(leftPlayer.getName(), rightPlayer.getName());
        rightPlayer.setRecentGame(leftPlayer.getName(), rightPlayer.getName());

        main.getBannerPanel().getStartGameButton().setEnabled(false);
        main.getMainAreaPanel().remove(main.getMainAreaPanel().getHallOfFame());
        main.getMainAreaPanel().add(gameBoard);
        setBannerPanelButtonsEnabled(false);
        setPlayerSelectButtonsEnabled(false);
    }

    /**
     * Quits the current game and returns to the main menu.
     */
    public void quitGame() {
        model.setInGame(false);
        model.resetBoard();
        resetUIComponents();
    }

    private void resetUIComponents() {
        PlayerPanel leftPlayer = main.getLeftPlayer();
        PlayerPanel rightPlayer = main.getRightPlayer();

        main.getMainAreaPanel().remove(gameBoard);
        main.getMainAreaPanel().getHallOfFame().refresh();
        leftPlayer.refresh();
        rightPlayer.refresh();
        main.getMainAreaPanel().add(main.getMainAreaPanel().getHallOfFame());

        setBannerPanelButtonsEnabled(true);
        setPlayerSelectButtonsEnabled(true);
    }

    private void setBannerPanelButtonsEnabled(boolean enabled) {
        main.getBannerPanel().getStartGameButton().setEnabled(enabled);
        main.getBannerPanel().getAddPlayerButton().setEnabled(enabled);
        main.getBannerPanel().getDeletePlayerButton().setEnabled(enabled);
        main.getBannerPanel().getDoneButton().setEnabled(!enabled);
    }

    private void setPlayerSelectButtonsEnabled(boolean enabled) {
        main.getLeftPlayer().getPlayerSelectButton().setEnabled(enabled);
        main.getRightPlayer().getPlayerSelectButton().setEnabled(enabled);
    }

    /**
     * Prompts the user to enter a player's name and adds the player to the player roster if valid.
     */
    public void addPlayer() {
        String playerName;
        boolean shouldContinue = true;

        while (shouldContinue) {
            playerName = JOptionPane.showInputDialog("Enter Player's Name:");

            if (playerName == null) {
                shouldContinue = false;
            } else if (isValidPlayerName(playerName)) {
                if (!playerRoster.checkPlayer(playerName)) {
                    playerRoster.addPlayer(new Player(playerName));
                    main.printAddSuccessMessage();
                    shouldContinue = false;
                } else {
                    main.printInvalidPlayer();
                }
            } else {
                main.printInvalidCharacter();
            }
        }
    }

    private boolean isValidPlayerName(String playerName) {
        return Pattern.matches("^[a-zA-Z0-9]*$", playerName);
    }

    /**
     * Prompts the user to enter a player's name and deletes the player if valid.
     */
    public void deletePlayer() {
        String playerName;
        boolean shouldContinue = true;

        while (shouldContinue) {
            playerName = JOptionPane.showInputDialog("Enter Player's Name");

            if (playerName == null) {
                shouldContinue = false;
            } else if (canDeletePlayer(playerName)) {
                playerRoster.deletePlayer(playerName);
                main.printDeleteSuccessMessage();
                shouldContinue = false;
            } else {
                handleInvalidDeleteRequest(playerName);
            }
        }
    }

    private boolean canDeletePlayer(String playerName) {
        return !("Hal".equals(playerName) || "Mr.Bean".equals(playerName)) && playerRoster.checkPlayer(playerName) && !isPlayerInGame(playerName);
    }

    private boolean isPlayerInGame(String playerName) {
        Player leftPlayer = main.getLeftPlayer().getPlayer();
        Player rightPlayer = main.getRightPlayer().getPlayer();

        return (leftPlayer != null && leftPlayer.getName().equals(playerName)) ||
               (rightPlayer != null && rightPlayer.getName().equals(playerName));
    }

    private void handleInvalidDeleteRequest(String playerName) {
        if ("Hal".equals(playerName) || "Mr.Bean".equals(playerName)) {
            main.printDeleteBasicPlayers();
        } else if (playerRoster.checkPlayer(playerName)) {
            main.printDeletePlayerInGame();
        } else {
            main.printDeleteNonExistingPlayer();
        }
    }

    /**
     * Displays the Tic-Tac-Toe tutorial in a new window.
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
