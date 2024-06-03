
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MazePanel extends JPanel {

    public final static int NO_POINTING_MODE = 0;
    public final static int START_POINTING_MODE = 1;
    public final static int END_POINTING_MODE = 2;
    private Maze maze;
    private Dimension size;
    private int pointedFieldRow;
    private int pointedFieldColumn;
    private int POINTING_MODE;
    private Color wallColor;
    private Color pathColor;
    private Color solutionPathColor;
    private Color pointingColor;
    private int drawingXBeginning;
    private int drawingYBeginning;
    private MazeField previousHighlighted;

    public MazePanel() {
        size = getPreferredSize();
        pointedFieldRow = -1;
        pointedFieldColumn = -1;
        POINTING_MODE = NO_POINTING_MODE;

        wallColor = Color.BLACK;
        pathColor = Color.WHITE;
        pointingColor = Color.RED;
        solutionPathColor = Color.GREEN;
        drawingXBeginning = 0;
        drawingYBeginning = 0;
    }

    public void highlightAt(int x, int y) {
        clearHighlighted();

        if (maze != null) {
            int column = (x - drawingXBeginning) / MazeField.wallSize;
            int row = (y - drawingYBeginning) / MazeField.wallSize;
            MazeField field = maze.getFieldAt(column, row);
            if (field != null && field.isPath()) {
                field.setHighlight(true);
                previousHighlighted = field;
                repaint();
            }
        }
    }

    public void clearHighlighted() {

        if(previousHighlighted != null){
        int prevX=(int)(previousHighlighted.getShape().getX())/10;
        int prevY=(int)(previousHighlighted.getShape().getY())/10;
        int startX=maze.getGraph().getBeginningNode().getXCoordinate();
        int startY=maze.getGraph().getBeginningNode().getYCoordinate();
        int endX=maze.getGraph().getEndNode().getXCoordinate();
        int endY=maze.getGraph().getEndNode().getYCoordinate();

        if ( (prevX!=startX || prevY!=startY) && (prevX!=endX  || prevY!=endY)) {
            previousHighlighted.setHighlight(false);
            repaint();
            //System.out.print(""+prevX+" "+prevY);
            previousHighlighted = null;

        }

        }
    }

    public void clearPath() {
        if (maze != null) {
            maze.clearSolutionPath();
            repaint();
        }
    }

    public void setMaze(Maze m) {
        this.maze = m;
        size.setSize(maze.getColumnsNumber() * MazeField.wallSize, maze.getRowsNumber() * MazeField.wallSize);
        setPreferredSize(size);

        revalidate();
        repaint();
    }

    public void setPointingMode(int mode) {
        POINTING_MODE = mode;
    }

    public int getPointingMode() {
        return POINTING_MODE;
    }

    public int getDrawingXBeginning() {
        return drawingXBeginning;
    }

    public int getDrawingYBeginning() {
        return drawingYBeginning;
    }

    public Dimension getMazeSize() {
        if (maze == null) {
            return new Dimension(0, 0);
        } else {
            return new Dimension(maze.getRowsNumber(), maze.getColumnsNumber());
        }
    }

    public Maze getMaze() {
        return maze;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        if (maze != null) {
            drawingXBeginning = (getWidth() - maze.getColumnsNumber() * MazeField.wallSize) / 2;
            drawingYBeginning = (getHeight() - maze.getRowsNumber() * MazeField.wallSize) / 2;

            g2D.translate(drawingXBeginning, drawingYBeginning);
            maze.draw(g2D);
        }
    }
}
