/**
 * The GameModel class represents the game logic and state for the Tic-Tac-Toe game.
 * It manages the moves of players, checks for win conditions, handles AI moves, and updates player scores.
 */
package model;


import controller.GameController;

public class GameModel {

	private GameController gc;
    private int mover;
    private boolean inGame;
    
    /**
     * Constructor for the GameModel class.
     * Initializes the game state and sets up the RandomPlayer instance for Mr. Bean moves.
     *
     * @param gc The GameController instance associated with this model.
     */
    public GameModel(GameController gc) {
        this.gc = gc;
        this.inGame = false;
        new RandomPlayer();
    }

    /**
     * Checks if the given row and column indices are valid on the Tic-Tac-Toe board.
     *
     * @param row The row index to check.
     * @param column The column index to check.
     * @return true if the row and column indices are valid, false otherwise.
     */
    public boolean checkValidInput(int row, int column) {
        return row >= 1 && row <= 3 && column >= 1 && column <= 3;
    }


    /**
     * Checks if the Tic-Tac-Toe board is full (all cells are chosen).
     *
     * @return true if the board is full, false otherwise.
     */
    public boolean isFull() {
        String[][] board = gc.getGameBoard().getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) {
                    return false; // If any cell is null, the board is not full
                }
            }
        }
        return true; // All cells are non-null, indicating a full board
    }


    /**
     * Changes the current mover (player turn) and schedules AI moves with a delay.
     */
    public void changeMover() {
        if (inGame) {
            mover = (mover == 0) ? 1 : 0;
            aiMove();
            mrBeanMove();
        }
    }


    /**
     * Resets the Tic-Tac-Toe board and associated variables to start a new game.
     */
    public void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gc.getGameBoard().getCells()[i][j].setChosen(false);
                gc.getGameBoard().getCells()[i][j].setClicked(false);
                gc.getGameBoard().getCells()[i][j].setHighlighted(false);
                gc.getGameBoard().getCells()[i][j].setBackground(null);
                gc.getGameBoard().getBoard()[i][j] = null;
            }
        }
    }

    /**
     * Gets the current mover (player turn).
     *
     * @return The current mover (0 for left player, 1 for right player).
     */
    public int getMover() {
        return mover;
    }

    /**
     * Sets the current mover (player turn) and executes Mr. Bean and AI moves.
     *
     * @param mover The current mover (0 for left player, 1 for right player).
     */
    public void setMover(int mover) {
        this.mover = mover;
        mrBeanMove();
        aiMove();
    }

    /**
     * Checks if the game is in progress.
     *
     * @return true if the game is in progress, false otherwise.
     */
    public boolean isInGame() {
        return inGame;
    }

    /**
     * Sets the game state to in-progress or not in-progress.
     *
     * @param inGame true if the game is in progress, false otherwise.
     */
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
}