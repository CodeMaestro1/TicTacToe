package start.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.AiPlayer;

public class AiPlayerTest {

    private AiPlayer ai;

    @BeforeEach
    void setUp() {
        ai = new AiPlayer("O");
    }

    @Test
    @DisplayName("Check best move for row")
    void testBestMove1() {
        String[][] board1 = { { "O", "O", null }, { "×", null, null }, { "×", "×", null } };
        ai.findBestMove(board1, "X");
        assertEquals(0, ai.getBestRow(), "the best row is 0");
        assertEquals(2, ai.getBestCol(), "The best col is 2");
    }

    @Test
    @DisplayName("Check best move for col")
    void testBestMove2() {
        String[][] board2 = { { "×", "Ï", "×" }, { "×", "Ï", "×" }, { "Ï", null, null } };
        ai.findBestMove(board2, "X");
        assertEquals(2, ai.getBestRow(), "the best row is 2");
        assertEquals(1, ai.getBestCol(), "The best col is 1");
    }

    @Test
    @DisplayName("Check best move for dialog")
    void testBestMove3() {
        String[][] board3 = { { null, "Ï", "×" }, { null, "Ï", "×" }, { "×", "X", "Ï" } };
        ai.findBestMove(board3, "X");
        assertEquals(0, ai.getBestRow(), "the best row is 0");
        assertEquals(0, ai.getBestCol(), "The best col is 0");
    }

    @Test
    @DisplayName("Check best move when in a random pos")
    void testBestMove4() {
        String[][] board4 = { { null, null, null }, { "Ï", null, null }, { "×", "Ï", "×" } };
        ai.findBestMove(board4, "X");
        assertEquals(0, ai.getBestRow(), "the best row is 0");
        assertEquals(0, ai.getBestCol(), "The best col is 0");
    }

    @Test
    @DisplayName("Check best move when board is full")
    void testBestMove5() {
        String[][] board5 = { { "Ï", "×", "Ï" }, { "×", "Ï", "×" }, { "×", "Ï", "×" } };
        ai.findBestMove(board5, "X");
        assertEquals(0, ai.getBestRow(), "the best row is -1");
        assertEquals(0, ai.getBestCol(), "The best col is -1");
    }

    @Test
    @DisplayName("Check best move for larger board")
    void testBestMove6() {
        String[][] board6 = { { null, null, null, null }, { null, "Ï", "×", null }, { "×", "Ï", "Ï", "×" },
                { "Ï", "×", null, "×" } };
        ai.findBestMove(board6, "X");
        assertEquals(0, ai.getBestRow(), "the best row is 0");
        assertEquals(0, ai.getBestCol(), "The best col is 0");
    }
}