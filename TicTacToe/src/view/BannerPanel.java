package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import controller.GameController;

/**
 * The BannerPanel class represents a panel that displays buttons for various game-related actions.
 * It provides buttons to start a game, add and delete players, quit the application, and finalize game settings.
 */
public class BannerPanel extends JPanel {


	private static final long serialVersionUID = 1L;
	private GameController gc;
	private JButton quitButton;
	private JButton startGameButton;
	private JButton doneButton;
	private JButton addPlayerButton;
	private JButton deletePlayerButton;
	
    /**
     * Constructor for the BannerPanel class.
     *
     * @param gc The GameController instance that manages the game logic and actions.
     */
	public BannerPanel(GameController gc) {
		
		this.gc = gc;
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setPreferredSize(new Dimension(MainWindow.WIDTH,80));
		this.setBorder(new LineBorder(Color.GRAY,1,true));
		this.setBackground(Color.WHITE);
		
		quitButton = new JButton("Quit");
		quitButton.setPreferredSize(new Dimension(100,40));
		quitButton.addActionListener(e->{try {
			gc.quit();
		} catch (Exception e1) {
			e1.printStackTrace();
		}});
		
		startGameButton = new JButton("Start Game");
		startGameButton.setPreferredSize(new Dimension(100,40));
		startGameButton.setEnabled(false);
		startGameButton.addActionListener(e-> gc.startGame());
		
		doneButton = new JButton("Done");
		doneButton.setPreferredSize(new Dimension(100,40));
		doneButton.setEnabled(false);
		doneButton.addActionListener(e-> gc.quitGame());
		
		addPlayerButton = new JButton("Add Player");
		addPlayerButton.setPreferredSize(new Dimension(100,40));
		addPlayerButton.setEnabled(true);
		addPlayerButton.addActionListener(e-> gc.addPlayer());
		
		deletePlayerButton = new JButton("Delete Player");
		deletePlayerButton.setPreferredSize(new Dimension(110,40));
		deletePlayerButton.setEnabled(true);
		deletePlayerButton.addActionListener(e -> gc.deletePlayer());
		
		// Create the circular tutorial button
		CustomButton tutorialButton = new CustomButton();
		tutorialButton.setForeground(Color.BLACK); // Set the question mark color
		tutorialButton.setBackground(Color.WHITE); // Set the button background color
		tutorialButton.addActionListener(e -> gc.showTutorial());
		
		add(addPlayerButton);
		add(deletePlayerButton);
		add(quitButton);
		add(startGameButton);
		add(doneButton);
		add(tutorialButton);
		

	}

	public GameController getGc() {
		return gc;
	}

	public JButton getQuitButton() {
		return quitButton;
	}

	public JButton getStartGameButton() {
		return startGameButton;
	}

	public JButton getDoneButton() {
		return doneButton;
	}

	public JButton getDeletePlayerButton() {
		return deletePlayerButton;
	}

	public void setDeletePlayerButton(JButton deletePlayerButton) {
		this.deletePlayerButton = deletePlayerButton;
	}

	public JButton getAddPlayerButton() {
		return addPlayerButton;
	}

	public void setAddPlayerButton(JButton addPlayerButton) {
		this.addPlayerButton = addPlayerButton;
	}

}