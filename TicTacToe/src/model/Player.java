package model;

import java.text.DecimalFormat;

public class Player {

	private String name;
	private int totalGames;
	private float score;
	private int wins;
	private int ties;
	private String[][] recentGames;
	private static final DecimalFormat df = new DecimalFormat("#.###"); // formatting for score
	
	

	public Player(String name) {
		
		this.recentGames = new String[5][2];
		for(int a=0; a<5; a++) {
			for(int b=0; b<2; b++) {
				recentGames[a][b]="None";
			}
		}
		
		this.name = name;
		this.totalGames = 0;
		this.score = 0;
		this.wins = 0;
		this.ties = 0;
	}
	
	public Player() {
		this(" ");
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotalGames() {
		return totalGames;
	}

	public void setTotalGames(int totalGames) {
		this.totalGames = totalGames;
	}
	
	
	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	
	public void updateScore() {
	    String formattedScore = df.format((double) 50 * ((2 * this.wins) + this.ties) / this.totalGames);
	    
	    // Replace any commas with periods before parsing as a float
	    formattedScore = formattedScore.replace(",", ".");
	    
	    this.score = Float.parseFloat(formattedScore);
	}


	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getTies() {
		return ties;
	}

	public void setTies(int ties) {
		this.ties = ties;
	}

	public String[][] getRecentGames() {
		return recentGames;
	}

	public void setRecentGame(String player1, String player2) {
		
		for(int a=4; a>0; a--) {
			recentGames[a][0]=getRecentGame(a-1, 0);
			recentGames[a][1]=getRecentGame(a-1, 1);
		}
		
		
		recentGames[0][0]=player1;
		recentGames[0][1]=player2;
		
		
	}
	
	public String getRecentGame(int a , int b) {
		return recentGames[a][b];
	}
	

	public void setRecentGames(String[][] recentGames) {
		this.recentGames = recentGames;
	}
}
