package view;

import javax.swing.*;
import java.awt.*;

/**
 * The CustomButton class represents a custom JButton that is rendered as a circular button with a question mark icon.
 * It extends the JButton class and overrides the paintComponent method to customize the appearance of the button.
 */
@SuppressWarnings("serial")
public class CustomButton extends JButton {


	private static final int SIZE = 50; // Adjust the size of the button as needed

    /**
     * Constructs a CustomButton with the default settings.
     * The button is set to have a circular shape with a fixed size and a question mark icon.
     */
    public CustomButton() {
        setPreferredSize(new Dimension(SIZE, SIZE));
        setToolTipText("Tutorial"); //tooltip text
        setBorderPainted(false); // Remove the button's border
    }

    /**
     * Overrides the paintComponent method to draw the circular shape and the question mark icon on the button.
     * The method sets the rendering hint for anti-aliasing to improve the visual quality of the button.
     * The circular shape is drawn by filling an oval with the specified background color.
     * The question mark icon is drawn with a specified font, font size, and color at the center of the button.
     *
     * @param g the Graphics object to draw the button
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the background color of the circular button
        g2d.setColor(getBackground());
        g2d.fillOval(0, 0, SIZE - 1, SIZE - 1); // Adjusted to account for the border

        // Draw the question mark icon
        g2d.setColor(getForeground());
        g2d.setFont(new Font("Arial", Font.BOLD, 30)); // Adjust font size and style as needed
        FontMetrics fm = g2d.getFontMetrics();
        int x = (SIZE - fm.stringWidth("?")) / 2;
        int y = ((SIZE - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString("?", x, y);

        g2d.dispose();
    }
}
