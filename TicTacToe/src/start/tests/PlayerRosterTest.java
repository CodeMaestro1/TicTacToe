package start.tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import model.Player;
import model.PlayerRoster;

public class PlayerRosterTest {

    private PlayerRoster roster;
    private static final String RANDOM_PLAYER = "Mr.Bean";
    private static final String HUMAN_PLAYER = "Beavis";
    private static final String AI_PLAYER = "Hal";

    @BeforeEach
    public void setUp() {
        roster = new PlayerRoster();
    }

    @Test
    public void testFindPlayer() {
        Player player1 = roster.findPlayer("Hal");
        assertEquals(AI_PLAYER, player1.getName());

        Player player2 = roster.findPlayer(RANDOM_PLAYER);
        assertEquals(RANDOM_PLAYER, player2.getName());

        assertNull(roster.findPlayer("John"));
    }

    @Test
    public void testCheckPlayer() {
        assertTrue(roster.checkPlayer(AI_PLAYER));
        assertTrue(roster.checkPlayer(RANDOM_PLAYER));
        assertFalse(roster.checkPlayer("John"));
    }

    @Test
    public void testAddPlayer() {
        Player newPlayer = new Player("John");
        roster.addPlayer(newPlayer);

        assertTrue(roster.checkPlayer("John"));
    }

    @Test
    public void testGetPlayersNames() {
        String[] names = roster.getPlayersNames();
        assertEquals(3, names.length);
        assertArrayEquals(new String[]{AI_PLAYER, RANDOM_PLAYER, HUMAN_PLAYER}, names);
    }

    @Test
    public void testGetPlayer() {
        Player[] players = roster.getPlayer();
        assertEquals(100, players.length); 
        assertEquals(AI_PLAYER, players[0].getName());
        assertEquals(RANDOM_PLAYER, players[1].getName());
        assertEquals(HUMAN_PLAYER, players[2].getName());
    }

    @Test
    public void testSetNumOfPlayers() {
        roster.setNumOfPlayers(5);

        assertEquals(5, roster.getNumOfPlayers());
        assertEquals(100, roster.getPlayer().length);
        assertEquals(AI_PLAYER, roster.getPlayer()[0].getName());
        assertEquals(RANDOM_PLAYER, roster.getPlayer()[1].getName());
        assertEquals(HUMAN_PLAYER, roster.getPlayer()[2].getName());
        assertEquals(" ", roster.getPlayer()[3].getName());
        assertEquals(" ", roster.getPlayer()[4].getName());
    }
    
    @Test
    public void testInitialization(){
    	
    	roster.setNumOfPlayers(0);
    	
    	assertEquals(3, roster.getNumOfPlayers());
        assertEquals(100, roster.getPlayer().length);
        assertEquals(AI_PLAYER, roster.getPlayer()[0].getName());
        assertEquals(RANDOM_PLAYER, roster.getPlayer()[1].getName());
        assertEquals(HUMAN_PLAYER, roster.getPlayer()[2].getName());
    }
    
    @ParameterizedTest
    @CsvSource({
            "Mr.Bean, true",
            "Beavis,  true",
            "John  ,  false",
            "Mary  ,  false"
    })
    void testDeletePlayer(String newPlayer, boolean expected) {
        assertEquals(expected, roster.deletePlayer(newPlayer));
    }

}