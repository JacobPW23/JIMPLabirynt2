import java.io.*;
import java.util.ArrayList;
import java.awt.*;
public class MazeWriter{

   
    private static final int FILE_ID=1381122627;
    private static final int SOLUTION_ID=1381122627;
    private static final int SEPARATOR=35;
    private static final int WALL=88;
    private static final int PATH=32;
    private ArrayList<CodeWord> fieldCodeWords;
    private ArrayList<CodeWord> solutionCodeWords;
    private DataOutputStream writer;
    private int wordCounter=0;
    
    public void writeCompressedMaze(Maze maze,String path){
        try{
            writer = new DataOutputStream(new FileOutputStream(new File(path)));
        

        //header
        write4BInLittleEndian(FILE_ID);
        writer.writeByte(27);
        write2BInLittleEndian((char) maze.getColumnsNumber());
        write2BInLittleEndian((char) maze.getRowsNumber());
        Point entry = maze.getEntry();
        Point exit= maze.getExit();
        write2BInLittleEndian((char)(entry.getX()+1));
        write2BInLittleEndian((char)(entry.getY()+1));
        write2BInLittleEndian((char)(exit.getX()+1));
        write2BInLittleEndian((char)(exit.getY()+1));
        //reserved
        write4BInLittleEndian(0);
        write4BInLittleEndian(0);
        write4BInLittleEndian(0);

        int entryX=(int)entry.getX();
        int entryY=(int)entry.getY();
        int exitX=(int)exit.getX();
        int exitY=(int)exit.getY();
        maze.lines.set(entryY,MazeReader.replaceAt(maze.lines.get(entryY),entryX,' '));
		maze.lines.set(exitY,MazeReader.replaceAt(maze.lines.get(exitY),exitX,' '));
        prepareLineWords(maze.lines);
        
        //write4BInLittleEndian(CodeWords.size());
        write4BInLittleEndian(wordCounter);
        
        //offset
        if(maze.getSolution()!=null)
            write4BInLittleEndian(1);
        else
            write4BInLittleEndian(0);
        writer.writeByte(SEPARATOR);
        writer.writeByte(WALL);
        writer.writeByte(PATH);

        
        //field code words
        
        for(CodeWord word:fieldCodeWords){
            word.writeToStream(writer);
            
        }
        
        if(maze.getSolution()!=null){
        prepareSolution(maze.getSolution().getInShortFormat());
        //solution header

        write4BInLittleEndian(FILE_ID);
        writer.writeByte(maze.getSolution().getInShortFormat().size()-1);

        //solution words
        for(CodeWord word:solutionCodeWords)
            word.writeToStream(writer);
        }
         writer.close();
        }
       
        catch(Exception ex){
            ex.printStackTrace();
        }
        
    }

    private void write2BInLittleEndian(char x){
        if(writer!=null){

        try{
        byte[] bytes = new byte[2];
        int length = bytes.length;
        for (int i = 0; i < length; i++){
            bytes[i] = (byte) (x & 0xFF);
            x >>= 8;
        }
        writer.write(bytes,0,2);
        }
        catch (Exception ex){}
            
        }
    }

    private void write4BInLittleEndian(int x){
        
        if(writer!=null){
        try{
        byte[] bytes = new byte[Integer.BYTES];
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            bytes[i] = (byte) (x & 0xFF);
            x >>= 8;
        }
        writer.write(bytes,0,4);
        }
        catch (Exception ex){}
        }

        
    }

    private void prepareLineWords(ArrayList<String> lines){

        
     
        ArrayList<CodeWord> results= new ArrayList<CodeWord>();
        int charCounter=1;
        int wordCounter=0;
        int charsToRead;
        for(String line:lines){
            
            
            for(int i=1;i<line.length();i++){
                 
                if(line.charAt(i)!=line.charAt(i-1)){
                
                    if(i==line.length()-1){
                        results.add(new CodeWord(SEPARATOR,line.charAt(i-1),charCounter));
                        wordCounter+=charCounter%256==0? charCounter/256:(charCounter/256)+1;
                        charCounter=1;

                        results.add(new CodeWord(SEPARATOR,line.charAt(i),1));
                        wordCounter+=1;
                        

                    }

                    else{

                        results.add(new CodeWord(SEPARATOR,line.charAt(i-1),charCounter));
                        wordCounter+=charCounter%256==0? charCounter/256:(charCounter/256)+1;
                        charCounter=1;

                    }
                }
                
                 else{
                    if(i==line.length()-1){
                
                        results.add(new CodeWord(SEPARATOR,line.charAt(i),++charCounter));
                        wordCounter+=charCounter%256==0? charCounter/256:(charCounter/256)+1;
                        charCounter=1;

                    }
                    else{
                        charCounter++;
                    }
                    
                   
                }
                
            }

        }
        this.wordCounter=wordCounter;
        fieldCodeWords=results;
        
    }

    
    
    private void prepareSolution(ArrayList<String> lines){

        //here solution should be divided into words
        // I advise to use the same class CodeWord only without using separator field 
        // And of course it should be remebered that solution step might be longer than 256 fileds so it must be divided into more than single one-byte word
        // in simmilar manner as it is done in prepareLineWords method.

        solutionCodeWords=new ArrayList<CodeWord>();
        for(String instruction: lines){
            solutionCodeWords.add(new CodeWord(-1,instruction.charAt(0),Integer.parseInt(instruction.substring(1))-1));
        }
    }
    

    class CodeWord{
        public int separator;
        public int value;
        public int sumCount;

        public CodeWord(int s,int v,int c){
            separator=s;
            value=v;
            sumCount=c;
        }

        
        public void writeToStream(DataOutputStream writer){

            int cycles= sumCount>256 ? sumCount/256 :0;
            int offset = sumCount%256;
            try{
                for(int i=0;i<cycles;i++){
                    if(separator>=0)
                        writer.writeByte(separator);
                    if(value>=0)
                        writer.writeByte(value);
                    
                    writer.writeByte(255);
                }
                    if(offset!=0){
                        if(separator>=0)
                            writer.writeByte(separator);
                        if(value>=0)
                        writer.writeByte(value);
                        writer.writeByte(offset-1);
                    }
                
            }

            catch(Exception ex){}
        }
        
    }

    public void writePlainSolution(Maze m, String path){

        try{
        BufferedWriter bwriter = new BufferedWriter(new FileWriter(new File(path)));
        
        for(String line:m.getSolution().getInPlainFormat())
            bwriter.write(line,0,line.length());
      
        bwriter.close();
    }
    catch(Exception ex){
        ex.printStackTrace();
    }
    
    }
    
}