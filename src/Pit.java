import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.*;

/**
 * Pit component class; Pit tracks number of stones it has and knows how to paint itself with its stones.
 * @author Bryan Mira
 */
public class Pit extends JPanel implements Bucket {
	private int stones;
	private Dimension dim = new Dimension(100, 100);
	private StrategyInterface strategy;

	/**
         * Initializes the initial number of stones held by this Pit.
         * @param newStones initial number of stones in pit
         */
	public Pit(int newStones) {
		stones = newStones;
	}
	
        /**
         * Used by GameModel to add a given number of stones to a Pit. Used in special case where stones are stolen and added to this Pit.
         * @param stonesStolen number of stones to be added to this Pit
         */
	public void addStolen(int stonesStolen) {
		stones +=stonesStolen;	
	}
	
        /**
         * Used by GameModel to remove stones from a Pit. Used in special case where stones are stolen from this Pit.
         * @param stonesStolen number of stones to be removed from this Pit
         */
	public void removeStolen(int stonesStolen) {
		stones -= stonesStolen;
	}

	/**
         *  Used by GameModel and MouseListener to check number of stones in a Pit.
         * @return number of stones held in this Pit
         */
	public int countStones() {
		return stones;
	}

	/**
         * Increments the stones in this Pit by one stone. Used by GameModel when a move is made by player.
         */
	public void addStones() {
		stones++;
	}
	
        /**
         * Decrements the stones in this Pit by one stone. Used by GameModel when undo is pressed.
         */
	public void removeStone() {
		stones--;
	}

	/**
         * Removes all the stones frome this Pit. Used by GameModel when a player makes a move.
         */
	public void removeStones() {
		stones = 0;
	}
        
        /**
         * Adds a concrete strategy that implements StrategyInterface to a Pit.
         * @param s concrete strategy that implements StrategyInterface.
         */
	public void addStrategy(StrategyInterface s) {
		strategy = s;
	}
        
        /**
         * Uses a concrete strategy to paint a Pit and its stones.
         * @param g Graphics context.
         */
        @Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		strategy.applyStrat(g2, dim);
		// String number = String.format("%d", stones);
		// g2.drawString(number, 40, 40);
		int total = stones;
		int n = 0;
		while (total != 0) {

			int row = 0;

			if (n % 2 == 1) {
				row = (-1) * row;
			}
			if (n == 0) {
				row = 0;
			}
			if (n == 1) {
				row = -15;
			}
			if (n == 2) {
				row = 15;
			}
			if (n == 3) {
				row = -30;
			}
			if (n == 4) {
				row = 30;
			}
			Ellipse2D.Double stoneShape = new Ellipse2D.Double(0, 0, 0, 0);
			if (total % 5 == 1)
				stoneShape = new Ellipse2D.Double((dim.getWidth() - 15) / 2, (dim.getWidth() - 15) / 2 + row, 15, 15);
			if (total % 5 == 2)
				stoneShape = new Ellipse2D.Double((dim.getWidth() - 15) / 2 + 15, (dim.getWidth() - 15) / 2 + row, 15,
						15);
			if (total % 5 == 3)
				stoneShape = new Ellipse2D.Double((dim.getWidth() - 15) / 2 - 15, (dim.getWidth() - 15) / 2 + row, 15,
						15);
			if (total % 5 == 0)
				stoneShape = new Ellipse2D.Double((dim.getWidth() - 15) / 2 + 30, (dim.getWidth() - 15) / 2 + row, 15,
						15);
			if (total % 5 == 4)
				stoneShape = new Ellipse2D.Double((dim.getWidth() - 15) / 2 - 30, (dim.getWidth() - 15) / 2 + row, 15,
						15);
			g2.fill(stoneShape);
			total--;
			if (total % 5 == 0) {
				n++;
			}

		}
	}
        
        /**
         * Retrieves the dimensions of the Pit JPanel.
         * @return the dimensions of the Pit JPanel.
         */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(101, 101);
	}
}