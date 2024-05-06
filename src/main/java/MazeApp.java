import javax.swing.*;

public class MazeApp extends JFrame{
	MazePanel content;
	public MazeApp(){
	
		super("ProZal Maze Solver");
		content = new MazePanel();
		setSize(1000,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(content);
		setVisible(true);


	}

	public static void main(String args[]){
		MazeApp app = new MazeApp();


	}

}
