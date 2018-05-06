import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;

/**
 * Mancala component class; Mancala tracks number of stones it has and knows how to paint itself with its stones.
 * @author Bryan Mira
 */
public class Mancala extends JPanel implements Bucket {
	private int stones;
	private Dimension dim = new Dimension(100, 201);
	private StrategyInterface strategy;

	/**
         * Used by GameModel and MouseListener to check number of stones in a Mancala.
         * @return the number of stones in the Mancala.
         */
	public int countStones() {
		return stones;
	}

	/**
         * Increments the stones in this Mancala by one stone. Used by GameModel when a move is made by player.
         */
	public void addStones() {
		stones++;
	}
        
        /**
         * Unused by Mancala. 
         * @param s unused
         */
	public void removeStolen(int s) {}
        
        /**
         * Decrements the stones in this Mancala by one stone. Used by GameModel when undo is pressed.
         */
	public void removeStone()
	{
		stones--;
	}
	
        /**
         * Used by GameModel to add a given number of stones to a Mancala. Used in special case where stones are stolen.
         * @param stonesStolen number of stones to be added to this Mancala.
         */
	public void addStolen(int stonesStolen) {
		stones += stonesStolen;
		
	}

	/**
         * Adds a concrete strategy that implements StrategyInterface to the Mancala.
         */
	public void removeStones() {
		stones = 0;
	}
        
        /**
         * Adds a concrete strategy, that implements StrategyInterface, to the Mancala.
         * @param s a concrete strategy that implements StrategyInterface.
         */
	public void addStrategy(StrategyInterface s) {
		strategy = s;
	}
        
        /**
         * Uses a concrete strategy to paint a Mancala and its stones.
         * @param g Graphics context.
         */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		strategy.applyStrat(g2, dim);
		//String number = String.format("%d", stones);
		//g2.drawString(number, 40, 40);

		int total = stones;
		int n = 0;
		int i = 0;
		while (total != 0) {

			int col = i * 15 - 2;
			int row = n * 15;
			Ellipse2D.Double stoneShape = new Ellipse2D.Double(col + 20, row + 150, 15, 15);
			g2.fill(stoneShape);
			total--;
			i++;
			if (total % 4 == 0) {
				n--;
				i = 0;
			}
		}
	}
        
        /**
         * Retrieves the dimensions of the Mancala JPanel.
         * @return the dimensions of the Mancala JPanel.
         */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(101, 202);
	}
}