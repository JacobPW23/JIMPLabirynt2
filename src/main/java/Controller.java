import com.sun.tools.javac.Main;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;


class Controller{
	private MainView view;

	public Controller(MainView view){

		this.view =view;
		view.addOpenFileListener(e -> openFile());
		view.addStartPointListener(e -> setStartPoint());

	}

	private void openFile(){
		JFileChooser fileDialog= new JFileChooser();

		int val=fileDialog.showOpenDialog(this.view);

		try{

			view.getMazeStage().setMazeLines(MazeReader.readMaze(fileDialog.getSelectedFile().getAbsolutePath()));
			view.getMazeStage().paintComponent(view.getMazeStage().getGraphics());
			view.getStageContainer().revalidate();
			view.getStageContainer().repaint();



		}
		catch(Exception ex){

		}

	}

	private void setStartPoint(){
		System.out.println("Start point");
	}



}
