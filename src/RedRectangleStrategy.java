
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 * 6/1/2018 20:27 PST
 * @author Charles Houser
 */
public class RedRectangleStrategy implements Strategy {
    public Color getColor() {
        return Color.RED;
    }
    
    public Shape getShape( int x, int y, int w, int h ) {
        return new Rectangle2D.Double( x, y , w , h );
    }
}