import static org.junit.Assert.*;
import javax.swing.*;
import java.awt.*;
import junit.framework.TestCase;
import java.io.*;

public class MazeWriterTest extends TestCase
{
    private Maze maze;
    private MazeWriter mw;
    private MazeReader reader;

    public void setUp(){
        mw=new MazeWriter();
        reader = new MazeReader();
        maze=reader.readMaze("src/main/resources/500x500.txt");
    }

 
  

    public void testBinaryWrite(){
    
        
       mw.writeCompressedMaze(maze,"./test.bin");
       maze=reader.readCompressedMaze("./test.bin");
       assertNotNull("Brak labiryntu",maze);
        
        assertEquals("Nieprawidłowa liczba kolumn",1001,maze.getColumnsNumber());
        assertEquals("Nieprawidłowa liczba wierszy",1001,maze.getRowsNumber());
        assertNotNull("Brak pól",maze.fields);
        assertNotNull("Nie wczytano labityntu",maze.lines);
        assertNotEquals("Brak znaków w lini",0,maze.lines.get(0).length());
        assertEquals("Brak początku",'P',maze.lines.get(1).charAt(0));
        assertEquals("Brak końca",'K',maze.lines.get(999).charAt(1000));
       
    }

}