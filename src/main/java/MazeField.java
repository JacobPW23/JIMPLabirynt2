import java.awt.geom.*;
import java.awt.*;


public class MazeField {

    private Color color;
    private final Color highlightColor=Color.RED;
    private Rectangle2D.Float shape;
    public static final int wallSize = 10;
    private float xBeginning;
    private float yBeginning;
    private boolean isHighlighted = false;
    private char type;

    public MazeField(char type, int i, int j) {
        this.color = (type==' ') ? Color.WHITE : Color.BLACK;
        this.type = type;
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
            g2D.setColor(highlightColor); // Assume highlightColor is defined
        } else {
            g2D.setColor(color); // Assume color is defined
        }
        g2D.fill(shape);
    }

    public void setBeginning(float xBeginning, float yBeginning) {
        this.xBeginning = xBeginning;
        this.yBeginning = yBeginning;
        shape.x += xBeginning;
        shape.y += yBeginning;
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