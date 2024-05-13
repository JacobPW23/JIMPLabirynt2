import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller{
	private MainView view;
	class MazeMotionAdapter extends MouseAdapter{
			private MainView view;
			


			public MazeMotionAdapter(MainView view){
				this.view=view;
				
			}


			@Override
			public void mouseMoved(MouseEvent e){
				
				view.setCurrentCordinates(e.getY(),e.getX());
				updatePointer(e.getY(),e.getX());
				
			}


			@Override
			public void mouseExited(MouseEvent e){

				view.setNoCordinates();
				clearPointer();
			}
			public void updatePointer(int x,int y){
				if(view.getMazeStage().getPointingMode()!=MazePanel.NO_POINTING_MODE){
					view.getMazeStage().highlightAt(x,y);
					
					//System.out.print(x+","+y+"\n");
					
				}
			}


			public void clearPointer(){
				view.getMazeStage().highlightAt(-1,-1);
				view.getMazeStage().repaint();
			}
		}

		class MazeKeyAdapter extends KeyAdapter{
				private MainView view;
			


			public MazeKeyAdapter(MainView view){
				this.view=view;
				
			}
			@Override
			public void keyTyped(KeyEvent e){
				
				if(e.getKeyChar()==27){
						
						view.getMazeStage().setPointingMode(MazePanel.NO_POINTING_MODE);
						view.getMazeStage().highlightAt(-1,-1);
						view.unlockPointButtons();
						
					}

			}
		}


	public Controller(MainView view){

		
		this.view =view;
		view.addOpenFileListener(e -> openFile());
		view.addStartPointListener(e -> setStartPoint());
		view.addEndPointListener(e-> setEndPoint());
		view.addAlgoChangeListener(e-> updatelgoDescription());

		view.addOnMazeMouseMovedListener(new MazeMotionAdapter(view));
		
		view.addOnMazeMouseExitedListener(new MazeMotionAdapter(view));

		view.addPointingModeEscapeListener(new MazeKeyAdapter(view));
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
		view.getMazeStage().setPointingMode(MazePanel.START_POINTING_MODE);
		view.lockPointButtons();
		view.requestFocus();

	}

	private void setEndPoint(){
		view.getMazeStage().setPointingMode(MazePanel.END_POINTING_MODE);
		view.lockPointButtons();
		view.requestFocus();

	}

	public void updatelgoDescription(){
		view.setAlgoDescription();
	}

}
