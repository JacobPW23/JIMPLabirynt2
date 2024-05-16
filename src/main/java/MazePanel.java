
import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.ArrayList;
import java.awt.geom.*;
public class MazePanel extends JPanel{

	public final static int NO_POINTING_MODE=0;
	public final static int START_POINTING_MODE=1;
	public final static int END_POINTING_MODE=1;
	private Maze maze;
	private String mazePath;
	private Dimension size;
	private int pointedFieldRow;
	private int pointedFieldColumn;
	private int POINTING_MODE;
	private float wallSize;
	private Color wallColor;
	private Color corridorColor;
	private Color solutionPathColor;
	private Color pointingColor;
	private float drawingXBegining;
	private float drawingYBegining;
	private MazeField previousHilighted;






	
	public MazePanel(){
		size=getPreferredSize();
		pointedFieldRow=-1;
		pointedFieldColumn=-1;
		POINTING_MODE=NO_POINTING_MODE;
		
		wallColor=Color.BLACK;
		corridorColor=Color.WHITE;
		pointingColor=Color.RED;
		solutionPathColor=Color.ORANGE;
		drawingXBegining=0;
		drawingYBegining=0;
		

	}

	public void highlightAt(int row,int column){
		
		clearHighLighted();
		if(maze!=null){
		maze.getFieldAt(row,column).highlight();
		previousHilighted=maze.getFieldAt(row,column);
		}
	}

	public void clearHighLighted(){
		if(previousHilighted!=null)
			previousHilighted.clearHighlight();
	}


	public void setMaze(Maze m){
		
		maze=m;
		size.setSize(maze.getColumnsNumber()*MazeField.wallSize,maze.getRowsNumber()*MazeField.wallSize);
		setPreferredSize(size);
		
	
		//wycentrowanie labiryntu
		drawingXBegining= (getSize().width-maze.getColumnsNumber()*MazeField.wallSize)/2;
	    drawingYBegining=(getSize().height-maze.getRowsNumber()*MazeField.wallSize)/2;	
		maze.setBegining(drawingXBegining,drawingYBegining);
		maze.setGraphics((Graphics2D) getGraphics());
		paintComponent(getGraphics());
		
	}
	
	public void setPointingMode(int mode){
		POINTING_MODE=mode;
	}


	public int getPointingMode(){
		return POINTING_MODE;
	}

	public float getDrawingXBegining(){

		return drawingXBegining;
	}
	public float getDrawingYBegining(){
		return drawingYBegining;
	}

	public Dimension getMazeSize(){

			if(maze==null){
				return new Dimension(0,0);
			}
			else{
				return new Dimension(maze.getRowsNumber(),maze.getColumnsNumber());
			}
	}

	@Override
	public void paintComponent(Graphics g){
		//super.paintComponent(g);
		Graphics2D comp2D= (Graphics2D) g;
		
		drawMaze(g);





	}

	private void drawMaze(Graphics g){

		Graphics2D comp2D = (Graphics2D)g;
		comp2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		if(maze!=null)
		{
			maze.setGraphics((Graphics2D)getGraphics());
			maze.draw();
		}

}
}

