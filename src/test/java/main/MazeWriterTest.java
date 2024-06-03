import static org.junit.Assert.*;
import javax.swing.*;
import java.awt.*;
import junit.framework.TestCase;
import java.io.*;
import java.util.*;

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
    
    public void testShortSolutionFormat(){
        maze=reader.readMaze("src/main/resources/10x10.txt");
        maze.buildGraph();
        maze.defaultBounds(maze.getGraph());
        MazeSolver solver = new MazeSolver(maze.getGraph());
        assertEquals(solver.solve(),true);
        solver.solve();
        MazeSolution solution = solver.getSolution();
        assertNotNull(solution);
        ArrayList<String> steps= solution.getInShortFormat();
        assertNotNull(steps);
        for(String step: steps){
            assertNotNull(step);

        }
        
        assertNotEquals(steps.size(),0);
        assertEquals(steps.get(0),"S2");
        assertEquals(steps.get(steps.size()-1),"S2");
        
    }

    public void testSolutionDecompress(){

        maze=reader.readMaze("src/main/resources/10x10.txt");
        maze.buildGraph();
        maze.defaultBounds(maze.getGraph());
        MazeSolver solver = new MazeSolver(maze.getGraph());
        assertTrue(solver.solve());
        MazeSolution solution = solver.getSolution();
        assertNotNull(solution);
        maze.setSolution(solution);
        mw.writeCompressedMaze(maze,"./solution-test.bin");
        maze = reader.readCompressedMaze("./solution-test.bin");
        assertNotNull("Nie wczytano labiryntu",maze);
        assertNotNull("Brak rozwiązania po wczytaniu z pliku",maze.getSolution());
        assertNotNull("Brak węzłów",maze.getSolution().getNodes());
        ArrayList<String> steps= solution.getInShortFormat();
        assertNotNull(steps);
        for(String step: steps){
            assertNotNull(step);

        }
        
        assertNotEquals(steps.size(),0);
        assertEquals(steps.get(0),"S2");
        assertEquals(steps.get(steps.size()-1),"S2");

        for(Node n:maze.getSolution().getNodes())
            System.out.print(n);
      
    }
}