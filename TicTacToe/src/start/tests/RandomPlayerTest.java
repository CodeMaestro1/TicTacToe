package start.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.RandomPlayer;

public class RandomPlayerTest {

    private RandomPlayer randomPlayer;

    @BeforeEach
    public void setUp() {
        randomPlayer = new RandomPlayer();
    }

    @Test
    @DisplayName("Check if random move is valid")
    public void testRandomMove() {
        String[][] board = { { "X", null, null }, { null, "O", null }, { null, null, null } };

        randomPlayer.playRandomMove(board);
        int randomRow = randomPlayer.getRow();
        int randomColumn = randomPlayer.getColumn();

        assertTrue(board[randomRow][randomColumn] == null, "The random move should be in an empty cell");
    }

    @Test
    @DisplayName("Check if row is set correctly")
    public void testSetRow() {
        int testRow = 2;
        randomPlayer.setRow(testRow);
        assertEquals(testRow, randomPlayer.getRow(), "The row should be set correctly");
    }

    @Test
    @DisplayName("Check if column is set correctly")
    public void testSetColumn() {
        int testColumn = 2;
        randomPlayer.setColumn(testColumn);
        assertEquals(testColumn, randomPlayer.getColumn(), "The column should be set correctly");
    }
}
