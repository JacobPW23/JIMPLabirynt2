import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class MazeGraph {

    protected HashMap<Point, Node> nodes = new HashMap<>();
    protected HashMap<Node, HashSet<Node>> associationLists = new HashMap<>();
    protected Node beginning;
    protected Node end;
    protected int nextNodeNumber = 0;

    public HashSet<Node> getAssociationList(Node n) {
        HashSet<Node> s = associationLists == null ? null : associationLists.get(n);
        return s == null ? new HashSet<>() : s;
    }

    public Set<Node> getNodes() {
        return this.associationLists.keySet();
    }

    public Node getNodeAt(double x, double y) {
        return nodes.get(new Point((int) x, (int) y));
    }

    public void addNode(Node x) {
        if (!associationLists.containsKey(x)) {
            associationLists.put(x, new HashSet<Node>());
            nodes.put(new Point((int)x.getXCoordinate(), (int)x.getYCoordinate()), x);
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

    public Node getBeginingNode() {
        return beginning;
    }

    public Node getEndNode() {
        return end;
    }


}