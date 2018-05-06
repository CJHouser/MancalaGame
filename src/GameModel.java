import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * GameModel class models the Mancala game logic; Controls the player's stone counts.
 * Represents the Model part of MVC pattern
 * @author Kayla Walton
 */
public class GameModel {
	//data
	private ArrayList<Bucket> pitList;
	private ArrayList<Pit> playerA;
	private ArrayList<Pit> playerB;
	//listeners
	private ArrayList<ChangeListener> listeners;
	private boolean playerAturn;
	private boolean bonusTurn;
	private int lastNumberStones;
	private int undoCountA; //how many times a player has undone
	private int undoCountB;
	private int lastClicked; //possibly use lastBoard instead? just save the whole thing.
	private int stolenStones; //last number of stolen stones
	
        /**
	 * GameModel initializes a list of both pits/mancala and listeners for interaction
	 */
	public GameModel()
	{
		pitList = new ArrayList<>();
		listeners = new ArrayList<>();
		playerAturn = true;
		playerA = new ArrayList<>();
		playerB = new ArrayList<>();
		lastNumberStones = 0;
		undoCountA = 3;
		undoCountB = 3;
	} //end GameModel
        
	/**
	 * Get list of pits from Player A
	 * @return list of 6 pits
	 */
	public ArrayList<Pit> getPlayerAList()
	{
		return playerA;
	} //end getPlayerAList
        
	/**
	 * Get list of pits from Player B
	 * @return list of 6 pits
	 */
	public ArrayList<Pit> getPlayerBList()
	{
		return playerB;
	} //end getPlayerBList
        
	/**
	 * Sets the player A list
	 */
	public void setPlayerAList()
	{
		for (int i = 0; i < 6; i++)
		{
			if (pitList.get(i) instanceof Pit)
				playerA.add((Pit) pitList.get(i));
		}
	}
        
	/**
	 * Sets player B list
	 */
	public void setPlayerBList()
	{
		for (int i = 7; i < 13; i++)
		{
			if (pitList.get(i) instanceof Pit)
				playerB.add((Pit) pitList.get(i));
		}
	}
        
	/**
	 * Gets the player turn.
	 * @return true for player a, false for player b
	 */
	public boolean getPlayerATurn()
	{
		return playerAturn;
	}
        
	/**
	 * Attach a change listener to the model
	 * @param l listener to add
	 */
	public void attach(ChangeListener l)
	{
		listeners.add(l);
	} //end attach
        
	/**
	 * Adds pits to the list and sets the player Lists
	 * @param bList bucketList to add to model
	 */
	public void addPits(ArrayList<Bucket> bList )
	{
		pitList = bList;
		setPlayerAList();
		setPlayerBList();
	}
        
	/**
	 * Changes the number of stones in pits after a play clicks.
	 * @param position pit clicked
         * @return returns false if either side of board is empty; true otherwise
	 */
	public boolean updatePits(int position)
	{
		if (aEmpty() || bEmpty())
			return false;
		//get the number of stones from the chosen pit.
		lastNumberStones = pitList.get(position).countStones();
		if (pitList.get(position) instanceof Pit)
			lastClicked =position;
		pitList.get(position).removeStones();

		//Move the stones into the appropriate pits.
		//player A gets pits 1-6 and mancala 2. 
		if (playerAturn)
		{
			position++;
			for (int i = 1; i < lastNumberStones; i++)
			{
				if (position < pitList.size()-1 )
					pitList.get(position).addStones();
				else
				{
					position = 0;
					pitList.get(position).addStones();
				}
				position++;
			}
			//now check for the last stone count and steal stones if needed.
			if (position < 6 && pitList.get(position).countStones() == 0)
			{
				int bSteal = 12-position;
				stolenStones = pitList.get(bSteal).countStones();
                                pitList.get(bSteal).removeStones();
                                pitList.get(6).addStolen(stolenStones+1);
			}
			else if (position == 14)
				pitList.get(0).addStones();
			else
				pitList.get(position).addStones();
			undoCountB = 3;
		} //end playerA logic
		//player B gets 7-12 and mancala 1, but in reverse....... 
		else
		{
			position++;
			for (int i = 1; i < lastNumberStones; i++)
			{
				if (position < pitList.size() && position != 6)
					pitList.get(position).addStones();
				else
				{
					position = 0;
					pitList.get(position).addStones();
				}
				position++;
			}
			//now check for the last stone count and steal stones if needed.
			if (position > 6 && position < 13 && pitList.get(position).countStones() == 0)
			{
				int aSteal = 12 - position;
				stolenStones = pitList.get(aSteal).countStones();
                                pitList.get(aSteal).removeStones();
                                pitList.get(13).addStolen(stolenStones+1);
			}
			else if (position == 14)
				pitList.get(0).addStones();
			else
				pitList.get(position).addStones();
			undoCountA = 3;
		} //end playerB logic
		bonusTurn = (position == 6) || (position == 13);
		for (ChangeListener l : listeners)
		{
			l.stateChanged(new ChangeEvent(this));
		}
		if (!bonusTurn || aEmpty() || bEmpty())
			changeTurn();
                
                gameOver();
		return true;
		
	} //end updatePits
        
        /**
         * Retrieves the number of undos remaining on a players turn.
         * @return the number of undos remaining
         */
	public int getUndoCount()
	{
		if (playerAturn)
		{
			return undoCountB;
		}
		else
			return undoCountA;
	}
        
	/**
	 * Swaps the flag for player turn. 
	 */
	public void changeTurn()
	{
		playerAturn = !playerAturn;
	}
        
	/**
	 * Check if A side is empty.
	 * @return true if all pit counts == 0
	 */
	public boolean aEmpty()
	{
		for (Pit p: playerA)
		{
			if (p.countStones() != 0)
				return false;
		}
		return true;
	}
        
	/**
	 * Check if B side is empty
	 * @return true if all pit counts == 0
	 */
	public boolean bEmpty()
	{
		for (Pit p: playerB)
		{
			if (p.countStones() != 0)
				return false;
		}
		return true;
	}
        
	/**
	 * Allows a player to undo the last turn
	 */
	public void undo()
	{
		//undo the player tracking flag
		if (!bonusTurn)
			playerAturn = !playerAturn;
		//find where to start removing stones from
		int position = lastClicked+1;
		//if A is able to undo and their turn
		if (undoCountA > 0 && playerAturn) {
			undoCountA--;
			//loop through the number of stones and put them back
			for (int i = 1; i < lastNumberStones; i++)
			{
				if (position < pitList.size()-1)
					pitList.get(position).removeStone();
				else
				{
					position = 0;
					pitList.get(position).removeStone();
				}
				position++;
				
			}
			// if the last position is a steal, then 
			// return the stolen stones and update the mancala
			// then remove the final stone
			if (position < 6 && stolenStones != 0)
			{
				int bReturn = 12-position;
				pitList.get(bReturn).addStolen(stolenStones);
				pitList.get(6).removeStolen(stolenStones+1);
				pitList.get(position).removeStone();
			}
			//if just out of index, update and remove
			else if (position == 14)
				pitList.get(0).removeStone();
			//otherwise, update like a normal stone removal.
			else
				pitList.get(position).removeStone();
			//put stones back into originally clicked pit.
			pitList.get(lastClicked).addStolen(lastNumberStones);
		}
		else if (!playerAturn && undoCountB > 0) {
			undoCountB--;
			//start from the beginning and take a stone out
			for (int i = 1; i < lastNumberStones; i++)
			{
				if (position < pitList.size() && position != 6 )
					pitList.get(position).removeStone();
				else
				{
					position = 0;
					pitList.get(position).removeStone();
				}
				position++;
			}
			//if last pit was one that stole stones, put them back
			//in the appropriate pit, remove from mancala, and update
			//last positional pit.
			if (position > 6 && position < 13 && stolenStones != 0)
			{
				int aReturn = 12 - position;
				pitList.get(aReturn).addStolen(stolenStones);
				pitList.get(13).removeStolen(stolenStones+1);
				pitList.get(position).removeStone();
				pitList.get(position).countStones();
			}
			//if it was a normal index, just set to 0 and move on
			else if (position == 14)
				pitList.get(0).removeStone();
			//otherwise, just remove the stone.
			else
				pitList.get(position).removeStone();
			//put the stones back into the original clicked pit.
			pitList.get(lastClicked).addStolen(lastNumberStones);
		}
		//reset stuff, regardless of player.
		lastNumberStones = 0;
		stolenStones = 0;
		//update the view listeners.
		for (ChangeListener l : listeners)
			l.stateChanged(new ChangeEvent(this));
			
	} //end undo
        
        /**
         *  Game over condition check and logic.
         */
        public void gameOver() {
            boolean isSideEmpty = true;
            for( int i = 0; i < 6; i++ )
                if( pitList.get( i ).countStones() != 0 )
                    isSideEmpty = false;
            
            if( !isSideEmpty ) {
                isSideEmpty = true;
                for( int i = 7; i < 13; i++ ) {
                    if( pitList.get( i ).countStones() != 0 ) {
                        isSideEmpty = false;
                    }
                }
            }
            
            if( isSideEmpty ) {
                for( int i = 0; i < 6; i++ ) {
                    pitList.get( 6 ).addStolen( pitList.get( i ).countStones() );
                    pitList.get( i ).removeStones();
                }

                for( int i = 7; i < 13; i++ ) {
                    pitList.get( 13 ).addStolen( pitList.get( i ).countStones() );
                    pitList.get( i ).removeStones();
                }
                for(ChangeListener l : listeners) {
                    l.stateChanged(new ChangeEvent(this));
                }
            }
        }
}