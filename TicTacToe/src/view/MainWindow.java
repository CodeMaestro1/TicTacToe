/**
 * The MainWindow class represents the main GUI window for the Tic-Tac-Toe game application.
 * It extends the JFrame class and contains various panels for displaying game elements and messages.
 *
 * This class provides methods to print different types of messages and manage the game's graphical components.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.GameController;
import model.Player;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

    // Constants for window dimensions and player panel sizes
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final int TOP_HEIGHT = 80;
    public static final int PLAYER_WIDTH = 300;

    // Panels for different game elements
    private PlayerPanel leftPlayer;
    private PlayerPanel rightPlayer;
    private BannerPanel bannerPanel;
    private GameBoard gameBoard;
    private GamePanel gamePanel;
    private MainAreaPanel mainAreaPanel;

    // Reference to the game controller
    private GameController gc;

    /**
     * Constructor for MainWindow class.
     * Creates the main GUI window and initializes the game elements' panels.
     *
     * @param gc The GameController instance to manage the game.
     * @throws IOException If an I/O error occurs.
     */
    public MainWindow(GameController gc) throws IOException {
        super("TIC-TAC-TOE");
        this.gc = gc;

        // Set the window size
        Container c = this.getContentPane();
        c.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // Initialize all the panels
        this.bannerPanel = new BannerPanel(gc);
        this.leftPlayer = new PlayerPanel(gc, 0);
        this.rightPlayer = new PlayerPanel(gc, 1);
        this.mainAreaPanel = new MainAreaPanel(gc);

        // Add the panels to the main window using BorderLayout
        this.add(bannerPanel, BorderLayout.PAGE_START);
        this.add(leftPlayer, BorderLayout.LINE_START);
        this.add(rightPlayer, BorderLayout.LINE_END);
        this.add(mainAreaPanel, BorderLayout.CENTER);

        this.pack();
        this.setLocationRelativeTo(null);
    }

    /**
     * Displays a message dialog to indicate that the player selected is already taken.
     */
    public void printInvalidPlayer() {
        JOptionPane.showMessageDialog(this,
                "Player already taken! Please choose another.",
                "Invalid Player",
                JOptionPane.ERROR_MESSAGE);
    }
    
    
    /**
     * Displays an error message dialog indicating that the same player cannot be selected for both X and O.
     */
    public void printSamePlayerError() {
        JOptionPane.showMessageDialog(this,
        		"Error: The same player cannot be selected for both X and O!",
        		"Error",
        		JOptionPane.ERROR_MESSAGE);
    }


    /**
     * Displays a message dialog indicating that the player cannot be deleted because they are currently in a game.
     */
    public void printDeletePlayerInGame() {
        JOptionPane.showMessageDialog(this,
        		"Error: The player cannot be deleted because they are currently in a game!",
        		"Error",
        		JOptionPane.ERROR_MESSAGE);
    }

    
    /**
     * Displays a message dialog to indicate that the player to delete does not exist.
     */
    public void printDeleteNonExistingPlayer() {
        JOptionPane.showMessageDialog(this,
                "The player does not exist",
                "Invalid input",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays a message dialog to indicate that a basic player cannot be deleted.
     */
    public void printDeleteBasicPlayers() {
        JOptionPane.showMessageDialog(this,
                "This player cannot be deleted",
                "Invalid input",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays a success message dialog for successful player deletion.
     */
    public void printDeleteSuccessMessage() {
        JOptionPane.showMessageDialog(
                this,
                "Player successfully deleted!",
                "Delete Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Displays a success message dialog for successful player addition.
     */
    public void printAddSuccessMessage() {
        JOptionPane.showMessageDialog(
                this,
                "Player successfully added!",
                "Add Success",
                JOptionPane.INFORMATION_MESSAGE);
    }
    

    /**
     * Retrieves the PlayerPanel representing the left player.
     *
     * @return The PlayerPanel representing the left player.
     */
    public PlayerPanel getLeftPlayer() {
        return leftPlayer;
    }

    /**
     * Sets the PlayerPanel representing the left player.
     *
     * @param leftPlayer The PlayerPanel representing the left player.
     */
    public void setLeftPlayer(PlayerPanel leftPlayer) {
        this.leftPlayer = leftPlayer;
    }

    /**
     * Retrieves the PlayerPanel representing the right player.
     *
     * @return The PlayerPanel representing the right player.
     */
    public PlayerPanel getRightPlayer() {
        return rightPlayer;
    }

    /**
     * Sets the PlayerPanel representing the right player.
     *
     * @param rightPlayer The PlayerPanel representing the right player.
     */
    public void setRightPlayer(PlayerPanel rightPlayer) {
        this.rightPlayer = rightPlayer;
    }

    /**
     * Retrieves the BannerPanel displaying the game banner and buttons.
     *
     * @return The BannerPanel displaying the game banner and buttons.
     */
    public BannerPanel getBannerPanel() {
        return bannerPanel;
    }

    /**
     * Sets the BannerPanel displaying the game banner and buttons.
     *
     * @param bannerPanel The BannerPanel displaying the game banner and buttons.
     */
    public void setBannerPanel(BannerPanel bannerPanel) {
        this.bannerPanel = bannerPanel;
    }

    /**
     * Retrieves the GameBoard representing the game board.
     *
     * @return The GameBoard representing the game board.
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Sets the GameBoard representing the game board.
     *
     * @param gameBoard The GameBoard representing the game board.
     */
    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Retrieves the GameController associated with this MainWindow.
     *
     * @return The GameController associated with this MainWindow.
     */
    public GameController getGc() {
        return gc;
    }

    /**
     * Sets the GameController associated with this MainWindow.
     *
     * @param gc The GameController to be associated with this MainWindow.
     */
    public void setGc(GameController gc) {
        this.gc = gc;
    }

    /**
     * Retrieves the top height of the main window.
     *
     * @return The top height of the main window.
     */
    public static int getTopHeight() {
        return TOP_HEIGHT;
    }

    /**
     * Retrieves the width of each player's panel.
     *
     * @return The width of each player's panel.
     */
    public static int getPlayerWidth() {
        return PLAYER_WIDTH;
    }

    /**
     * Checks if both players are ready (have valid names) to start the game,
     * and enables the start game button in the banner panel if true.
     */
    public void checkIfReady() {
        Player newLeftPlayer = gc.getMain().getLeftPlayer().getPlayer();
        Player newRightPlayer = gc.getMain().getRightPlayer().getPlayer();

        if (newLeftPlayer != null && newLeftPlayer.getName() != null && newRightPlayer != null && newRightPlayer.getName() != null) {
            gc.getMain().getBannerPanel().getStartGameButton().setEnabled(true);
        }
    }

    /**
     * Retrieves the GamePanel representing the game panel.
     *
     * @return The GamePanel representing the game panel.
     */
    public GamePanel getGamePanel() {
        return gamePanel;
    }

    /**
     * Sets the GamePanel representing the game panel.
     *
     * @param gamePanel The GamePanel representing the game panel.
     */
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Retrieves the MainAreaPanel representing the main area panel.
     *
     * @return The MainAreaPanel representing the main area panel.
     */
    public MainAreaPanel getMainAreaPanel() {
        return mainAreaPanel;
    }

    /**
     * Sets the MainAreaPanel representing the main area panel.
     *
     * @param mainAreaPanel The MainAreaPanel representing the main area panel.
     */
    public void setMainAreaPanel(MainAreaPanel mainAreaPanel) {
        this.mainAreaPanel = mainAreaPanel;
    }
}