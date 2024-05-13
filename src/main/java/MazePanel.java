
import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.ArrayList;
import java.awt.geom.*;
public class MazePanel extends JPanel{

	public final static int NO_POINTING_MODE=0;
	public final static int START_POINTING_MODE=1;
	public final static int END_POINTING_MODE=1;
	private ArrayList<String> mazeLines;
	private MazeField [][] mazeFields;
	//private Rectangle2D.Float [][] mazeFields;
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
	private int drawingXBegining;
	private int drawingYBegining;
	private MazeField previousHilighted;






	class MazeField{

		private Color color;
		private Rectangle2D.Float shape;

		public MazeField(Rectangle2D.Float shape,Color color){
				this.color=color;
				this.shape=shape;
		}
		
		public Color getColor(){
			return color;
		}
		public void setColor(Color color){
			this.color=color;
		}

		public Rectangle2D.Float getShape(){
			return shape;
		}

		public void setShape(Rectangle2D.Float shape){
			this.shape=shape;
		}

	}
	public MazePanel(){
		size=getPreferredSize();
		pointedFieldRow=-1;
		pointedFieldColumn=-1;
		POINTING_MODE=NO_POINTING_MODE;
		wallSize=10;
		wallColor=Color.BLACK;
		corridorColor=Color.WHITE;
		pointingColor=Color.RED;
		solutionPathColor=Color.ORANGE;
		drawingXBegining=0;
		drawingYBegining=0;
		

	}

	public void hilightAt(int row,int cloumn){
		pointedFieldColumn=cloumn;
		pointedFieldRow=row;

		if(cloumn<0 || row <0)
		repaint();
		try{
			if(mazeFields[(pointedFieldRow-drawingYBegining)/10][(pointedFieldColumn-drawingXBegining)/10 ].getColor().equals(corridorColor)){
				if(previousHilighted!=null)
					previousHilighted.setColor(corridorColor);
				mazeFields[(pointedFieldRow-drawingYBegining)/10][(pointedFieldColumn-drawingXBegining)/10 ].setColor(pointingColor);
				
				previousHilighted=mazeFields[(pointedFieldRow-drawingYBegining)/10][(pointedFieldColumn-drawingXBegining)/10 ];
			}
			drawMaze(getGraphics());	
			
			
		
		}
		catch (Exception ex){
			
		}
	}



	public void setMazeLines(ArrayList<String> lines){
		if(lines==null){
			System.err.print("BRAK labiryntu");
			return;
		}
		mazeLines=lines;
		


	}
	
	public void setPointingMode(int mode){
		POINTING_MODE=mode;
	}


	public int getPointingMode(){
		return POINTING_MODE;
	}




	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D comp2D= (Graphics2D) g;
		if(mazeLines!=null){
			reinitDrawingConditions();
			drawMaze(g);

		}




	}

	private void reinitDrawingConditions(){
		try{
		mazeFields= new MazeField [mazeLines.size()][mazeLines.get(0).length()];
		size.setSize(mazeLines.get(0).length()*wallSize,mazeLines.size()*wallSize);
		setPreferredSize(size);

		//wycentrowanie labiryntu
		drawingXBegining=(int) (getSize().width-mazeLines.get(0).length()*wallSize)/2;
	    drawingYBegining=(int) (getSize().height-mazeLines.size()*wallSize)/2;
		Iterator<String> it = this.mazeLines.iterator();
		int ay=drawingYBegining;
		int ax=drawingXBegining;

		int rowCounter=0;
		while(it.hasNext()){
			String line=it.next();
			for(int i=0;i<line.length();i++){
				
				if(line.charAt(i)=='X'){

					mazeFields[rowCounter][i] = new MazeField(new Rectangle2D.Float(ax,ay,wallSize,wallSize),wallColor);

					
				}
				else{
					
					
					mazeFields[rowCounter][i]= new MazeField(new Rectangle2D.Float(ax,ay,wallSize,wallSize),corridorColor);

				}
				ax+=wallSize;

			}
			ax=drawingXBegining;
			ay+=wallSize;
			rowCounter++;


		}
		}
		catch(Exception ex){
			System.err.print(ex.getMessage());
		}


	}
	


	

	private void drawMaze(Graphics g){

		Graphics2D comp2D = (Graphics2D)g;
		comp2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		

	for(MazeField lines []: mazeFields)
		for(MazeField f:lines )
		{
			comp2D.setColor(f.getColor());
			comp2D.fill(f.getShape());

		}


}
}

