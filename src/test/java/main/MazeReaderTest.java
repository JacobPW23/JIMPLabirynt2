import static org.junit.Assert.*;
import javax.swing.*;
import java.awt.*;
import junit.framework.TestCase;
import java.io.*;

public class MazeReaderTest extends TestCase
{
    private Maze maze;
    private MazeReader mr;

    public void setUp(){
        mr=new MazeReader();
        
    }

 
    public void testRead()
    {
        maze=mr.readMaze("./src/main/resources/20x20.txt");
        assertNotNull("Nie wczytano labiryntu",maze );
        assertNotNull("Brak pól",maze.getFields());
        assertNotNull("Brak pól",maze.getFields().get(0));
        assertNotNull("Brak dostępu do Pola o konkretnych współrzędnych",maze.getFieldAt(0,0));
        assertNotNull(maze.getColumnsNumber());
        assertNotNull(maze.getRowsNumber());
        assertEquals(maze.getColumnsNumber(),41);
        assertEquals(maze.getRowsNumber(),41);
        assertEquals(maze.getFieldAt(0,20).getColor(),Color.BLACK);
    }

    public void testBinaryRead(){
    
       maze= mr.readCompressedMaze("./src/main/resources/maze.bin");
        assertNotNull("Brak labiryntu",maze);
        
        assertEquals("Nieprawidłowa liczba kolumn",513,maze.getColumnsNumber());
        assertEquals("Nieprawidłowa liczba wierszy",513,maze.getRowsNumber());
        assertNotNull("Brak pól",maze.fields);
        assertNotNull("Nie wczytano labityntu",maze.lines);
        assertNotEquals("Brak znaków w lini",0,maze.lines.get(0).length());
        assertEquals("Brak początku",'P',maze.lines.get(1).charAt(0));
        assertEquals("Brak końca",'K',maze.lines.get(511).charAt(512));
    }

}