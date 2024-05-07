
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Controller{
	private MainView view;

	class MActionListener implements ActionListener{
		MainView mainView;
		public MActionListener (MainView v){
			mainView=v;


		}

		public void actionPerformed(ActionEvent e){}

	}
	public Controller(MainView view){

		this.view=view;

		view.addOpenFileListener(new MActionListener(this.view){

			@Override
			public void actionPerformed(ActionEvent e){
				JFileChooser fileDialog= new JFileChooser();

				int val=fileDialog.showOpenDialog(this.mainView);

				try{

					mainView.getMazeStage().setMazePath(fileDialog.getSelectedFile().getAbsolutePath());
					mainView.paintComponents(mainView.getGraphics());

				}
				catch(Exception ex){

				}

			}
		});

		view.addSaveCompressedListener(new MActionListener(this.view){

			@Override
			public void actionPerformed(ActionEvent e){

				JFileChooser fileDialog= new JFileChooser();

				int val=fileDialog.showSaveDialog(this.mainView);

			}
		});
		view.addSaveSolutionListener(new MActionListener(this.view){


			@Override
			public void actionPerformed(ActionEvent e){

				JFileChooser fileDialog= new JFileChooser();

				int val=fileDialog.showSaveDialog(this.mainView);

			}

		});

		view.addAlgoChooseListener(new MActionListener(this.view){

			@Override
			public void actionPerformed(ActionEvent e){
				mainView.getAlgoDialog().show();
			}

		});
	}






}
