package model;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class TicTacToeTutorial extends JFrame {


	private static final long serialVersionUID = 1L;

	public TicTacToeTutorial() {
		
        setTitle("Tic-Tac-Toe Tutorial");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create sections for game rules, controls, and strategies
        JPanel gameRulesPanel = createSection("Game Rules",
        """
        Tic-Tac-Toe is a two-player game played on a 3x3 grid
        The objective is to get three of your symbols (X or O) in a row, horizontally, vertically, or diagonally
        Players take turns placing their symbol on the board until one player wins or the board is full.
        """
        	);

        JPanel controlsPanel = createSection("Controls",
        """
        To make a move, click on an empty cell on the board to place your symbol.
        """
        	);

        JPanel strategiesPanel = createSection("Strategies",
        		"""
        Here are some basic strategies to improve your gameplay:
        - Try to create a fork by placing your symbol in a way that you have two winning paths.
        - Block your opponent's forks to prevent them from creating multiple winning paths.
        - Form defensive lines to block your opponent's potential winning rows, columns, or diagonals.
        """
        	);

        mainPanel.add(gameRulesPanel, BorderLayout.NORTH);
        mainPanel.add(controlsPanel, BorderLayout.CENTER);
        mainPanel.add(strategiesPanel, BorderLayout.SOUTH);
       

        add(mainPanel);
    }

    // Helper method to create a section with a title and content
    private JPanel createSection(String title, String content) {
        JPanel sectionPanel = new JPanel(new BorderLayout());
        sectionPanel.setBorder(BorderFactory.createTitledBorder(title));
        JTextArea contentArea = new JTextArea(content);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setFocusable(false);
        sectionPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
        return sectionPanel;
    }

}

