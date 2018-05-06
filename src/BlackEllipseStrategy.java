
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
/**
 * Strategy for game board that uses black ellipses for Buckets.
 * @author Charles Houser
 */
public class BlackEllipseStrategy implements StrategyInterface {
    /**
     * Used to apply a display strategy to a Bucket; Draws black ellipses as shape for Buckets.
     * @param g2 graphics context
     * @param d dimensions of Bucket shape
     */
    public void applyStrat( Graphics2D g2, Dimension d ) {
        g2.setColor( Color.BLACK );
        g2.draw( new Ellipse2D.Double( 0, 0, d.getWidth(), d.getHeight() ) );
    }
}