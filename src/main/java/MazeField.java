import java.awt.geom.*;
import java.awt.*;


public class MazeField {

    private Color color;
    private final Color highlightColor=Color.RED;
    private boolean isHighlighted = false;
    private Rectangle2D.Float shape;
    public static final int wallSize = 10;
    private int row;
    private int column;
    private char type;

    public MazeField(char type, int i, int j) {
        this.color = (type==' ') ? Color.WHITE : Color.BLACK;
        this.type = type;
        this.row = i;
        this.column = j;
        this.shape = new Rectangle2D.Float(i * wallSize, j * wallSize, wallSize, wallSize);
    }

    public boolean isPath() {
        return type == ' ';
    }

    public void setHighlight(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    public void draw(Graphics2D g2D) {
        if (isHighlighted) {
            g2D.setColor(highlightColor);
        } else {
            g2D.setColor(color);
        }
        g2D.fill(new Rectangle2D.Float(shape.x, shape.y, shape.width, shape.height));
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Rectangle2D.Float getShape() {
        return shape;
    }

    public void setShape(Rectangle2D.Float shape) {
        this.shape = shape;
    }
}