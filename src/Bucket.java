
import java.awt.event.MouseListener;
/**
 * Interface for Mancala and Pit classes.
 * @author Bryan Mira
 */
public interface Bucket {
    
    /**
     * Used by GameModel and MouseListener to check number of stones in a Bucket.
     * @return number of stones in this Bucket.
     */
    int countStones();
    
    /**
     * Used by GameModel to add a given number of stones to a Bucket. Used in special case where stones are stolen.
     * @param s number of stones to be added to this Bucket.
     */
    void addStolen(int s);
    
    /**
     * Used by GameModel to remove stones from a bucket. Used in special case where stones are stolen.
     * @param s number of stones to be removed from this bucket.
     */
    void removeStolen(int s);
    
    /**
     * Increments the stones in this Bucket by one stone. Used by GameModel when a move is made by player.
     */
    void addStones();
    
    /**
     * Decrements the stones in this Bucket by one stone. Used by GameModel when undo is pressed.
     */
    void removeStone();
    
    /**
     * Removes all the stones frome this Bucket. Used by GameModel when a player makes a move.
     */
    void removeStones();
    
    /**
     * Adds a concrete strategy that implements StrategyInterface to the Bucket.
     * @param s A concrete strategy that implements StrategyInterface.
     */
    void addStrategy( StrategyInterface s );
    
    /**
     * Adds a MouseListener to this Bucket (used only by Pit objects) to listen for mouse clicks.
     * @param mouseListener MouseListener object that listens for mouse clicks.
     */
    public void addMouseListener( MouseListener mouseListener );
}