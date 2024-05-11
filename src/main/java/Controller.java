import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


class Controller{
	private MainView view;
	class MazeMotionAdapter extends MouseAdapter{
			private MainView view;
			
			public MazeMotionAdapter(MainView view){
				this.view=view;
			}
		}


	public Controller(MainView view){

		
		this.view =view;
		view.addOpenFileListener(e -> openFile());
		view.addStartPointListener(e -> setStartPoint());

		view.addOnMazeMouseMovedListener(new MazeMotionAdapter(view){

			
			@Override
			public void mouseMoved(MouseEvent e){

				view.getXLabel().setText("X: "+e.getX());
				view.getYLabel().setText("Y: "+e.getY());
			}
			


		});
		view.addOnMazeMouseExitedListener(new MazeMotionAdapter(view){


			@Override
			public void mouseExited(MouseEvent e){

				view.getXLabel().setText("X: -");
				view.getYLabel().setText("Y: -");
				
			}
		});

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
