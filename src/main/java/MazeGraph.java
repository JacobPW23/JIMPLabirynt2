import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;


public class MazeGraph {
    protected Node[][] nodes;
    protected HashMap<Node, ArrayList<Node>> associationLists = new HashMap<>();
    protected Node beginning;
    protected Node end;
    protected int nextNodeNumber = 0;

    public MazeGraph(int width, int height) {
        nodes = new Node[width][height];
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

    public int getNumNodes() {
        return associationLists == null ? 0 : associationLists.size();
    }

    public void setBeginningNode(Node start) {
        beginning = start;
    }

    public void setEndNode(Node end) {
        this.end = end;
    }

    public Node getBeginningNode() {
        return beginning;
    }

    public Node getEndNode() {
        return end;
    }
}