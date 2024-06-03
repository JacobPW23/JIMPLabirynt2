import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Maze {
    public ArrayList<MazeField> fields;
    public ArrayList<ErrorHandler> errorListeners= new ArrayList<ErrorHandler>();
    private int entryX;
    private int entryY;
    private int exitX;
    private int exitY;

    private int columnNumber;
    private int rowNumber;
    public ArrayList<String> lines;
    public List<Node> solutionPath;
    private MazeGraph graph;
    private MazeField lastStartPoint;
    private MazeField lastEndPoint;
    private MazeSolution solution;

    public Maze(ArrayList<String> lines) {
        if (lines.isEmpty()) return;
        this.lines = lines;
        rowNumber = lines.size();
        columnNumber = lines.get(0).length();

        fields = new ArrayList<>();
        for (int j = 0; j < lines.size(); j++) {
            String line = lines.get(j);
            for (int i = 0; i < line.length(); i++)
                fields.add(new MazeField(line.charAt(i), i, j));
        }

        graph = new MazeGraph(columnNumber, rowNumber);
        buildGraph();

        setHoles();
    }

    public void draw(Graphics2D g2D) {
        for (MazeField field : fields) {
            field.draw(g2D);
        }

        if (solution != null) {
            for (Node n : solution.getNodes()) {
                MazeField field = getFieldAt(n.getXCoordinate(), n.getYCoordinate());
                field.setColor(Color.GREEN);
                field.draw(g2D);
            }
        }
    }

    public void buildGraph() {
        createNodes();
        createAssociation();
    }

    public void createAssociation() {
        graph.establishAssociation(graph.getBeginningNode(), graph.getNodeAt(graph.getBeginningNode().getXCoordinate() + 1, graph.getBeginningNode().getYCoordinate()));
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

    protected void createNodes() {
        for (int i = 1; i < lines.size() - 1; i++) {
            String line = lines.get(i);
            int j = 0;
            if (line.charAt(j) == 'P') {
                Node begin = new Node(j++, i);
                graph.addNode(begin);
                graph.setBeginningNode(begin);
            }
            while (j < line.length() - 1) {
                if (line.charAt(j) == ' ') {
                    graph.addNode(new Node(j, i));
                }
                j++;
            }
            if (line.charAt(j) == 'K') {
                Node end = new Node(j, i);
                graph.addNode(end);
                graph.setEndNode(end);
            }
        }
    }

    public void clearSolutionPath() {
        if (solutionPath != null) {
            for (Node n : solutionPath) {
                MazeField field = getFieldAt(n.getXCoordinate(), n.getYCoordinate());
                if (field != null && field.isPath()) {
                    field.setColor(Color.WHITE); // Reset to original color for path
                }
            }
            solutionPath = null;
        }
    }

    public int getColumnsNumber() {
        return this.columnNumber;
    }

    public Point getEntry(){

        return new Point(entryX,entryY);
    }
    public Point getExit(){
        return new Point(exitX,exitY);
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

    public List<Node> getSolutionPath(){
        return this.solutionPath;
    }

    public void setStartPoint(int x, int y) {
        Node start = graph.getNodeAt(x, y);
        if (start != null) {
            graph.setBeginningNode(start);
        } else {
            System.out.println("Niepoprawny punkt początkowy: (" + x + ", " + y + ")");
        }
    }

    public void setEndPoint(int x, int y) {
        Node end = graph.getNodeAt(x, y);
        if (end != null) {
            graph.setEndNode(end);
        } else {
            System.out.println("Niepoprawny punkt końcowy: (" + x + ", " + y + ")");
        }
    }

    public MazeGraph getGraph() {
        return graph;
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


    private void setHoles(){
        String line=null;
        for(int j=0;j<lines.size();j++){
			 line=lines.get(j);
             for(int i=0;i<line.length();i++)
				if(line.charAt(i)=='P'){
                    entryY=j;
                    entryX=i;
                }


				else if(line.charAt(i)=='K'){
                    exitY=j;
                    exitX=i;
                }
        }

    }

    public void resetPathDrawing(){

        for(MazeField field: fields){

                field.setDefaultColor();
            }
    }

    public void setSolution(MazeSolution s){
        solution=s;
    }

    public MazeSolution getSolution(){
        return this.solution;
    }
}
