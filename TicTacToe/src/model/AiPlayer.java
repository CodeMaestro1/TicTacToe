/**
 * The AiPlayer class represents an AI player that plays the best move in each turn of the game.
 * It uses the Minimax algorithm to evaluate and select the optimal move on the Tic-Tac-Toe board.
 */
package model;

import java.util.Objects;

public class AiPlayer {

    private static final int LOSE_SCORE = -10;
    private static final int WIN_SCORE = 10;
    private String playerSymbol;
    private int bestRow;
    private int bestCol;
    
    /**
     * Constructor for the AiPlayer class.
     * Initializes the AI player by setting the player's symbol.
     *
     * @param playerSymbol The symbol of the AI player (X or O).
     */
    public AiPlayer(String playerSymbol) {
        this.playerSymbol = playerSymbol;
    }
    
    /**
     * Finds the best move given the current state of the game board using the Minimax algorithm.
     *
     * @param board          The 2D array representing the Tic-Tac-Toe board.
     * @param opponentSymbol The symbol of the opponent player (X or O).
     */
    public void findBestMove(String[][] board, String opponentSymbol) {
        int bestVal = Integer.MIN_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) {
                    board[i][j] = playerSymbol;
                    int moveVal = minimax(board, 1, false, opponentSymbol);
                    board[i][j] = null;

                    if (moveVal > bestVal) {
                        bestRow = i;
                        bestCol = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
    }

    /**
     * A method that calculates whether it should minimize or maximize the score during the Minimax algorithm.
     * The Minimax algorithm is used to determine the best possible move for the AI player.
     *
     * @param board          The 2D array representing the Tic-Tac-Toe board.
     * @param depth          The current depth of the search tree during the Minimax algorithm.
     * @param isMax          A flag indicating if the current move should be maximized or minimized.
     * @param opponentSymbol The symbol of the opponent player (X or O).
     * @return The calculated score for the current move during the Minimax algorithm.
     */
    private int minimax(String[][] board, int depth, boolean isMax, String opponentSymbol) {
        int score = evaluate(board, playerSymbol);

        if (score == WIN_SCORE)
            return score;

        if (score == LOSE_SCORE)
            return score;

        if (!isMoveLeft(board))
            return 0;

        return isMax ? maximize(board, depth, opponentSymbol) : minimize(board, depth, opponentSymbol);
    }

    /**
     * A helper method used by the Minimax algorithm to maximize the score for the AI player.
     *
     * @param board          The 2D array representing the Tic-Tac-Toe board.
     * @param depth          The current depth of the search tree during the Minimax algorithm.
     * @param opponentSymbol The symbol of the opponent player (X or O).
     * @return The maximum score achieved during the Minimax algorithm for the AI player's moves.
     */
    private int maximize(String[][] board, int depth, String opponentSymbol) {
        int best = Integer.MIN_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) {
                    board[i][j] = playerSymbol;
                    best = Math.max(best, minimax(board, depth + 1, false, opponentSymbol));
                    board[i][j] = null;
                }
            }
        }

        return best;
    }

    /**
     * A helper method used by the Minimax algorithm to minimize the score for the opponent player.
     *
     * @param board          The 2D array representing the Tic-Tac-Toe board.
     * @param depth          The current depth of the search tree during the Minimax algorithm.
     * @param opponentSymbol The symbol of the opponent player (X or O).
     * @return The minimum score achieved during the Minimax algorithm for the opponent's moves.
     */
    private int minimize(String[][] board, int depth, String opponentSymbol) {
        int best = Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) {
                    board[i][j] = opponentSymbol;
                    best = Math.min(best, minimax(board, depth + 1, true, opponentSymbol));
                    board[i][j] = null;
                }
            }
        }

        return best;
    }

    /**
     * Evaluates the current state of the board and returns a score based on the player's symbol.
     * A higher score means a more favorable outcome for the AI player.
     *
     * @param board        The 2D array representing the Tic-Tac-Toe board.
     * @param playerSymbol The symbol of the AI player (X or O).
     * @return The score indicating the desirability of the board state for the AI player.
     */
    private int evaluate(String[][] board, String playerSymbol) {
    	
        String opponentSymbol = playerSymbol.equals("X") ? "O" : "X";

        if (hasVictory(board, playerSymbol)) {
            return WIN_SCORE;
        }

        if (hasVictory(board, opponentSymbol)) {
            return LOSE_SCORE;
        }

        return 0;
    }

    /**
     * Checks whether a given symbol has achieved victory on the Tic-Tac-Toe board.
     * The method examines rows, columns, and diagonals for symbol victory.
     *
     * @param board  The 2D array representing the Tic-Tac-Toe board.
     * @param symbol The symbol (X or O) to check for victory.
     * @return {@code true} if the symbol has achieved victory, {@code false} otherwise.
     *
     * @implNote This method uses Objects.equals for comparing the contents of String objects
     *           instead of using the equals or == operator. The reason for using Objects.equals
     *           is to ensure null safety during comparisons and to correctly handle array
     *           comparisons, as the input board is a 2D array of strings. Objects.equals
     *           will safely compare the contents of the strings, considering multidimensional arrays.
     *           Using Objects.equals ensures safer and more reliable comparisons of objects
     *           for content equality.
     */
    private boolean hasVictory(String[][] board, String symbol) {
        // Checking for Rows and Columns for symbol victory.
        for (int i = 0; i < 3; i++) {
            if (Objects.equals(board[i][0], board[i][1]) && Objects.equals(board[i][0], board[i][2])
                    && Objects.equals(board[i][1], board[i][2]) && Objects.equals(board[i][0], symbol)) {
                return true;
            }

            if (Objects.equals(board[0][i], board[1][i]) && Objects.equals(board[0][i], board[2][i])
                    && Objects.equals(board[1][i], board[2][i]) && Objects.equals(board[0][i], symbol)) {
                return true;
            }
        }

        // Checking for Diagonals for symbol victory.
        if ((Objects.equals(board[0][0], symbol) && Objects.equals(board[1][1], symbol) && Objects.equals(board[2][2], symbol))
        	    || (Objects.equals(board[0][2], symbol) && Objects.equals(board[1][1], symbol) && Objects.equals(board[2][0], symbol))) {
        	    return true;
        	}



        return false;
    }


    /**
     * Checks if there are any available moves left on the board.
     *
     * @param board The 2D array representing the Tic-Tac-Toe board.
     * @return {@code true} if there are available moves, {@code false} otherwise.
     */
    private boolean isMoveLeft(String[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getBestRow() {
        return bestRow;
    }

    public int getBestCol() {
        return bestCol;
    }
}