import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;
import java.util.List;

public class Maze {
    public ArrayList<MazeField> fields;
    public ArrayList<ErrorHandler> errorListeners = new ArrayList<ErrorHandler>();
    public ArrayList<MazeObserver> mazeObservers = new ArrayList<MazeObserver>();
    private int columnNumber;
    private int rowNumber;
    public ArrayList<String> lines;
    public List<Node> solutionPath;

    public Maze(ArrayList<String> lines) {
        if (lines.isEmpty())
            return;
        this.lines = lines;
        rowNumber = lines.size();
        columnNumber = lines.get(0).length();

        fields = new ArrayList<MazeField>();
        for (int j = 0; j < lines.size(); j++) {
            String line = lines.get(j);
            for (int i = 0; i < line.length(); i++)
                fields.add(new MazeField(line.charAt(i), i, j));
        }
    }

    public void draw(Graphics2D g2D) {
        for (MazeField field : fields) {
            field.draw(g2D);
        }

        if(solutionPath != null) {
            for(Node n : solutionPath) {
                MazeField field = getFieldAt(n.getXCoordinate(), n.getYCoordinate());
                field.setColor(Color.GREEN);
                field.draw(g2D);
            }
        }

    }

    public int getColumnsNumber() {
        return this.columnNumber;
    }

    public int getRowsNumber() {
        return this.rowNumber;
    }

    public MazeField getFieldAt(int column, int row) {
        if (column < 0 || column >= columnNumber || row < 0 || row >= rowNumber)
            return null;
        return fields.get(row * columnNumber + column);
    }

    public ArrayList<MazeField> getFields() {
        return fields;
    }

    public void addErrorListener(ErrorHandler handler) {
        errorListeners.add(handler);
    }

    private void notifyErrorListeners(Exception ex) {
        for (ErrorHandler l : errorListeners)
            l.handleError(ex);
    }

    public void setSolutionPath(Stack<Node> solutionPath) {
        this.solutionPath = new ArrayList<>(solutionPath);
    }


    public void defaultBounds(MazeGraph g) {
        for (int j = 0; j < lines.size(); j++) {
            String line = lines.get(j);
            if (line.charAt(0) == 'P')
                g.setBeginningNode(g.getNodeAt(1, j));
            else if (line.charAt(line.length() - 1) == 'K')
                g.setEndNode(g.getNodeAt(line.length() - 2, j));
        }
    }

    public static void main(String[] args) {
        MazeReader reader = new MazeReader();
        Maze maze = reader.readMaze("src/main/resources/1024x1024.txt");
        MazeGraph graph = new MazeGraph(maze);
        double start = System.nanoTime();
        graph.buildGraph();

        double end = System.nanoTime();
        System.out.println("Czas budowy grafu: " + (end - start) / 1000000000 + " s");

    }

}