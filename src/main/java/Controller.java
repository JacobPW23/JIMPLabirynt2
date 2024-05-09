import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
		fileDialog.setFileFilter(new FileNameExtensionFilter("Pliki tekstowe, binarne", "bin", "txt"));
		fileDialog.setCurrentDirectory(new File("src/main/resources"));
		int val=fileDialog.showOpenDialog(this.view);

		try{

			if(val==JFileChooser.APPROVE_OPTION){

    			view.getMazeStage().setMazeLines(MazeReader.readMaze(fileDialog.getSelectedFile().getAbsolutePath()));
				view.getMazeStage().paintComponent(view.getMazeStage().getGraphics());
				view.getStageContainer().revalidate();
				view.getStageContainer().repaint();
			}
			



		}
		catch(Exception ex){

		}

	}

	private void setStartPoint(){
		System.out.println("Start point");
	}



}
