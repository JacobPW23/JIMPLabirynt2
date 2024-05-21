

import java.util.Objects;

/**
 *  Undirected edge in a weighted graph (see equals method)
 *
 * @author jstar
 */
public class Edge implements Comparable<Edge> {   

    private Node first;
    private Node second;
    private double weight;

    public Edge(Node first, Node second, double w) {
        this.first=first;
        this.second=second;
        weight = w;
    }

    
    public Node getFirstNode() {
        return first;
    }

  
    public void setFirstNode(Node node) {
        this.first=node;
    }

   
    public Node getSecondNode() {
        return second;
    }

  
    public void setSecond(Node node) {
        this.second=node;
    }

    
    public double getWeight() {
        return weight;
    }

   
    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return first+ " -<" + weight + ">- " + second;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Edge
                && (((Edge) o).first.equals(first) && ((Edge) o).second.equals(second) || ((Edge) o).first.equals(second) && ((Edge) o).second.equals(first) )
                && ((Edge) o).weight == weight;
    }

    @Override
    public int hashCode() {
        return 7 * first.hashCode() + 17 * second.hashCode() + 251 * Objects.hash(weight);
    }

    @Override
    public int compareTo(Edge o) {
        return o.getWeight() > weight ? -1 : (o.getWeight() == weight ? 0 : 1);
    }

}
