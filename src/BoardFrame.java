import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * JFrame that displays the Buckets, stones, labels, and undo button.
 * @author Charles Houser
 */
public class BoardFrame extends JFrame {
    private StrategyInterface strategy;
    private int startStone;
    
    /**
     * Initializes strategy and initial stones used by Buckets; calls method to create board.
     * @param strat Concrete strategy that implements StrategyInterface.
     * @param stones Initial number of stones in pits.
     */
    public BoardFrame( StrategyInterface strat, int stones ) {
    	startStone = stones;
        strategy = strat;
        initGUI();
    }
    
    /**
     * Creates the board and Buckets; adds Buckets to GameModel.
     */
    public void initGUI() {
        GameModel model = new GameModel();
        setLayout( new GridBagLayout() );
        GridBagConstraints c = new GridBagConstraints();
        
        JLabel bPitLabel = new JLabel( "B6                            B5                            B4                            B3                            B2                            B1" );
        c.gridx = 2;
        c.gridy = 0;
        add( bPitLabel, c );
        JLabel bLabel = new JLabel( "MANCALA B" );
        c.gridx = 0;
        c.gridy = 1;
        add( bLabel, c );
        
        JLabel aLabel = new JLabel( "MANCALA A" );
        c.gridx = 4;
        c.gridy = 1;
        add( aLabel, c );
        
        JLabel aPitLabel = new JLabel( "A1                            A2                            A3                            A4                            A5                            A6" );
        c.gridx = 2;
        c.gridy = 2;
        add( aPitLabel, c );
        
        JButton undo = new JButton( "Undo" );
        undo.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent ae ) {
                // tell model to undo
            	if (model.getUndoCount() > 0)
            		model.undo();
            }
        });
        c.gridx = 4;
        c.gridy = 2;
        add( undo, c );
        
        JPanel pitPanel = new JPanel( new GridLayout( 2, 6 ) );
        
        // Create and populate a list of buckets
        ArrayList<Bucket> bucketList = new ArrayList();
        for( int i = 0; i < 14; i++ ) {
            Bucket aBucket;
            
            // If pit, add mouselistener
            if( i % 7 == 6 )
                aBucket = new Mancala();
            else {
                aBucket = new Pit( startStone );
                aBucket.addMouseListener( new MouseAdapter() {
                    public void mouseClicked(MouseEvent me) {
                    	Pit clicked = (Pit) me.getSource();
                    	if (model.getPlayerATurn())
                    	{
	                    	for (int i = 0; i < 6; i++)
	                    	{
	                    		if (model.getPlayerAList().get(i) == clicked && model.getPlayerAList().get( i ).countStones() != 0 )
	                    			model.updatePits(i);
	                    	}
                    	}
                    	else if (!model.getPlayerATurn())
                    	{
                    		for (int i = 0; i < 6; i++)
	                    	{
	                    		if (model.getPlayerBList().get(i) == clicked && model.getPlayerBList().get( i ).countStones() != 0)
	                    			model.updatePits(i+7);
	                    	}
                    	}
                    }
                });
            }
            
            model.attach( ( e ) -> { repaint(); } );
            aBucket.addStrategy( strategy );
            bucketList.add( i, aBucket );
        }
        
        model.addPits( bucketList );
        
        // Player A mancala (EAST), Player B mancala (WEST)
        
        c.gridx = 3;
        c.gridy = 1;
        add( (JPanel)bucketList.get( 6 ), c );
        
        c.gridx = 1;
        c.gridy = 1;
        add( (JPanel)bucketList.get( 13 ), c );
        
        //Player B pits (NORTH)
        for( int i = 12; i > 6; i-- )
            pitPanel.add( (JPanel)bucketList.get( i ) );
        
        //Player A pits (SOUTH)
        for( int i = 0; i < 6; i++ )
            pitPanel.add( (JPanel)bucketList.get( i ) );
        
        c.gridx = 2;
        c.gridy = 1;
        add( pitPanel, c );
        
        setResizable( false );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        pack();
        setVisible( true );
    }
}