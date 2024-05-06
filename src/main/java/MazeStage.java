import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.ArrayList;
import java.awt.geom.*;
public class MazeStage extends JPanel{
	private MazeReader reader;
	private ArrayList<String> mazeLines;
	public MazeStage(){
	
	reader= new MazeReader("../resources/20x20.txt");
	reader.readMaze();
	this.mazeLines=reader.getMazeLines();

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
		

		while(it.hasNext()){
			String line=it.next();
			for(int i=0;i<line.length();i++){
				
				if(line.charAt(i)=='X'){
					
					Rectangle2D.Float wallElem = new Rectangle2D.Float(ax,ay,wallSize,wallSize);
					comp2D.fill(wallElem);
				}
				ax+=wallSize;
			}
			ax=xBegining;
			ay+=wallSize;

		}




	}


	


}
