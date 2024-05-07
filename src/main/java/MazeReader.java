import java.util.ArrayList;
import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner;
import java.util.Iterator;
public class MazeReader{

    private ArrayList<String> mazeLines;
    private String path;

    public MazeReader(String path){
        this.path=path;
        mazeLines = new ArrayList<String>();


    }
    public void readMaze(){
        String data;
        try {
      File myObj = new File(path);
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        data = myReader.nextLine();
        
        mazeLines.add(data);
      }
      myReader.close();
    } 
    
    catch (FileNotFoundException e) {
      System.out.println("Wystąpił błąd");
      e.printStackTrace();
    }
  

    }
  public ArrayList<String> getMazeLines(){

    return this.mazeLines;


  }
  public void printMazeLines(){

    Iterator<String> it = mazeLines.iterator();

    while (it.hasNext()){
      System.out.print(it.next());
      System.out.println();


    }

  }

}