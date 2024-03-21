/**
 * The GameModel class represents the game logic and state for the Tic-Tac-Toe game.
 * It manages the moves of players, checks for win conditions, handles AI moves, and updates player scores.
 */
package model;


import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import controller.GameController;

public class GameModel {

	private GameController gc;
    private int mover;
    private boolean inGame;
    private RandomPlayer mrBean;
    private String rightPlayerName;
    private String leftPlayerName;
    private Timer timer;
    
    private static final int MOVE_DELAY = 1000; // 1 second delay for AI moves
    private static final String SYMBOL_X = "X";
    private static final String SYMBOL_O = "O";

    /**
     * Constructor for the GameModel class.
     * Initializes the game state and sets up the RandomPlayer instance for Mr. Bean moves.
     *
     * @param gc The GameController instance associated with this model.
     */
    public GameModel(GameController gc) {
        this.gc = gc;
        this.inGame = false;
        this.mrBean = new RandomPlayer();
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
     * Executes an AI move based on the right and left players' names.
     * If the right player's name is "Hal" and it's their turn (mover == 1), AI uses the Minimax algorithm to find the best move.
     * If the left player's name is "Hal" and it's their turn (mover == 0), AI uses the Minimax algorithm to find the best move.
     */
    public void aiMove() {
    	
        this.rightPlayerName = gc.getMain().getRightPlayer().getPlayer().getName();
        this.leftPlayerName = gc.getMain().getLeftPlayer().getPlayer().getName();

        if (rightPlayerName != null && rightPlayerName.equals("Hal") && mover == 1) {
            AiPlayer ai = new AiPlayer(SYMBOL_O);
            ai.findBestMove(gc.getGameBoard().getBoard(), SYMBOL_X);
            int bestRow = ai.getBestRow();
            int bestCol = ai.getBestCol();
            
            
            // Cancel the existing timer task, if it exists
            if (timer != null) {
                timer.cancel();
            }

            // Add a delay before executing the AI move
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    gc.getGameBoard().getCells()[bestRow][bestCol].chooseCell();
                }
            }, MOVE_DELAY);
        }

        if (leftPlayerName != null && leftPlayerName.equals("Hal") && mover == 0) {
            AiPlayer ai = new AiPlayer(SYMBOL_X);
            ai.findBestMove(gc.getGameBoard().getBoard(), SYMBOL_O);
            int bestRow = ai.getBestRow();
            int bestCol = ai.getBestCol();
            
            // Cancel the existing timer task, if it exists
            if (timer != null) {
                timer.cancel();
            }

            // Add a delay before executing the AI move
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    gc.getGameBoard().getCells()[bestRow][bestCol].chooseCell();
                }
            }, MOVE_DELAY);
        }
    }

    /**
     * Executes Mr. Bean's move based on the right and left players' names.
     * If the right player's name is "Mr.Bean" and it's their turn (mover == 1), Mr. Bean makes a random move on the board.
     * If the left player's name is "Mr.Bean" and it's their turn (mover == 0), Mr. Bean makes a random move on the board.
     */
    public void mrBeanMove() {
        rightPlayerName = gc.getMain().getRightPlayer().getPlayer().getName();
        leftPlayerName = gc.getMain().getLeftPlayer().getPlayer().getName();

        if (rightPlayerName != null && rightPlayerName.equals("Mr.Bean") && mover == 1) {
            mrBean.playRandomMove(gc.getGameBoard().getBoard());
            int row = mrBean.getRow();
            int col = mrBean.getColumn();
            
            // Cancel the existing timer task, if it exists
            if (timer != null) {
                timer.cancel();
            }

            // Add a delay before executing Mr. Bean's move
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    gc.getGameBoard().getCells()[row][col].chooseCell();
                }
            }, MOVE_DELAY);
        }

        if (leftPlayerName != null && leftPlayerName.equals("Mr.Bean") && mover == 0) {
            mrBean.playRandomMove(gc.getGameBoard().getBoard());
            int row = mrBean.getRow();
            int col = mrBean.getColumn();
            
            
            // Cancel the existing timer task, if it exists
            if (timer != null) {
                timer.cancel();
            }

            // Add a delay before executing Mr. Bean's move
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    gc.getGameBoard().getCells()[row][col].chooseCell();
                }
            }, MOVE_DELAY);
        }
    }

    /**
     * Checks for a winning move on the Tic-Tac-Toe board.
     * If a winning move is found, it handles the game end and updates player scores accordingly.
     */
    public boolean checkWinner() {
        if (!inGame) {
            return true; // The game is over.
        }
        
        String[][] board = gc.getGameBoard().getBoard();

        // Check rows
        for (int i = 0; i < 3; i++) {
            if (isWinningMove(board[i][0], board[i][1], board[i][2])) {
                markWinningCellsInRow(i);
                handleWinningMove(board[i][0]);
                return true; // Return true if a win is found
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (isWinningMove(board[0][j], board[1][j], board[2][j])) {
                markWinningCellsInColumn(j);
                handleWinningMove(board[0][j]);
                return true; // Return true if a win is found
            }
        }

        // Check diagonals
        if (isWinningMove(board[0][0], board[1][1], board[2][2])) {
            markWinningCellsInDiagonal(true);
            handleWinningMove(board[0][0]);
            return true; // Return true if a win is found
        } else if (isWinningMove(board[0][2], board[1][1], board[2][0])) {
            markWinningCellsInDiagonal(false);
            handleWinningMove(board[0][2]);
            return true; // Return true if a win is found
        }

        // Check for a tie (if no win has been found)
        if (isFull()) {
            handleTie();
            return true;
        }

        // If no win or tie is found, return false
        return false;
    }


    private void markWinningCellsInRow(int row) {
        for (int j = 0; j < 3; j++) {
            gc.getGameBoard().getCells()[row][j].setBackground(Color.RED);
        }
    }

    private void markWinningCellsInColumn(int col) {
        for (int i = 0; i < 3; i++) {
            gc.getGameBoard().getCells()[i][col].setBackground(Color.RED);
        }
    }

    private void markWinningCellsInDiagonal(boolean isMainDiagonal) {
        for (int i = 0; i < 3; i++) {
            int j = isMainDiagonal ? i : 2 - i;
            gc.getGameBoard().getCells()[i][j].setBackground(Color.RED);
        }
    }


    private boolean isWinningMove(String symbol1, String symbol2, String symbol3) {
        return symbol1 != null && symbol1.equals(symbol2) && symbol1.equals(symbol3);
    }

    private void handleWinningMove(String winBoardSymbol) {
        Player leftPlayer;
        Player rightPlayer;

        rightPlayer = gc.getMain().getRightPlayer().getPlayer();
        leftPlayer = gc.getMain().getLeftPlayer().getPlayer();

        inGame = false;
            
        if (winBoardSymbol.equals(SYMBOL_X)) {
            leftPlayer.setWins(leftPlayer.getWins() + 1);
        } else {
            rightPlayer.setWins(rightPlayer.getWins() + 1);
        }

        rightPlayer.setTotalGames(rightPlayer.getTotalGames() + 1);
        leftPlayer.setTotalGames(leftPlayer.getTotalGames() + 1);
        rightPlayer.updateScore();
        leftPlayer.updateScore();

    }

    private void handleTie() {
        Player leftPlayerTie;
        Player rightPlayerTie;

        rightPlayerTie = gc.getMain().getRightPlayer().getPlayer();
        leftPlayerTie = gc.getMain().getLeftPlayer().getPlayer();
        
        inGame = false;
        rightPlayerTie.setTies(rightPlayerTie.getTies() + 1);
        leftPlayerTie.setTies(leftPlayerTie.getTies() + 1);
        rightPlayerTie.setTotalGames(rightPlayerTie.getTotalGames() + 1);
        leftPlayerTie.setTotalGames(leftPlayerTie.getTotalGames() + 1);
        rightPlayerTie.updateScore();
        leftPlayerTie.updateScore();
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