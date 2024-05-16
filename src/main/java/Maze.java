import java.awt.geom.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.*;

public class Maze{

public ArrayList<MazeField> fields;
private int nOfcolumns;
private int nOfRows;
private float xBegining;
private float yBegining;
private Graphics2D g2D;
public ArrayList<String> lines;
public Maze(ArrayList<String> lines){

    this.lines=lines;
    nOfRows=lines.size();
    nOfcolumns=lines.get(0).length();
    if(lines.isEmpty())
        return ;

    fields= new ArrayList<MazeField>();
  

   for(int j=0;j<lines.size();j++){
            String line=lines.get(j);
            for(int i=0;i<line.length();i++)
                fields.add(new MazeField(line.charAt(i),i,j));


    }
    xBegining=0;
    yBegining=0;

}

public int getColumnsNumber(){
    return this.nOfcolumns;
}

public int getRowsNumber(){

    return this.nOfRows;
}

public void setBegining(float xBegining, float yBegining)
{
    this.xBegining=xBegining;
    this.yBegining=yBegining;

    float x=xBegining;float y=yBegining;
    for(int i=0;i<fields.size();i++){
        
        /*if(x%nOfcolumns==0){
            x=xBegining;
            y+=MazeField.wallSize;
        }
        */
        fields.get(i).setBegining(x,y);
        //x+=MazeField.wallSize;
        
    }


}
public void setGraphics(Graphics2D g){
    g2D=g;
     for(int i=0;i<fields.size();i++){
        fields.get(i).setGraphics(g2D);
    }
}

public void draw(){
    for(int i=0;i<fields.size();i++){
        fields.get(i).draw();
    }
}

    public MazeField getFieldAt(int x,int y){
       
       try{
        return fields.get(y/(MazeField.wallSize*nOfcolumns)+(x/MazeField.wallSize)/nOfcolumns );
       }
        catch(Exception ex){
            return null;
        }
       
    }

    public ArrayList<MazeField> getFields(){
        return fields;
    }

}