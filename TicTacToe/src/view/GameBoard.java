package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Arrays;

import controller.GameController;

@SuppressWarnings({ "serial" })
public class GameBoard extends GamePanel {

    private BoardCell[][] cells;
    private MainWindow main;
    private GameController gc;
    private String[][] board;
    
    /**
     * Constructs a new GameBoard object.
     * 
     * @param gc    The GameController associated with the game board.
     * @param main  The MainWindow to which the game board belongs.
     */
    public GameBoard(GameController gc, MainWindow main) {
        super(gc);
        this.main = main;
        this.setLayout(null);
        this.setSize(new Dimension(MainWindow.WIDTH - 2 * MainWindow.PLAYER_WIDTH, MainWindow.HEIGHT - MainWindow.TOP_HEIGHT));
        this.setBackground(Color.YELLOW);
        this.cells = new BoardCell[3][3];
        board = new String[3][3];

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                BoardCell cell = new BoardCell(gc, row, column);

                cell.setBounds(boardZero().x + column * cellSize() + BoardCell.CELL_PADDING,
                        boardZero().y + row * cellSize() + BoardCell.CELL_PADDING,
                        cellSize() - 2 * BoardCell.CELL_PADDING, cellSize() - 2 * BoardCell.CELL_PADDING);
                this.add(cell);
                this.cells[row][column] = cell;
            }
        }
    }
    

    /**
     * Calculates the size of a cell based on the minimum dimension of the game board.
     * 
     * @return The size of a cell.
     */
    private int cellSize() {
        int minDim = Integer.min(this.getWidth(), this.getHeight());
        return minDim / 5;
    }

    /**
     * Calculates the size of the game board.
     * 
     * @return The size of the game board.
     */
    private int boardSize() {
        return 3 * cellSize();
    }

    /**
     * Calculates the starting point of the game board.
     * 
     * @return The starting point (top-left corner) of the game board.
     */
    private Point boardZero() {
        int x = (this.getWidth() - boardSize()) / 2;
        int y = (this.getHeight() - boardSize()) / 2;
        return new Point(x, y);
    }

    /**
     * Draws the grid lines on the game board.
     * 
     * @param g The Graphics object to draw on.
     */
    public void drawGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(6));

        for (int i = 1; i < 3; i++) {
            g2d.drawLine(boardZero().x + i * cellSize(), boardZero().y, boardZero().x + i * cellSize(),
                    boardZero().y + boardSize());

            g2d.drawLine(boardZero().x, boardZero().y + i * cellSize(), boardZero().x + boardSize(),
                    boardZero().y + i * cellSize());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        this.drawGrid(g2d);

    }

    /**
     * Gets the array of BoardCell objects representing the cells of the game board.
     * 
     * @return The array of BoardCell objects.
     */
    public BoardCell[][] getCells() {
        return cells;
    }

    /**
     * Sets the array of BoardCell objects representing the cells of the game board.
     * 
     * @param cells The new array of BoardCell objects.
     */
    public void setCells(BoardCell[][] cells) {
        this.cells = cells;
    }

    /**
     * Gets the MainWindow associated with the game board.
     * 
     * @return The MainWindow object.
     */
    public MainWindow getMain() {
        return main;
    }

    /**
     * Sets the MainWindow associated with the game board.
     * 
     * @param main The new MainWindow object.
     */
    public void setMain(MainWindow main) {
        this.main = main;
    }

    /**
     * Gets the GameController associated with the game board.
     * 
     * @return The GameController object.
     */
    public GameController getGc() {
        return gc;
    }

    /**
     * Sets the GameController associated with the game board.
     * 
     * @param gc The new GameController object.
     */
    public void setGc(GameController gc) {
        this.gc = gc;
    }

    /**
     * Makes a copy of the board array instead of directly returning the reference.
     * 
     * @return A copy of the board array.
     */
    public String[][] getBoard() {
        return Arrays.copyOf(board, board.length);
    }

    /**
     * Sets the board array representing the state of the game board.
     * 
     * @param board The new board array.
     */
    public void setBoard(String[][] board) {
        this.board = board;
    }
}