import java.awt.*;
import java.util.ArrayList;

public class Maze {

    public ArrayList<MazeField> fields;
    public ArrayList<ErrorHandler> errorListeners = new ArrayList<ErrorHandler>();
    private int columnNumber;
    private int rowNumber;
    public ArrayList<String> lines;

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

    private void notifyListeners(Exception ex) {
        for (ErrorHandler l : errorListeners)
            l.handleError(ex);
    }

    public MazeGraph buildGraph() {
        MazeGraph graph = new MazeGraph();
        createNodes(graph);
        createAssociation(graph);
        return graph;
    }

    public void createAssociation(MazeGraph graph) {
        graph.establishAssociation(graph.getBeginingNode(), graph.getNodeAt(graph.getBeginingNode().getXCoordinate() + 1, graph.getBeginingNode().getYCoordinate()));
        graph.establishAssociation(graph.getEndNode(), graph.getNodeAt(graph.getEndNode().getXCoordinate() - 1, graph.getEndNode().getYCoordinate()));
        for (int i = 1; i < lines.size() - 1; i++) {
            String line = lines.get(i);

            for (int j = 1; j < line.length() - 1; j++) {
                Node current = graph.getNodeAt(j, i);
                graph.establishAssociation(current, graph.getNodeAt(j - 1, i));
                graph.establishAssociation(current, graph.getNodeAt(j + 1, i));
                graph.establishAssociation(current, graph.getNodeAt(j, i - 1));
                graph.establishAssociation(current, graph.getNodeAt(j, i + 1));
            }
        }
    }

    private void createNodes(MazeGraph graph) {

        for (int i = 1; i < lines.size() - 1; i++) {

            String line = lines.get(i);
            int j = 0;
            if (line.charAt(j) == 'P') {

                Node begin = new Node(j++, i);
                graph.addNode(begin);
                graph.setBeginningNode(begin);
            }
            for (; j < line.length() - 1; j++) {
                if (line.charAt(j) == ' ') {
                    graph.addNode(new Node(j, i));
                }
            }

            if (line.charAt(j) == 'K') {
                Node end = new Node(j, i);
                graph.addNode(end);
                graph.setEndNode(end);
            }
        }
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
        Maze maze = reader.readMaze("src/main/resources/100x100.txt");
        MazeGraph graph = maze.buildGraph();

    }
}
