
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 6/1/2018 20:27 PST
 * @author Charles Houser, Bryan Mira
 */

public class MancalaMain {
    public static void main(String[] args) {
        JFrame selectionFrame = new JFrame( "Welcome to Mancala!" );
        
        JLabel numStoneLabel = new JLabel( "Stones per pit:" );
        JComboBox numStoneSelector = new JComboBox<Integer>();
        numStoneSelector.addItem( 3 );
        numStoneSelector.addItem( 4 );
        
        JLabel stratLabel = new JLabel( "UI Look:" );
        JComboBox stratSelector = new JComboBox<String>();
        stratSelector.addItem("Red Rectangle");
        stratSelector.addItem("Black Ellipse");
        
        JButton startButton = new JButton("Start");
        startButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent ae ) {
                Strategy strat;
                switch( (String)stratSelector.getSelectedItem() ) {
                    case "Red Rectangle":
                        strat = new RedRectangleStrategy();
                        break;
                    case "Black Ellipse":
                        strat = new BlackEllipseStrategy();
                        break;
                    default:
                        strat = new RedRectangleStrategy();
                }
                
                new BoardFramev2( new GameModel( (int)numStoneSelector.getSelectedItem() ), strat );
                
                selectionFrame.dispose();
            }
        });
       
        selectionFrame.add( numStoneLabel );
        selectionFrame.add( numStoneSelector );
        selectionFrame.add( stratLabel );
        selectionFrame.add( stratSelector );
        selectionFrame.add( startButton );
        
        selectionFrame.setLayout( new FlowLayout() );
        selectionFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        selectionFrame.setResizable( false );
        selectionFrame.setLocationRelativeTo( null );
        selectionFrame.pack();
        selectionFrame.setVisible( true ) ;
    }
}