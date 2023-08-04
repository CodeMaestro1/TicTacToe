package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import controller.GameController;

@SuppressWarnings({ "serial" })
public class BoardCell extends GamePanel implements MouseListener {

	protected static final int CELL_PADDING = 10;
	private GameController gc;
	private int row;
	private int collumn;
	private boolean chosen;
	private boolean highlighted;
	private boolean clicked;
	private String mark;
	private MainWindow main;
	
	
	public BoardCell (GameController gc, int row, int collumn) {
		super(gc);
		this.gc = gc;
		this.setBackground(Color.WHITE);
		this.row = row;
		this.collumn = collumn;
		this.addMouseListener(this);
		this.highlighted = false;
		this.setLayout(null);
		this.chosen = false;
		this.setBackground(Color.YELLOW);
	}
	

	public void chooseCell() {
		if(gc.getModel().isInGame() && (!chosen)) {
			clicked = true;
			repaint();
			}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D) g;
	    int size = this.getSize().width - 2 * CELL_PADDING;
	    g2d.setStroke(new BasicStroke(6));

	    if (!clicked) {
	        g2d.setColor(highlighted ? Color.LIGHT_GRAY : Color.YELLOW);
	        g2d.fillRect(CELL_PADDING, CELL_PADDING, size, size);
	    } else {
	        if (gc.getModel().getMover() == 0) {
	            g2d.drawLine(CELL_PADDING, CELL_PADDING, CELL_PADDING + size, CELL_PADDING + size);
	            g2d.drawLine(CELL_PADDING + size, CELL_PADDING, CELL_PADDING, CELL_PADDING + size);
	        } else {
	            g2d.drawOval(CELL_PADDING, CELL_PADDING, size, size);
	        }

	        chosen = true;
	        gc.getGameBoard().getBoard()[this.row][this.collumn] = (gc.getModel().getMover() == 0) ? "X" : "O";
	        if (gc.getModel().isFull()) {
	            gc.getMain().getBannerPanel().getDoneButton().setEnabled(true);
	        }

			gc.getModel().checkWinner();
	        gc.getModel().changeMover();
	    }
	}


	public GameController getGc() {
		return gc;
	}

	public void setGc(GameController gc) {
		this.gc = gc;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCollumn() {
		return collumn;
	}

	public void setCollumn(int collumn) {
		this.collumn = collumn;
	}

	public boolean getChosen() {
		return chosen;
	}

	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}

	public boolean isHighlighted() {
		return highlighted;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}

	public static int getCellPadding() {
		return CELL_PADDING;
	}

	public boolean isClicked() {
		return clicked;
	}


	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}


	public String getMark() {
		return mark;
	}


	public void setMark(String mark) {
		this.mark = mark;
	}




	public MainWindow getMain() {
		return main;
	}


	public void setMain(MainWindow main) {
		this.main = main;
	}


	@Override
	public void mousePressed(MouseEvent e) {
   // Empty implementation for mousePressed method
 }


	@Override
	public void mouseReleased(MouseEvent e) {
	// Empty implementation for mouseReleased method
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(gc.getModel().isInGame() && (!chosen)) {
		clicked=true;
		repaint();
		
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(gc.getModel().isInGame() && (!chosen)) {
		highlighted = true;
		repaint();
		
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(gc.getModel().isInGame() && (!chosen)) {
		highlighted = false;
		repaint();
		
		}
	}
}