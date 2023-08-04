package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import controller.GameController;
import utils.ReadData;

/**
 * The MainAreaPanel class represents the main area panel of the game interface.
 * It extends the GamePanel class and contains a card layout to switch between different panels.
 */
@SuppressWarnings({ "serial" })
public class MainAreaPanel extends GamePanel {

    private CardLayout cards;
    private JPanel gameBoard;
    private HallOfFame hallOfFame;
    private ReadData readData;

    /**
     * Constructs a MainAreaPanel object.
     *
     * @param gc The GameController object used for game control.
     * @throws IOException If an IO error occurs during file reading.
     */
    public MainAreaPanel(GameController gc) throws IOException {
        super(gc);
        this.cards = new CardLayout();
        this.setLayout(this.cards);
        this.setPreferredSize(new Dimension(WIDTH - 2 * MainWindow.PLAYER_WIDTH, MainWindow.HEIGHT - MainWindow.TOP_HEIGHT));
        this.setBackground(Color.WHITE);
        this.setBorder(new LineBorder(Color.GRAY, 1, true));

        this.hallOfFame = new HallOfFame(gc);
        this.add(hallOfFame);
        this.readData = new ReadData(gc);
        readData.readFromFile();
        hallOfFame.refresh();
    }

    /**
     * Returns the CardLayout used in the panel.
     *
     * @return The CardLayout object.
     */
    public CardLayout getCards() {
        return cards;
    }

    /**
     * Sets the CardLayout used in the panel.
     *
     * @param cards The CardLayout object to set.
     */
    public void setCards(CardLayout cards) {
        this.cards = cards;
    }

    /**
     * Returns the game board panel.
     *
     * @return The game board panel as a JPanel.
     */
    public JPanel getGameBoard() {
        return gameBoard;
    }

    /**
     * Sets the game board panel.
     *
     * @param gameBoard The game board panel to set.
     */
    public void setGameBoard(JPanel gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Returns the HallOfFame panel.
     *
     * @return The HallOfFame panel as a HallOfFame object.
     */
    public HallOfFame getHallOfFame() {
        return hallOfFame;
    }

    /**
     * Sets the HallOfFame panel.
     *
     * @param hallOfFame The HallOfFame panel to set.
     */
    public void setHallOfFame(HallOfFame hallOfFame) {
        this.hallOfFame = hallOfFame;
    }

}