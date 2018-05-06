
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
/**
 * Strategy for game board that uses red rectangles for Buckets.
 * @author Charles Houser
 */
public class RedRectangleStrategy implements StrategyInterface {
    /**
     * Used to apply a display strategy to a Bucket; Draws red rectangles for shape of Buckets
     * @param g2 graphics context
     * @param d dimensions of Bucket shape
     */
    public void applyStrat( Graphics2D g2, Dimension d ) {
        g2.setColor( Color.RED );
        g2.draw( new Rectangle2D.Double( 0, 0, d.getWidth(), d.getHeight() ) );
    }
}