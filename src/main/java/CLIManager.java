import java.io.*;
import java.util.ArrayList;
public class CLIManager extends Thread{

    private ArrayList<CLIListener> listeners=new ArrayList<CLIListener>();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    @Override
    public void run(){
      
            listenInput();
        
        
    }

    private void listenInput(){

        while(true){
            try{
                System.out.print("load and find the solution in the provided file >");
                String line=reader.readLine();
                notifyListeners(line);
                
            }
            catch(Exception ex){}
        }
    }

    public void addListener(CLIListener listener){
        listeners.add(listener);
    }

    private void notifyListeners(String path){

        if(!path.isEmpty()){

            for(CLIListener l: listeners)
                l.onCommandEntered(path);
        }
    }
}