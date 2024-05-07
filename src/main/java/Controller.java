
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Controller{
	private MainView view;

	class MActionListener implements ActionListener{
		MainView Mview;
		public MActionListener (MainView v){
			Mview=v;


		}

		public void actionPerformed(ActionEvent e){}

	}
	public Controller(MainView view){

		this.view=view;

		view.addOpenFileListener(new MActionListener(this.view){

			@Override
			public void actionPerformed(ActionEvent e){
				JFileChooser fileDialog= new JFileChooser();

				int val=fileDialog.showOpenDialog(this.Mview);

				try{

					Mview.getMazeStage().setMazePath(fileDialog.getSelectedFile().getAbsolutePath());
					Mview.paintComponents(Mview.getGraphics());

				}
				catch(Exception ex){

				}

			}
		});

		view.addSaveCompressedListener(new MActionListener(this.view){

			@Override
			public void actionPerformed(ActionEvent e){

				JFileChooser fileDialog= new JFileChooser();

				int val=fileDialog.showSaveDialog(this.Mview);

			}
		});
		view.addSaveSolutionListener(new MActionListener(this.view){


			@Override
			public void actionPerformed(ActionEvent e){

				JFileChooser fileDialog= new JFileChooser();

				int val=fileDialog.showSaveDialog(this.Mview);

			}

		});


	}






}
