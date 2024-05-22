import java.awt.geom.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.*;
import java.util.HashSet;

public class Maze{

	private static final int NORMAL_NODE=1;
	private static final int START_NODE=2;
	private static final int END_NODE=3;
	private static final int NOT_NODE=0;
	public ArrayList<MazeField> fields;
	private int nOfcolumns;
	private int nOfRows;
	private float xBegining;
	private float yBegining;
	private Graphics2D g2D;
	public ArrayList<String> lines;
	private HashSet chracteristicCodes=new HashSet();

	public Maze(ArrayList<String> lines){

		this.lines=lines;
		nOfRows=lines.size();
		nOfcolumns=lines.get(0).length();
		if(lines.isEmpty())
			return ;

		fields= new ArrayList<MazeField>();


		for(int j=0;j<lines.size();j++){
			String line=lines.get(j);
			for(int i=0;i<line.length();i++)
				fields.add(new MazeField(line.charAt(i),i,j));


		}
		xBegining=0;
		yBegining=0;
		chracteristicCodes.add(288);
		chracteristicCodes.add(232);
		chracteristicCodes.add(344);
		chracteristicCodes.add(283);
		chracteristicCodes.add(227);
		chracteristicCodes.add(339);
		chracteristicCodes.add(176);
		chracteristicCodes.add(171);
		chracteristicCodes.add(128);
		chracteristicCodes.add(296);
		chracteristicCodes.add(184);
		
	}

	public int getColumnsNumber(){
		return this.nOfcolumns;
	}

	public int getRowsNumber(){

		return this.nOfRows;
	}

	public void setBegining(float xBegining, float yBegining)
	{
		this.xBegining=xBegining;
		this.yBegining=yBegining;

		float x=xBegining;float y=yBegining;
		for(int i=0;i<fields.size();i++){


			fields.get(i).setBegining(x,y);


		}


	}
	public void setGraphics(Graphics2D g){
		g2D=g;
		for(int i=0;i<fields.size();i++){
			fields.get(i).setGraphics(g2D);
		}
	}

	public void draw(){
		for(int i=0;i<fields.size();i++){
			fields.get(i).draw();
		}
	}

	public MazeField getFieldAt(int x,int y){

		try{
			return fields.get(y/(MazeField.wallSize*nOfcolumns)+(x/MazeField.wallSize)/nOfcolumns );
		}
		catch(Exception ex){
			return null;
		}

	}

	public ArrayList<MazeField> getFields(){
		return fields;
	}

	private int mazeNode(int line, int column){


		if(line <1 || column<0 || column>=lines.get(line).length() || line>=lines.size() ||lines.get(line).charAt(column)=='X')
			return NOT_NODE;
		if(lines.get(line).charAt(column)=='K') 
			return END_NODE;
		if(lines.get(line).charAt(column)=='P' )
			return START_NODE;
		int factorNorth=lines.get(line-1).charAt(column);
		int factorWest=lines.get(line).charAt(column-1);
		int factorEast=lines.get(line).charAt(column+1);
		int factorSouth=lines.get(line+1).charAt(column);
		int nodeDeterminant=factorNorth+factorWest+factorEast+factorSouth;


		//wraz z kodem 240 musi zostać spełnony dodatkowy warunek, dlatego został wyłączony ze zbioru
		if((nodeDeterminant == 240 && factorNorth!=factorSouth) || chracteristicCodes.contains(nodeDeterminant)){
			return NORMAL_NODE;
		}
		return NOT_NODE;
	}
	public MazeGraph buildGraph(){

		MazeGraph graph= new MazeGraph();
		createNodes(graph);
		createAssociation(graph);
		return graph;
		
	}

	public void createAssociation(MazeGraph graph){
		graph.establishAssociation(graph.getBeginingNode(),graph.getNodeAt(graph.getBeginingNode().getXCoordinate()+1,graph.getBeginingNode().getYCoordinate()));
		graph.establishAssociation(graph.getEndNode(),graph.getNodeAt(graph.getEndNode().getXCoordinate()-1,graph.getEndNode().getYCoordinate()));
		for(int i=1;i<lines.size()-1;i++){

			String line=lines.get(i);
			
			
			for(int j=1;j<line.length()-1;j++){

				//here it is tried to establish association with all possible neighboors N,E,W,S, establishAssociation method will do it whereever it is only possible;
				Node current=graph.getNodeAt(j,i);
				graph.establishAssociation(current,graph.getNodeAt(j-1,i));
				graph.establishAssociation(current,graph.getNodeAt(j+1,i));
				graph.establishAssociation(current,graph.getNodeAt(j,i-1));
				graph.establishAssociation(current,graph.getNodeAt(j,i+1));
				
			}
		
	}
	}

	private void createNodes(MazeGraph graph){

		for(int i=1;i<lines.size()-1;i++){

			String line=lines.get(i);
			int j=0;
			if(line.charAt(j)=='P'){
				
				Node begin= new Node(j++,i);
				graph.addNode(begin);
				graph.setBeginingNode(begin);
			}
			for(;j<line.length()-1;j++){

				if(line.charAt(j)==' '){

						graph.addNode(new Node(j,i));

				}
			}

			if(line.charAt(j)=='K'){
				Node end =new Node(j,i);
				graph.addNode(end);
				graph.setEndNode(end);
			}
			
		}

	}

	public void defaultBounds(MazeGraph g){

		
		for(int j=0;j<lines.size();j++){
			String line=lines.get(j);
				if(line.charAt(0)=='P')
					g.setBeginingNode(g.getNodeAt(1,j));
				else if(line.charAt(line.length()-1)=='K')
					g.setEndNode(g.getNodeAt(line.length()-2,j));
					
	}
	}
}
