import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.ArrayList;
import java.awt.geom.*;
public class MazeStage extends JPanel{
	private MazeReader reader;
	private ArrayList<String> mazeLines;
	private Rectangle2D.Float [][] mazeFields;
	public MazeStage(){
	
	reader= new MazeReader("../resources/20x20.txt");
	reader.readMaze();
	this.mazeLines=reader.getMazeLines();
	mazeFields= new Rectangle2D.Float[mazeLines.size()][mazeLines.get(0).length()];
	}


	@Override
	public void paintComponent(Graphics g){
		Graphics2D comp2D= (Graphics2D) g;
		comp2D.setColor(Color.BLACK);
		comp2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		Iterator<String> it = this.mazeLines.iterator();
		float wallSize=10;

		//wycentrowanie labiryntu
		float xBegining= (getSize().width-mazeLines.get(0).length()*wallSize)/2;
		float yBegining= (getSize().height-mazeLines.size()*wallSize)/2;


		float ay=yBegining;
		float ax=xBegining;
		
		int rowCounter=0;
		while(it.hasNext()){
			String line=it.next();
			for(int i=0;i<line.length();i++){
				
				if(line.charAt(i)=='X'){
					
					mazeFields[rowCounter][i] = new Rectangle2D.Float(ax,ay,wallSize,wallSize);

					comp2D.fill(mazeFields[rowCounter][i]);
				}
				else{
					
					 mazeFields[rowCounter][i]= new Rectangle2D.Float(ax,ay,wallSize,wallSize);
						
				}
				ax+=wallSize;
				
			}
			ax=xBegining;
			ay+=wallSize;
        	rowCounter++;
		}




	}

	/*private void buildMaze(){
		int rows=mazeLines.size();
		int columns=mazeLines.get(0).length();
		if(rows>columns)
		mazeMap=new GridLayout(rows,rows);
		else
			mazeMap=new GridLayout(columns,columns);

		mazeMap=new GridLayout(rows,columns);
		setLayout(mazeMap);
		mazeFields= new JPanel [rows] [columns];
		for(int i=0;i<rows;i++){
			String line = mazeLines.get(i);
			for(int j=0;j<columns;j++){

				mazeFields[i][j]=new JPanel();
				if(line.charAt(j)=='X')
					mazeFields[i][j].setBackground(Color.BLACK);
				add(mazeFields[i][j]);
			}
		}
		



	}
	*/


}
