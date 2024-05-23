import java.util.HashSet;
import java.util.HashMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;



public class MazeGraph{

    protected int nextNodeNumber = 0;
    protected HashMap<Node, HashSet<Node>> associationLists = new HashMap<>();
    protected Node begining;
    protected Node end;



    public HashSet<Node> getAssociationList(Node n) {
        HashSet<Node> s = associationLists == null ? null : associationLists.get(n);
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

       
    
    public void addNode(Node x){

        if(!associationLists.containsKey(x)){
            associationLists.put(x,new HashSet<Node>());
            x.setNumber(nextNodeNumber++);

        }
    }


    public void establishAssociation(Node a, Node b){
        if(associationLists.containsKey(a) && associationLists.containsKey(b)){
            associationLists.get(a).add(b);
            associationLists.get(b).add(a);
        }
    }

    public int getNumNodes() {
        return associationLists == null ? 0 : associationLists.size();
    }


  

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

   

   
}