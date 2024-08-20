/**
 * The GameModel class represents the game logic and state for the Tic-Tac-Toe game.
 * It manages the moves of players, checks for win conditions, handles AI moves, and updates player scores.
 */
package model;


import java.io.IOException;

import controller.GameController;
import view.MainWindow;

public class GameModel {

	private GameController gc;
    private int mover;
    private boolean inGame;
    private MainWindow main;
    private GameLogic logic;
    
    /**
     * Constructor for the GameModel class.
     * Initializes the game state and sets up the RandomPlayer instance for Mr. Bean moves.
     *
     * @param gc The GameController instance associated with this model.
     * @throws IOException 
     */
    public GameModel(GameController gc) throws IOException {
        this.gc = gc;
        this.inGame = false;
        this.main =new MainWindow(this.gc);
        this.logic = new GameLogic(this.gc);
    }
    
    /**
     * Changes the current mover (player turn) and schedules AI moves with a delay.
     */
    public void changeMover() {
        if (inGame) {
        	
        	Player leftPlayer  = main.getLeftPlayer().getPlayer();
        	Player rightPlayer = main.getRightPlayer().getPlayer();
        	
            mover = (mover == 0) ? 1 : 0;
            Player currentPlayer = mover == 0 ? leftPlayer  : rightPlayer;
            currentPlayer.makeMove(gc); // Delegate move to the current player
            logic.checkWinner();
        }
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