package model;

import java.util.Arrays;

public class PlayerRoster {
    private Player[] player;
    private int numOfPlayers;

    public PlayerRoster() {
        initialization();
    }

    /**
     * This method finds a player in the roster by name.
     * @param name The name of the player to find.
     * @return The Player object if found, null otherwise.
     */
    public Player findPlayer(String name) {
        return Arrays.stream(player)
                     .filter(p -> p != null && p.getName().equals(name))
                     .findFirst()
                     .orElse(null);
    }


    /**
     * This method deletes a player from the roster by name.
     * @param name The name of the player to delete.
     * @return true if the player was found and deleted, false otherwise.
     */
    public boolean deletePlayer(String name) {
        for (int i = 0; i < numOfPlayers; i++) {
            if (player[i].getName().equals(name)) {
                // Move all players after the deleted player one position up
                for (int j = i; j < numOfPlayers - 1; j++) {
                    player[j] = player[j + 1];
                }

                // Clear the last position (duplicate of the previous last player)
                player[numOfPlayers - 1] = null ;

                // Decrease the number of players
                numOfPlayers--;

                return true; // Player was found and deleted
            }
        }
        return false; // Player was not found in the roster
    }

    public boolean checkPlayer(String name) {
        for (int i = 0; i < numOfPlayers; i++) {
            if (player[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void addPlayer(Player newPlayer) {
        player[numOfPlayers] = newPlayer;
        numOfPlayers++;
    }
    
    

    public String[] getPlayersNames() {
        String[] names = new String[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
            names[i] = player[i].getName();
        }
        return names;
    }

    public Player[] getPlayer() {
        return player;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        if (numOfPlayers == 0) {
            initialization();
        } else {
            this.numOfPlayers = numOfPlayers;
            player = Arrays.copyOf(player, 100);
            for (int i = 3; i < numOfPlayers; i++) {
                player[i] = new Player();
            }
        }
    }
    
    private void initialization() {
        this.numOfPlayers = 3;
        player = new Player[100];
        player[0] = PlayerActionFactory.createPlayerAction("Hal", "O");
        player[1] = PlayerActionFactory.createPlayerAction("Mr.Bean", null);
        player[2] = new Player("Beavis");
    }

}