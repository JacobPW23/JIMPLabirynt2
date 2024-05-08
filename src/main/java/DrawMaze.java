import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.ArrayList;
import java.awt.geom.*;
public class DrawMaze extends JPanel{

	private ArrayList<String> mazeLines;
	private Rectangle2D.Float [][] mazeFields;
	private String mazePath;
	public DrawMaze(){
	}

	public void setMazePath(String path){

		mazePath=path;
	}


	public String getMazePath(){

		return mazePath;

	}


	@Override
	public void paintComponent(Graphics g){
		Graphics2D comp2D= (Graphics2D) g;
		if(mazePath!=null){


			this.mazeLines=MazeReader.readMaze(mazePath);
			mazeFields= new Rectangle2D.Float[mazeLines.size()][mazeLines.get(0).length()];
			drawMaze(comp2D);

		}




	}



	private void drawMaze(Graphics2D comp2D){

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
}
