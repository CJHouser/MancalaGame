
import java.awt.Color;
import java.awt.Shape;

/**
 * 6/1/2018 20:27 PST
 * @author Charles Houser
 */

public interface Strategy {
    /**
     * Gets a color.
     * @return a color.
     */
    Color getColor();
    /**
     * Gets a shape.
     * @param x x-coordinate of the shape.
     * @param y y-coordinate of the shape.
     * @param w width of the shape.
     * @param h height of the shape.
     * @return a shape with specific coordinates and dimensions.
     */
    Shape getShape( int x, int y, int w, int h );
}