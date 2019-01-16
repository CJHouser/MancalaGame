
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 6/8/2018 13:40 PST
 * @author Charles Houser
 */

public class BoardFramev2 extends JFrame {
    private GameModel model;
    private Strategy strategy;
    private ArrayList<Pit> pitList;
    
    public BoardFramev2( GameModel m, Strategy s ) {
        model = m;
        strategy = s;
        pitList = new ArrayList<Pit>();
        
        int[] pitContents = model.getPitContents();
        for( int i = 0; i < pitContents.length; i++ )
            pitList.add( new Pit( i ) );
        
        pitList.get( 6 ).setPreferredSize( new Dimension( 64, 128 ) );
        pitList.get( 13 ).setPreferredSize( new Dimension( 64, 128 ) );
        
        setLayout( new GridBagLayout() );
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add( new BoardFramev2.UpperPanel(), gbc );
        
        gbc.gridy = 1;
        add( new BoardFramev2.PitPanel(), gbc );
        
        gbc.gridy = 2;
        add( new BoardFramev2.LowerPanel(), gbc );
        
        gbc.gridy = 3;
        JButton undo = new JButton( "Undo" );
        undo.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent ae ) {
                model.undoLastMove();
            }
        });
        add( undo, gbc );
        
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        pack();
        setVisible( true );
    }
    
    private class PitPanel extends JPanel {
        private PitPanel() {
            setLayout( new GridBagLayout() );
            GridBagConstraints gbc = new GridBagConstraints();
            
            // Pits
            for( int i = 0; i < ( pitList.size() / 2 ) - 1; i++ ) {
                gbc.gridx = i + 1;
                gbc.gridy = 0;
                add( pitList.get( 12 - i ), gbc );
                gbc.gridy = 1;
                add( pitList.get( i ), gbc );
            }
            
            // Mancala
            gbc.gridheight = 2;
            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.gridx = 7;
            gbc.gridy = 0;
            add( pitList.get( 6 ), gbc );
            gbc.gridx = 0;
            gbc.gridy = 0;
            add( pitList.get( 13 ), gbc );
            
            model.attachListener( ( e ) -> { repaint(); } );
        }
    }
    
    private class Pit extends JComponent {
        private final int index;
        private Pit( int i ) {
            index = i;
            setPreferredSize( new Dimension( 64, 64 ) );
            addMouseListener( new MouseAdapter() {
                @Override
                public void mouseClicked( MouseEvent me ) {
                    model.updatePits( index );
                }
            });
        }
        
        @Override
        public void paintComponent( Graphics g ) {
            super.paintComponent( g );
            Graphics2D g2 = (Graphics2D) g;
            
            g2.setColor( strategy.getColor() );
            g2.draw( strategy.getShape( 0, 0, getWidth() - 1, getHeight() - 1 ) );
            int stones = model.getContentsAt( index );
            if( stones < 10 ) {
                GeneralPath path = new GeneralPath();
                int k = 0;
                for( int j = 0; j < stones; j++ ) {
                    if( j > 0 && j % 3 == 0 )
                        k++;
                    path.append( strategy.getShape( 15 * ( j % 3 ), k * 15, 10, 10 ), false );
                }
                g2.translate( getWidth()/2 - path.getBounds().getWidth() / 2, getHeight() / 2 - path.getBounds().getHeight() / 2 );
                g2.draw( path );
            }
            else {
                String str = Integer.toString( stones );
                FontMetrics metrics = g2.getFontMetrics(g2.getFont());
                
                g2.drawString( str,
                        ( getWidth() - metrics.stringWidth( str ) ) / 2,
                        metrics.getAscent() + ( ( getHeight() - metrics.getHeight() ) / 2 ) );
            }
        }
    }
    
    private class UpperPanel extends JPanel {
        private UpperPanel() {
            setPreferredSize( new Dimension( 513, 12 ) );
        }
        
        @Override
        public void paintComponent( Graphics g ) {
            
            int shapeWidth = getWidth() / 8;
            Graphics2D g2 = (Graphics2D) g;
            
            for( int i = 6; i > 0; i-- ) {
                g2.drawString( "B" + ( 7 - i ), ( shapeWidth * i ) + shapeWidth / 2 - 8, 10 );
            }
        }
    }
    
    private class LowerPanel extends JPanel {
        private LowerPanel() {
            setPreferredSize( new Dimension( 513, 12 ) );
        }
        
        @Override
        public void paintComponent( Graphics g ) {
            
            int shapeWidth = getWidth() / 8;
            Graphics2D g2 = (Graphics2D) g;
            
            for( int i = 1; i < 7; i++ ) {
                g2.drawString( "A" + i, ( shapeWidth * i ) + shapeWidth / 2 - 8, 10 );
            }
        }
    }
}