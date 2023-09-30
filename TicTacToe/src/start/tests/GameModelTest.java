package start.tests;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import controller.GameController;
import model.GameModel;
import model.Player;
import view.PlayerPanel;


public class GameModelTest {

    private GameController gc;
    private GameModel gameModel;

    
    @BeforeEach
    public void setUp() throws IOException {
    	
        Player leftPlayer;
        Player rightPlayer;
        
        gc = new GameController();
        gameModel = new GameModel(gc);

        // Get the existing PlayerPanel instances from the GameController
        PlayerPanel leftPlayerPanel = gc.getMain().getLeftPlayer();
        PlayerPanel rightPlayerPanel = gc.getMain().getRightPlayer();

        // Mock the Player objects
        rightPlayer = createMock(Player.class);
        leftPlayer = createMock(Player.class);

        // Associate the Player objects with the PlayerPanels
        leftPlayerPanel.setPlayer(leftPlayer);
        rightPlayerPanel.setPlayer(rightPlayer);

        // Configure the mock Player objects to return expected values
        expect(rightPlayer.getName()).andReturn("Hal").anyTimes(); // Use anyTimes() for non-crucial methods
        expect(leftPlayer.getName()).andReturn("Mr.Bean").anyTimes(); // Use anyTimes() for non-crucial methods
        expect(rightPlayer.getWins()).andReturn(0).anyTimes(); // Set initial wins to 0
        expect(leftPlayer.getWins()).andReturn(0).anyTimes();  // Set initial wins to 0

        replay(rightPlayer, leftPlayer); // Start the replay mode for the mocks
  
        
    }

    @Test
    public void testCheckValidInput() {
        assertTrue(gameModel.checkValidInput(1, 1));
        assertTrue(gameModel.checkValidInput(2, 3));
        assertFalse(gameModel.checkValidInput(0, 2));
        assertFalse(gameModel.checkValidInput(4, 4));
        assertFalse(gameModel.checkValidInput(8, 8));
        assertFalse(gameModel.checkValidInput(3, -5));
        assertFalse(gameModel.checkValidInput(-1, 3));
    }




    @ParameterizedTest
    @CsvSource({
            "1, 1, true",
            "2, 3, true",
            "3, 2, true",
            "0, 2, false",
            "4, 4, false",
            "-1, -1, false",
            "1, 0, false",
            "0, 0, false",
            "4, 1, false",
            "3, 4, false"
    })
    public void testCheckValidInput(int row, int column, boolean expected) {
        assertEquals(expected, gameModel.checkValidInput(row, column));
    }
    
    
    @Test
    public void testIsFull() {
       
    	gc.getGameBoard().getBoard()[0][0] = "X";
    	gc.getGameBoard().getBoard()[0][1] = "X";
    	gc.getGameBoard().getBoard()[0][2] = "X";
    	gc.getGameBoard().getBoard()[1][0] = "X";
    	gc.getGameBoard().getBoard()[1][1] = "X";
    	gc.getGameBoard().getBoard()[1][2] = "X";
    	gc.getGameBoard().getBoard()[2][0] = "X";
    	gc.getGameBoard().getBoard()[2][1] = "X";
    	gc.getGameBoard().getBoard()[2][2] = "X";

        assertTrue(gameModel.isFull());
    }


    @Test
    public void testResetBoard() {
        gc.getGameBoard().getCells()[0][0].setChosen(true);
        gc.getGameBoard().getCells()[0][1].setChosen(true);
        gc.getGameBoard().getCells()[1][0].setChosen(true);
        gc.getGameBoard().getCells()[1][1].setChosen(true);

        gameModel.resetBoard();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertFalse(gc.getGameBoard().getCells()[i][j].getChosen());
                assertFalse(gc.getGameBoard().getCells()[i][j].isClicked());
                assertFalse(gc.getGameBoard().getCells()[i][j].isHighlighted());
                assertNull(gc.getGameBoard().getBoard()[i][j]);
                gc.getGameBoard().getCells()[i][j].repaint();
            }
        }
    }

    @Test
    public void testCheckWinnerTie() {
        String[][] board = {
            { "X", "O", "O" },
            { "O", "X", "X" },
            { "X", "O", "O" }
        };
        gc.getGameBoard().setBoard(board);
        gameModel.checkWinner();
        assertFalse(gameModel.isInGame());
        assertEquals(0, gc.getMain().getRightPlayer().getPlayer().getWins());
        assertEquals(0, gc.getMain().getLeftPlayer().getPlayer().getWins());
    }
}
