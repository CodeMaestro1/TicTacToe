/**
 * The RandomPlayer class represents a player that makes random moves on a Tic-Tac-Toe board.
 * It generates random row and column indices to play a move on the board.
 */
package model;

import java.util.Random;

import controller.GameController;
import utils.SchedulerUtil;

public class RandomPlayer extends Player{
    private int row;
    private int column;
    private static Random rand = new Random();
    private static final String PLAYER_NAME = "Mr.Bean";
    
    public RandomPlayer() {
    	super(PLAYER_NAME, "O");
    }

    /**
     * Generates a random move on the Tic-Tac-Toe board.
     * The method randomly chooses a cell on the board and checks if it's empty.
     * If the cell is empty, the move is valid, and the row and column indices are updated accordingly.
     * If the chosen cell is not empty, it generates another random move until a valid move is found.
     *
     * @param board The current state of the Tic-Tac-Toe board.
     */
    public void playRandomMove(String[][] board) {
        boolean validInput = false;
        do {
            int intRandom = rand.nextInt(9) + 1; // generate random values from 1-9

            row = (intRandom - 1) / 3; // calculate the row index
            column = (intRandom - 1) % 3; // calculate the column index

            if (board[row][column] == null) {
                validInput = true;
            }
        } while (!validInput);
    }
    
    /**
     * Gets the row index of the move.
     *
     * @return The row index of the move.
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row index of the move.
     *
     * @param row The row index of the move.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Gets the column index of the move.
     *
     * @return The column index of the move.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Sets the column index of the move.
     *
     * @param column The column index of the move.
     */
    public void setColumn(int column) {
        this.column = column;
    }
    
    /**
     * Executes Mr. Bean's move based on the right and left players' names.
     * If the right player's name is "Mr.Bean" and it's their turn (mover == 1), Mr. Bean makes a random move on the board.
     * If the left player's name is "Mr.Bean" and it's their turn (mover == 0), Mr. Bean makes a random move on the board.
     */
    @Override
    public void makeMove(GameController gc) {
        playRandomMove(gc.getGameBoard().getBoard());
        int tempRow = getRow();
        int tempCol = getColumn();
        
        SchedulerUtil.scheduleMove(tempRow, tempCol, () -> {
            gc.getGameBoard().getCells()[tempRow][tempCol].chooseCell();
        });
    }
}