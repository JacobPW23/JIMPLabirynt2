

public class Node {

    private double xCoordinate;
    private double yCoordinate;
    private int number;

    public Node(){}
    public Node(double x, double y){
        xCoordinate=x;
        yCoordinate=y;
    }
    public double getXCoordinate(){
        return xCoordinate;
    }

    public double getYCoordinate(){
        return yCoordinate;
    }


    public int getNumber(){
        return number;
    }


    public void setXCordinate(double x){
        xCoordinate=x;
    }

    public void setYCoordinate(double y){
        yCoordinate=y;
    }
    public void setNumber(int n){
       number=n;
    }

    
    public boolean isCollinear(Node tested){

        if(tested.getXCoordinate()==xCoordinate || tested.getYCoordinate()==yCoordinate)
            return true;
        return false;

    }
   
    public int distanceFrom(Node n){
        return (int) Math.sqrt(Math.pow(n.getXCoordinate()-xCoordinate,2)+Math.pow(n.getYCoordinate()-yCoordinate,2));
    }

    @Override
    public boolean equals(Object o){

        return o instanceof Node && ((Node)o).getXCoordinate()==xCoordinate && ((Node) o).getYCoordinate()==yCoordinate;
    }

    @Override
    public int hashCode(){
      return  xCoordinate>yCoordinate? (int) (13 *xCoordinate+ 67*yCoordinate) : (int) (73*xCoordinate+211*yCoordinate);
    }

    @Override 
    public String toString(){
        return  number+"(" + xCoordinate+", "+ yCoordinate + ")";
    }

}