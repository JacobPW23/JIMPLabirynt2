import com.sun.tools.javac.Main;

import javax.swing.*;
import java.io.File;

class Controller{
	private MainView view;

	public Controller(MainView view){
		this.view =view;
		view.addOpenFileListener(e -> openFile());
		view.addStartPointListener(e -> setStartPoint());
	}

	private void openFile(){
		System.out.println("Open file");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("src/main/resources"));
		int result = fileChooser.showOpenDialog(view);
		if(result == JFileChooser.APPROVE_OPTION){
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}
	}

	private void setStartPoint(){
		System.out.println("Start point");
	}



}
