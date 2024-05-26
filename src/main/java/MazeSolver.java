import java.util.*;

public class MazeSolver {
    private MazeGraph graph;
    private final Stack<Node> solutionStack = new Stack<>();

    public MazeSolver(MazeGraph graph) {
        this.graph = graph;
    }

    //Na razie stworzyłem jeden algorytn - BFS
    public boolean solve() {
        graph.beginning.setVisited(true);
        Queue<Node> queue = new LinkedList<>();
        Map<Node,Node>parentMap = new HashMap<>();
        queue.add(graph.beginning);
        parentMap.put(graph.beginning, null);
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            if (currentNode.equals(graph.end)) {
                constructPath(parentMap, graph.end);
                return true;
            }
            List<Node>adjNodes = new ArrayList<>(graph.getAssociationList(currentNode));
            for (Node adjNode : adjNodes) {
                if (!adjNode.isVisited()) {
                    adjNode.setVisited(true);
                    queue.add(adjNode);
                    parentMap.put(adjNode, currentNode);
                }
            }
        }
        constructPath(parentMap, graph.end);
        return false;
    }

    private void constructPath(Map<Node, Node> parentMap, Node endNode) {
        Node currentNode = endNode;
        while (currentNode != null) {
            solutionStack.push(currentNode);
            currentNode = parentMap.get(currentNode);
        }
    }

    public Stack<Node> getSolutionPath() {
        return solutionStack;
    }

}
