
import java.awt.Dimension;
import java.awt.Graphics2D;
/**
 * Interface for concrete strategies.
 * @author Charles Houser
 */
public interface StrategyInterface {
    /**
     * Used to apply a display strategy to the Buckets.
     * @param g2 graphics context.
     * @param d dimensions of Bucket shape
     */
    void applyStrat( Graphics2D g2, Dimension d );
}