import java.util.Objects;

public class Node {
    private int xCoordinate;
    private int yCoordinate;
    private int number;
    private boolean isVisited;

    public Node(int x, int y) {
        xCoordinate = x;
        yCoordinate = y;
        isVisited = false;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public int getNumber() {
        return number;
    }

    public void setYCoordinate(int y) {
        yCoordinate = y;
    }

    public void setXCoordinate(int x){
        this.xCoordinate=x;
    }
    public void setNumber(int n) {
        number = n;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public boolean isAxisAligned(Node tested) {
        return tested.getXCoordinate() == xCoordinate || tested.getYCoordinate() == yCoordinate;
    }

    public double distanceFrom(Node n) {
        return Math.sqrt(Math.pow(n.getXCoordinate() - xCoordinate, 2) + Math.pow(n.getYCoordinate() - yCoordinate, 2));
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Node && ((Node) o).getXCoordinate() == xCoordinate && ((Node) o).getYCoordinate() == yCoordinate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoordinate, yCoordinate);
    }

    @Override
    public String toString() {
        return number + "(" + xCoordinate + ", " + yCoordinate + ")";
    }

}