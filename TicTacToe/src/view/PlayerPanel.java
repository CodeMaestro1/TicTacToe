/**
 * The PlayerPanel class represents a panel for selecting and displaying player information.
 * it extends the JPanel class and it used in the graphical user interface of the game.
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import controller.GameController;
import model.Player;

@SuppressWarnings({ "serial" })
public class PlayerPanel extends JPanel {

    private GameController gc;
    private JButton playerSelectButton;
    private int numOfSelection;
    private String selectedPlayer;
    private JTextField playerName;
    private JLabel playerMark;
    private JTextArea playerStats;
    private Player player;
    
    
    /**
     * Constructs a PlayerPanel object with the specified GameController and number of selection.
     *
     * @param gc              The GameController object to access player data.
     * @param numOfSelection The number of player selection (0 or 1).
     */
    public PlayerPanel(GameController gc, int numOfSelection) {
        this.gc = gc;
        this.numOfSelection = numOfSelection;
        
        setPreferredSize(new Dimension(MainWindow.PLAYER_WIDTH, MainWindow.HEIGHT - MainWindow.TOP_HEIGHT));
        setBorder(new LineBorder(Color.GRAY, 1, true));
        setBackground(Color.GREEN);

        playerSelectButton = new JButton("Choose Player");
        playerSelectButton.setPreferredSize(new Dimension(150, 40));
        playerSelectButton.addActionListener(e -> selectPlayer());

        playerName = createTextField();
        playerMark = createLabel(numOfSelection == 0 ? "X" : "O", 90);
        playerStats = createTextArea();

        add(playerSelectButton);
        add(playerMark);
        add(playerName);
        add(playerStats);

        playerStats.setEditable(false);
        playerName.setEditable(false);
        this.player = new Player(getPlayerName().getName()); ///////////////////////////
    }

    /**
     * Creates a JTextField with the specified properties.
     *
     * @return The created JTextField object.
     */
    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setBackground(Color.GREEN);
        textField.setPreferredSize(new Dimension(MainWindow.PLAYER_WIDTH, 40));
        textField.setMaximumSize(textField.getPreferredSize());
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setFont(textField.getFont().deriveFont(Font.BOLD, 30));
        return textField;
    }

    /**
     * Creates a JLabel with the specified text and font size.
     *
     * @param text     The text to be displayed.
     * @param fontSize The font size of the label.
     * @return The created JLabel object.
     */
    private JLabel createLabel(String text, int fontSize) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(MainWindow.PLAYER_WIDTH, 80));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD, fontSize));
        return label;
    }

    /**
     * Creates a JTextArea with the specified properties.
     *
     * @return The created JTextArea object.
     */
    private JTextArea createTextArea() {
        JTextArea textArea = new JTextArea(10, 15);
        textArea.setPreferredSize(new Dimension(0, 0));
        textArea.setMaximumSize(textArea.getPreferredSize());
        textArea.setFont(textArea.getFont().deriveFont(Font.BOLD, 20));
        textArea.setMargin(textArea.getMargin());
        textArea.setBackground(Color.GREEN);
        return textArea;
    }

    /**
     * Displays a dialog for player selection and updates the player information.
     */
    public void selectPlayer() {
        String[] playerNames = gc.getPlayerRoster().getPlayersNames();
        Arrays.sort(playerNames);
        selectedPlayer = (String) JOptionPane.showInputDialog(this, "Choose your Player", "Player Selection", JOptionPane.PLAIN_MESSAGE, null, playerNames, null);

        playerStats.setEditable(true);
        player = gc.getPlayerRoster().findPlayer(selectedPlayer);

        if (player != null) {
            playerName.setText(player.getName());
            playerStats.setText("");
            playerStats.append("Wins: " + player.getWins() + "\n");
            playerStats.append("Ties: " + player.getTies() + "\n");
            playerStats.append("Total Games: " + player.getTotalGames() + "\n");
            playerStats.append("Recent Games:\n");
            for (int a = 0; a < 5; a++) {
                String recentGame = player.getRecentGame(a, 0);
                if (recentGame != null) {
                    playerStats.append(recentGame + " VS ");
                }
                recentGame = player.getRecentGame(a, 1);
                if (recentGame != null) {
                    playerStats.append(recentGame + "\n");
                }
            }
        } else {
            playerName.setText("");
            playerStats.setText("Player not found");
        }

        playerStats.setEditable(false);

        if (gc.getMain().getLeftPlayer().getPlayer() != null && gc.getMain().getRightPlayer().getPlayer() != null
                && gc.getMain().getLeftPlayer().getPlayer().getName().equals(gc.getMain().getRightPlayer().getPlayer().getName())) {
            gc.getMain().printInvalidPlayer();
        }

        repaint();
        gc.getMain().checkIfReady();
    }

    /**
     * Refreshes the player information displayed on the panel.
     */
    public void refresh() {
        playerStats.setEditable(true);
        playerName.setText(player.getName());
        playerStats.setText("");
        playerStats.append("Wins: " + player.getWins() + "\n");
        playerStats.append("Ties: " + player.getTies() + "\n");
        playerStats.append("Total Games: " + player.getTotalGames() + "\n");
        playerStats.append("Recent Games:\n");
        for (int a = 0; a < 5; a++) {
            playerStats.append(player.getRecentGame(a, 0) + " VS " + player.getRecentGame(a, 1) + "\n");
        }
        playerStats.setEditable(false);
    }

    // Getters and Setters

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GameController getGc() {
        return gc;
    }

    public void setGc(GameController gc) {
        this.gc = gc;
    }

    public JButton getPlayerSelectButton() {
        return playerSelectButton;
    }

    public void setPlayerSelectButton(JButton playerSelectButton) {
        this.playerSelectButton = playerSelectButton;
    }

    public int getNumOfSelection() {
        return numOfSelection;
    }

    public void setNumOfSelection(int numOfSelection) {
        this.numOfSelection = numOfSelection;
    }

    public String getSelectedPlayer() {
        return selectedPlayer;
    }

    public void setSelectedPlayer(String selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }

    public JTextField getPlayerName() {
        return playerName;
    }

    public JLabel getPlayerMark() {
        return playerMark;
    }

    public JTextArea getPlayerStats() {
        return playerStats;
    }
}