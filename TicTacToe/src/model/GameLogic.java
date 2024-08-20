package model;

import java.awt.Color;

import controller.GameController;

public class GameLogic {

    private GameController gc;
    private int mover;
    private boolean inGame;

    public GameLogic(GameController gc) {
        this.gc = gc;
        this.inGame = false;
        this.mover = 0;
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

        // Check for a tie
        if (isFull()) {
            handleTie();
            return true;
        }

        return false;
    }

    private boolean isWinningMove(String symbol1, String symbol2, String symbol3) {
        return symbol1 != null && symbol1.equals(symbol2) && symbol1.equals(symbol3);
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

    private void handleWinningMove(String winBoardSymbol) {
        Player leftPlayer = gc.getMain().getLeftPlayer().getPlayer();
        Player rightPlayer = gc.getMain().getRightPlayer().getPlayer();

        inGame = false;

        if (winBoardSymbol.equals("X")) {
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
        Player leftPlayer = gc.getMain().getLeftPlayer().getPlayer();
        Player rightPlayer = gc.getMain().getRightPlayer().getPlayer();

        inGame = false;
        rightPlayer.setTies(rightPlayer.getTies() + 1);
        leftPlayer.setTies(leftPlayer.getTies() + 1);
        rightPlayer.setTotalGames(rightPlayer.getTotalGames() + 1);
        leftPlayer.setTotalGames(leftPlayer.getTotalGames() + 1);
        rightPlayer.updateScore();
        leftPlayer.updateScore();
    }

   /**
    * Checks if the Tic-Tac-Toe board is full (all cells are chosen).
    *
    * @return true if the board is full, false otherwise.
    */
    private boolean isFull() {
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

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
}
