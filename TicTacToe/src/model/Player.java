package model;

import java.text.DecimalFormat;
import controller.GameController;

public class Player {

    private String name;
    private int totalGames;
    private float score;
    private int wins;
    private int ties;
    private String[][] recentGames;
    private static final DecimalFormat df = new DecimalFormat("#.###"); // formatting for score
    private String symbol;

    public Player(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        this.recentGames = initializeRecentGames();
        this.totalGames = 0;
        this.score = 0;
        this.wins = 0;
        this.ties = 0;
    }

    public Player(String name) {
        this(name, " "); // Default symbol is a space
    }

    public Player() {
        this(" ", " "); // Default name and symbol as spaces
    } //To be deleted soon.

    private String[][] initializeRecentGames() {
        String[][] games = new String[5][2];
        for (int i = 0; i < games.length; i++) {
            for (int j = 0; j < games[i].length; j++) {
                games[i][j] = "None";
            }
        }
        return games;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void updateScore() {
        if (totalGames > 0) {
            String formattedScore = df.format(50.0 * ((2 * this.wins) + this.ties) / this.totalGames);
            formattedScore = formattedScore.replace(",", ".");
            this.score = Float.parseFloat(formattedScore);
        } else {
            this.score = 0;
        }
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getTies() {
        return ties;
    }

    public void setTies(int ties) {
        this.ties = ties;
    }

    public String[][] getRecentGames() {
        return recentGames.clone();
    }

    public void setRecentGame(String player1, String player2) {
        for (int i = recentGames.length - 1; i > 0; i--) {
            recentGames[i] = recentGames[i - 1].clone();
        }
        recentGames[0][0] = player1;
        recentGames[0][1] = player2;
    }

    public String getRecentGame(int row, int col) {
        if (row < 0 || row >= recentGames.length || col < 0 || col >= recentGames[row].length) {
            throw new IndexOutOfBoundsException("Invalid recent game index");
        }
        return recentGames[row][col];
    }

    public void setRecentGames(String[][] recentGames) {
        if (recentGames != null && recentGames.length == 5 && recentGames[0].length == 2) {
            this.recentGames = recentGames.clone();
        } else {
            throw new IllegalArgumentException("Invalid recent games array");
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * To be completed by its subclasses.
     * @param gc
     * @param mover
     */
    public void makeMove(GameController gc) {
        // Method to be implemented in subclasses
    }
}
