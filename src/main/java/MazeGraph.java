import java.util.HashSet;
import java.util.HashMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;



public class MazeGraph{

    protected int nextNodeNumber = 0;
    protected HashMap<Node, HashSet<Edge>> associationLists = new HashMap<>();
    protected Node begining;
    protected Node end;
    public HashSet<Edge> getAssociationList(Node n) {
        HashSet<Edge> s = associationLists == null ? null : associationLists.get(n);
        return s == null ? new HashSet<>() : s;
    }

    public Set<Node> getNodes(){
        return this.associationLists.keySet();
    }
    public Node getNodeAt(double x, double y){
        
        Set<Node> k=associationLists.keySet();
        Iterator<Node> it = k.iterator();
        while(it.hasNext()){
            Node n=it.next();
            if(n.getXCoordinate()==x && n.getYCoordinate()==y){
                return n;
            }
        }
        return null;
    }

       
    public void addEdge(Node first, Node second) {
        addEdge(first, second, 1.0);
    }

    
    public void addEdge(Node first, Node second, double weight) {
        associationLists.get(first).add(new Edge(first, second, weight));
        associationLists.get(second).add(new Edge(second, first, weight));
    }

    public void addNode(Node x){

        if(!associationLists.containsKey(x)){
            associationLists.put(x,new HashSet<Edge>());
            x.setNumber(nextNodeNumber++);

        }
    }



    public int getNumNodes() {
        return associationLists == null ? 0 : associationLists.size();
    }


   /* @Override
    public String toString(){

        String result= new String();
        HashSet<Node> k=associationLists.keySet();
        Iterator<Node> it = k.iterator();
        while(it.hasNext()){
            Node x=it.next();
            result.concat(x.getLabel() + " : " + x.getXCoordinate() +  ", "  x.getYCoordinate() + "\n");
            //result.concat("/t"+ )
            
        }
        return result;
    }
    */

   public void setBeginingNode(Node start){

        begining=start;
   }

   public void setEndNode(Node end){

        this.end=end;
   }

   public Node getBeginingNode(){
     return begining;
   }

   public Node getEndNode(){

        return end;
   }

   public void injectNode(Node injected){

        Set<Node> k=associationLists.keySet();
        if(!associationLists.containsKey(injected)){

            addNode(injected);
            Iterator<Node> kit=k.iterator();
            while(kit.hasNext()){
                Node n=kit.next();
                if(n.isCollinear(injected)){
                    HashSet<Edge> edges =getAssociationList(n);
                    Iterator<Edge> eit=edges.iterator();
                    while(eit.hasNext());
                    {
                        Edge someEdge=eit.next();
                        //może troche niezrozumiały warunek-  wsztrzykujemy tylko, gdy injected leży pomiędzy węzłami
                        if(someEdge.getSecondNode().getYCoordinate()>injected.getYCoordinate() 
                        && someEdge.getFirstNode().getYCoordinate()<injected.getYCoordinate()
                        || someEdge.getSecondNode().getXCoordinate()>injected.getXCoordinate()
                        && someEdge.getFirstNode().getXCoordinate()<injected.getXCoordinate()){

                            
                           
                            //addEdge(n,injected,n.distanceFrom(injected));
                            //addEdge(someEdge.getSecondNode(),injected,injected.distanceFrom(someEdge.getSecondNode()));
                            
                            //edges.remove(someEdge); 
                            //getAssociationList(someEdge.getSecondNode()).remove(someEdge);
                            return;
                        }
                    }
                }


            }

        }

   }
}