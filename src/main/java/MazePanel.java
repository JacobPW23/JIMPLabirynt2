
import javax.swing.*;
import java.awt.*;

public class MazePanel extends JPanel {

    public final static int NO_POINTING_MODE = 0;
    public final static int START_POINTING_MODE = 1;
    public final static int END_POINTING_MODE = 1;
    private Maze maze;
    private String mazePath;
    private Dimension size;
    private int pointedFieldRow;
    private int pointedFieldColumn;
    private int POINTING_MODE;
    private float wallSize;
    private Color wallColor;
    private Color corridorColor;
    private Color solutionPathColor;
    private Color pointingColor;
    private float drawingXBeginning;
    private float drawingYBeginning;
    private MazeField previousHighlighted;


    public MazePanel() {
        size = getPreferredSize();
        pointedFieldRow = -1;
        pointedFieldColumn = -1;
        POINTING_MODE = NO_POINTING_MODE;

        wallColor = Color.BLACK;
        corridorColor = Color.WHITE;
        pointingColor = Color.RED;
        solutionPathColor = Color.ORANGE;
        drawingXBeginning = 0;
        drawingYBeginning = 0;
    }

    public void highlightAt(int column, int row) {
        clearHighlighted();

        if (maze != null) {
            MazeField field = maze.getFieldAt(row, column);
            if (field != null && field.isPath()) {
                field.setHighlight(true);
                previousHighlighted = field;
                repaint();  // Request a repaint to reflect the highlight change
            }
        }

    }


    public void clearHighlighted() {
        if (previousHighlighted != null) {
            previousHighlighted.setHighlight(false);
            repaint();  // Request a repaint to clear the highlight
            previousHighlighted = null;
        }
    }


    public void setMaze(Maze m) {

        maze = m;
        size.setSize(maze.getColumnsNumber() * MazeField.wallSize, maze.getRowsNumber() * MazeField.wallSize);
        setPreferredSize(size);

        //wycentrowanie labiryntu
        drawingXBeginning = (getSize().width - maze.getColumnsNumber() * MazeField.wallSize) / 2;
        drawingYBeginning = (getSize().height - maze.getRowsNumber() * MazeField.wallSize) / 2;
        maze.setBegining(drawingXBeginning, drawingYBeginning);
        repaint();

    }

    public void setPointingMode(int mode) {
        POINTING_MODE = mode;
    }


    public int getPointingMode() {
        return POINTING_MODE;
    }

    public float getDrawingXBeginning() {

        return drawingXBeginning;
    }

    public float getDrawingYBeginning() {
        return drawingYBeginning;
    }

    public Dimension getMazeSize() {

        if (maze == null) {
            return new Dimension(0, 0);
        } else {
            return new Dimension(maze.getRowsNumber(), maze.getColumnsNumber());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        if(maze!=null)
            maze.draw(g2D);
    }

}

