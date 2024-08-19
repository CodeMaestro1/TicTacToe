package start;

import java.io.IOException;

import controller.GameController;

public class TicTacToe {

	public static void main(String[] args) throws IOException {

		GameController gc = new GameController();
		gc.start();
		
	}
}
