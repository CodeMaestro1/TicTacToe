package view;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JTextArea;

import controller.GameController;
import model.Player;

/**
 * The HallOfFame class displays the hall of fame for players based on their scores.
 * It extends the GamePanel class.
 */
public class HallOfFame extends GamePanel {

    private static final long serialVersionUID = 1L;

    private JTextArea display;
    private GameController gc;

    /**
     * Constructs a HallOfFame object.
     *
     * @param gc The GameController object used to access player data.
     */
    public HallOfFame(GameController gc) {
        super(gc);
        this.gc = gc;
        display = new JTextArea();
        display.setEnabled(true);
        display.setEditable(false);
        display.setBackground(Color.ORANGE);
        Font markf = new Font("SansSerif", Font.BOLD, 30);
        display.setFont(markf);
        this.setBackground(Color.ORANGE);
        display.setBounds(0, 0, MainWindow.WIDTH - 2 * MainWindow.PLAYER_WIDTH, MainWindow.HEIGHT - MainWindow.TOP_HEIGHT);
        display.append("Hall of Fame\n\n\n");
        this.add(display);
        refresh();
    }

    /**
     * Refreshes the hall of fame with updated player data.
     */
    public void refresh() {
    	Player[] bestPlayer;
        display.setEditable(true);
        bestPlayer = getTopPlayers();
        display.setText("Hall Of Fame\n\n\n");
        int loop = Math.min(bestPlayer.length, 10);
        for (int j = 0; j < loop; j++) {
            if (bestPlayer[j] != null) {
                display.append(bestPlayer[j].getName() + "  " + bestPlayer[j].getScore() + "\n");
            }
        }
        display.setEditable(false);
    }

    /**
     * Retrieves the top players based on their scores in descending order.
     *
     * @return An array of Player objects representing the top players.
     */
    private Player[] getTopPlayers() {
        Player[] playerRoster = gc.getPlayerRoster().getPlayer();
        Player[] sortedPlayers = Arrays.copyOf(playerRoster, playerRoster.length);
        Arrays.sort(sortedPlayers, Comparator.nullsLast(Comparator.comparingDouble(Player::getScore).reversed()));
        return sortedPlayers;
    }
}