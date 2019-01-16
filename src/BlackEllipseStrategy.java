
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 * 6/1/2018 20:27 PST
 * @author Charles Houser
 */

public class BlackEllipseStrategy implements Strategy {
    public Color getColor() {
        return Color.BLACK;
    }
    
    public Shape getShape( int x, int y, int w, int h ) {
        return new Ellipse2D.Double( x, y , w , h );
    }
}