package view;

import javax.swing.JPanel;

import controller.GameController;
import model.GameModel;

@SuppressWarnings("serial")
public abstract class GamePanel extends JPanel{
	
	protected GameController gc;
	
	protected GamePanel(GameController gc) {
		super();
		this.gc = gc;
	}
	
	protected GameController getController() {
		return this.gc;
	}	

	
	protected GameModel getModel() {
		return gc.getModel();
	}
}
