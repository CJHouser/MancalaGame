
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 6/1/2018 20:27 PST
 * @author Charles Houser
 */

public class GameModel {
    private boolean currentPlayer; // true = player A
    private int undoCount;
    private int lastUndoCount;
    private int pitContents[];
    private int lastPitContents[];
    private ArrayList<ChangeListener> listeners;
    
    /**
     * Constructs a BetterGameModel object.
     * @param initialStones the initial number of stones in each pit
     */
    public GameModel( int initialStones ) {
        undoCount = 0;
        lastUndoCount = 0;
        currentPlayer = true;
        pitContents = new int[ 14 ];
        for( int i = 0; i < 14; i++ )
            if( i != 6 && i != 13 )
                pitContents[ i ] = initialStones;
        lastPitContents = null;
        listeners = new ArrayList();
    }
    
    /**
     * Updates the number of stones per pit when a move is made.
     * @param pos the index of pit being played
     */
    public void updatePits( int pos ) {
        // Do not update model if index is of mancala or invalid pit
        if( pos == 6 || pos == 13 )
            return;
        else if( currentPlayer && pos > 6 )
            return;
        else if( !currentPlayer && pos < 6 )
            return;
        
        lastPitContents = pitContents.clone();
        lastUndoCount = undoCount;
        
        int skipIndex = 13;
        int mancalaIndex = 6;
        if( !currentPlayer ) {
            skipIndex = 6;
            mancalaIndex = 13;
        }
        
        int stones = pitContents[ pos ];
        pitContents[ pos ] = 0;
        while( stones > 0 ) {
            pos = ( pos + 1 ) % 14;
            if( pos == skipIndex )
                pos = ( pos + 1 ) % 14;
            pitContents[ pos ]++;
            stones--;
        }
        
        // Stone stealing condition
        if( pitContents[ pos ] == 1 && ( skipIndex + 1 ) % 14 < pos && pos < mancalaIndex ) {
            pitContents[ mancalaIndex ] = pitContents[ mancalaIndex ] + pitContents[ 12 - pos ] + 1;
            pitContents[ 12 - pos ] = 0;
            pitContents[ pos ] = 0;
        }
        // Extra turn condition
        else if( pos == mancalaIndex || ( pos < 0 && mancalaIndex == 13 ) ) {
            currentPlayer = !currentPlayer;
            lastPitContents = null;
        }
        
        // Game over condition and logic
        if( gameOverCondition() ) {
            lastPitContents = pitContents;
            for( int i = ( skipIndex + 1 ) % 14; i < mancalaIndex; i++ ) {
                pitContents[ mancalaIndex ] += pitContents[ i ];
                pitContents[ i ] = 0;
                undoCount = 0;
            }
            for( int i = ( mancalaIndex + 1 ) % 14; i < skipIndex; i++ ) {
                pitContents[ skipIndex ] += pitContents[ i ];
                pitContents[ i ] = 0;
                undoCount = 0;
            }
            
            // GAME OVER SCREEN GOES HERE
        }
        
        notifyListeners();
        currentPlayer = !currentPlayer;
        undoCount = 0;
    }
    
    /**
     * Reverts the last move.
     */
    public void undoLastMove() {
        if( lastPitContents != null && lastUndoCount < 3 ) {
            pitContents = lastPitContents.clone();
            lastPitContents = null;
            
            int temp = undoCount;
            undoCount = lastUndoCount;
            lastUndoCount = temp;
            undoCount++;
            
            currentPlayer = !currentPlayer;
            notifyListeners();
        }
    }
    
    /**
     * Checks if either side of the board has no stones.
     * @return true if the game is over
     */
    private boolean gameOverCondition() {
        boolean ASideEmpty = true;
        boolean BSideEmpty = true;
        // Check for game over (one side has no stones)
        int index = 0;
        while( index < 6 ) {
            if( pitContents[ index ] > 0 ) {
                ASideEmpty = false;
                break;
            }
            index++;
        }
        
        index = 7;
        while( index < 13 ) {
            if( pitContents[ index ] > 0 ) {
                BSideEmpty = false;
                break;
            }
            index++;
        }
        
        return ASideEmpty || BSideEmpty;
    }
    
    public int getContentsAt( int i ) {
        return pitContents[ i ];
    }
    /**
     * Gets the stones in each pit.
     * @return an int array containing the number of stones per pit
     */
    public int[] getPitContents() {
        return pitContents;
    }
    
    /**
     * Gets turn possession.
     * @return true if it is Player A's turn
     */
    public boolean getCurrentPlayer() {
        return currentPlayer;
    }
    
    /**
     * Adds a ChangeListener object to the model.
     * @param cl the ChangeListener being added to the model
     */
    public void attachListener( ChangeListener cl ) {
        listeners.add( cl );
    }
    
    /**
     * Notifies ChangeListeners that a change has been made to the model. 
     */
    public void notifyListeners() {
        for( ChangeListener c : listeners )
            c.stateChanged( new ChangeEvent( this ) );
    }
}