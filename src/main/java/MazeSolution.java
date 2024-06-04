import java.util.ArrayList;
import java.util.HashMap;
public class MazeSolution{

    private ArrayList<Node> pathNodes;
    private ArrayList<String> plainInstructions;
    private ArrayList<String> shortInstructions;

    public MazeSolution(ArrayList<Node> pathNodes){
        this.pathNodes=pathNodes;
        createPlainFormat();
        createShortFormat();
    }

    public MazeSolution(ArrayList<ArrayList<String>> decompressedSteps,Node start,Node end){
        
        pathNodes= new ArrayList<Node>();
        start.setXCoordinate(1);
        start.setYCoordinate(1);   
        pathNodes.add(start);
        for(ArrayList<String> step:decompressedSteps ){
            
            String direction= step.get(0);
            int baseX=start.getXCoordinate();
            int baseY=start.getYCoordinate();
            int count= Integer.parseInt(step.get(1));
            
            switch(direction){
                case "N":
                    {
                        for(int i=1;i<=count+2;i++)
                            pathNodes.add(new Node(baseX,baseY-i));
                        start=pathNodes.get(pathNodes.size()-1);
                        break;
                    }
                    
                  
                case "S":{

                    for(int i=1;i<=count+2;i++)
                            pathNodes.add(new Node(baseX,baseY+i));
                    start=pathNodes.get(pathNodes.size()-1);
                    break;
                }
                case "E":{
                    for(int i=1;i<=count+2;i++)
                            pathNodes.add(new Node(baseX+i,baseY));
                    start=pathNodes.get(pathNodes.size()-1);
                    break;
                }
                case "W":{
                    for(int i=1;i<=count+2;i++)
                            pathNodes.add(new Node(baseX-i,baseY));
                    start=pathNodes.get(pathNodes.size()-1);
                    break;
                }
            }
        }
        createPlainFormat();
        createShortFormat();
    }
    public ArrayList<String> getInPlainFormat(){

        return this.plainInstructions;
    }
    
    private void createPlainFormat(){
        
          plainInstructions= new ArrayList<String>();
        
          for(int i=1;i<pathNodes.size();i++){

            Node current=pathNodes.get(i);
            int count =1;
            String direction=null;
            try{

                while(i<pathNodes.size()-1 && pathNodes.get(i+1).isAxisAligned(current) ){
                count++;
                i++;
                }
            }
            catch(Exception ex){
                break;
            }
           
            

           direction=direction(current,pathNodes.get(i));
           if(direction!=null){
            String line=direction=direction+" "+count+"\n";
            plainInstructions.add(line);
           }
            
            count=0;
        }
    }

    public ArrayList<String> getInShortFormat(){
       
       return this.shortInstructions;
    }

    private void createShortFormat(){

        shortInstructions= new ArrayList<String>();
        for(int i=0;i<plainInstructions.size();i++){
            String currentPlain=plainInstructions.get(i);

            String shortStep=currentPlain==null || currentPlain=="" ? null : currentPlain.substring(0,1)+currentPlain.substring(currentPlain.lastIndexOf(' ')+1,currentPlain.length()-1);
            shortInstructions.add(shortStep);
            
            
        }
            
    }
    
    private String direction(Node x,Node y){
        String direction;
        if(x.getXCoordinate()>y.getXCoordinate()){
                direction="WEST";
            }
            else if(x.getXCoordinate()<y.getXCoordinate()){
                direction="EAST";
            }
            else if(x.getYCoordinate()<y.getYCoordinate()){
                direction="SOUTH";
            }
            else if(x.getYCoordinate()>y.getYCoordinate()){
                direction="NORTH";
            }
            else return null;

        return direction;
    }

    public ArrayList<Node> getNodes(){
        return this.pathNodes;
    }
}
