import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;


public class MazeGraph {
    private Maze maze;
    protected Node[][] nodes;
    protected HashMap<Node, ArrayList<Node>> associationLists = new HashMap<>();
    protected Node beginning;
    protected Node end;
    protected int nextNodeNumber = 0;

    public MazeGraph(Maze maze) {
        this.maze = maze;
        this.nodes = new Node[maze.getColumnsNumber()][maze.getRowsNumber()];
    }

    public ArrayList<Node> getAssociationList(Node n) {
        ArrayList<Node> s = associationLists == null ? null : associationLists.get(n);
        return s == null ? new ArrayList<Node>() : s;
    }

    public Set<Node> getNodes() {
        return this.associationLists.keySet();
    }

    public Node getNodeAt(int x, int y) {
        return nodes[x][y];
    }

    public void addNode(Node x) {
        if (!associationLists.containsKey(x)) {
            associationLists.put(x, new ArrayList<>());
            nodes[x.getXCoordinate()][x.getYCoordinate()] = x;
            x.setNumber(nextNodeNumber++);
        }
    }

    public void establishAssociation(Node a, Node b) {
        if (associationLists.containsKey(a) && associationLists.containsKey(b)) {
            associationLists.get(a).add(b);
            associationLists.get(b).add(a);
        }
    }

    public void buildGraph() {
        createNodes();
        createAssociation();
    }

    public void createAssociation() {
        establishAssociation(getBeginingNode(), getNodeAt(getBeginingNode().getXCoordinate() + 1, getBeginingNode().getYCoordinate()));
        establishAssociation(getEndNode(), getNodeAt(getEndNode().getXCoordinate() - 1, getEndNode().getYCoordinate()));
        for (int i = 1; i < maze.lines.size() - 1; i++) {
            String line = maze.lines.get(i);

            for (int j = 1; j < line.length() - 1; j++) {
                Node current = getNodeAt(j, i);
                establishAssociation(current, getNodeAt(j - 1, i));
                establishAssociation(current, getNodeAt(j + 1, i));
                establishAssociation(current, getNodeAt(j, i - 1));
                establishAssociation(current, getNodeAt(j, i + 1));
            }
        }
    }

    protected void createNodes() {

        for (int i = 1; i < maze.lines.size() - 1; i++) {

            String line = maze.lines.get(i);
            int j = 0;
            if (line.charAt(j) == 'P') {
                Node begin = new Node(j++, i);
                addNode(begin);
                setBeginningNode(begin);
            }
            while (j < line.length() - 1) {
                if (line.charAt(j) == ' ') {
                    addNode(new Node(j, i));
                }
                j++;
            }

            if (line.charAt(j) == 'K') {
                Node end = new Node(j, i);
                addNode(end);
                setEndNode(end);
            }
        }
    }

    public int getNumNodes() {
        return associationLists == null ? 0 : associationLists.size();
    }

    public void setBeginningNode(Node start) {
        beginning = start;
    }

    public void setEndNode(Node end) {
        this.end = end;
    }

    public Node getBeginingNode() {
        return beginning;
    }

    public Node getEndNode() {
        return end;
    }


}