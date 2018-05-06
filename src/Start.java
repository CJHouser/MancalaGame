import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * Displays the start menu where user selects board type and initial stones.
 * @author Bryan Mira
 */
public class Start extends JFrame {

    private JButton button1;
    JComboBox<Integer> numStone = new JComboBox<Integer>();
    JComboBox<String> stratType = new JComboBox<String>();
    
    /**
     * Configures and displays the start menu.
     */
    public Start()
    {
    	super("Welcome to Mancala!");
        JLabel numLabel = new JLabel("Number of stones:");
        numStone.addItem(3);
        numStone.addItem(4);
        
        JLabel stratLabel = new JLabel("Layout Type:");
        stratType.addItem("Round");
        stratType.addItem("Square");

        this.add(numLabel);
        this.add(numStone);
        this.add(stratLabel);
        this.add(stratType);
        this.button1 = new JButton("Start");
        this.button1.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
    		StrategyInterface choice = new BlackEllipseStrategy();
    		if (stratType.getSelectedItem().equals("Square")) {
    			choice = new RedRectangleStrategy();
    		}
    		new BoardFrame(choice,(int)numStone.getSelectedItem());
            }
        });
        this.add(button1);
        this.setLayout(new FlowLayout());
        this.setVisible(true);
        this.pack();
        
    }
}