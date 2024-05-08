import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
public class MainView extends JFrame{
	private JPanel topPanel;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu solutionMenu;
	private JMenuItem openFileItem;
	private JMenuItem saveAsCompressedItem;
	private JMenuItem saveSolutionItem;
	private JComboBox setAlgorithm;

	private JPanel centerPanel;
	private DrawMaze stage;

	private JPanel bottomPanel;
	private JButton findSolutionButton;
	private JButton pointBeginingButton;
	private JButton pointEndButton;
	private JButton addIndirectPointButton;

	private AlgoChooserDialog algoDialog;

	class AlgoChooserDialog extends JDialog{
		private JPanel content;
		public AlgoChooserDialog(JPanel parent) {
			//nie wiem jak ustawić rodzica, może być potrzebny JFrame, który jest w MazeApp, można go np. umieścić w polu tej klasy głównej
			super((Frame)null);
			setSize(100,50);
			content=new JPanel();
			content.add(new JLabel("Hello Algo"));
			getRootPane().setContentPane(content);

		}
	}

	public MainView(){
		super("Maze Solver");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000,700);
		setLayout(new BorderLayout());

		add(initTopPanel(), BorderLayout.NORTH);
		add(initCenterPanel(), BorderLayout.CENTER);
		add(initBottomPanel(), BorderLayout.SOUTH);
		setVisible(true);

	}
	private JPanel initTopPanel() {

		topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		menuBar = new JMenuBar();

		fileMenu = new JMenu("Plik");
		solutionMenu = new JMenu("Rozwiązanie");

		JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		topRightPanel.setBackground(Color.LIGHT_GRAY);
		setAlgorithm = new JComboBox(new String[]{"DFS", "BFS", "A*"});
		setAlgorithm.setSelectedIndex(0);
		topRightPanel.add(setAlgorithm);

		openFileItem = new JMenuItem("Otwórz plik z labiryntem");
		saveAsCompressedItem = new JMenuItem("Zapisz labirynt jako plik skompresowany");
		saveSolutionItem = new JMenuItem("Zapisz rozwiązanie");

		fileMenu.add(openFileItem);
		fileMenu.add(saveAsCompressedItem);
		solutionMenu.add(saveSolutionItem);

		menuBar.add(fileMenu);
		menuBar.add(solutionMenu);
		topPanel.add(menuBar);
		topPanel.add(Box.createHorizontalGlue());
		topPanel.setBackground(Color.LIGHT_GRAY);
		topPanel.add(topRightPanel);

		return topPanel;
	}
	private JPanel initCenterPanel(){
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());

		stage = new DrawMaze();
		centerPanel.add(stage, BorderLayout.CENTER);

		return centerPanel;
	}

	private JPanel initBottomPanel(){
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		pointBeginingButton = new JButton("Wskaż początek");
		pointEndButton = new JButton("Wskaż koniec");
		addIndirectPointButton = new JButton("Dodaj punkt pośredni");
		findSolutionButton = new JButton("Znajdź rozwiązanie");

		bottomPanel.add(pointBeginingButton);
		bottomPanel.add(pointEndButton);
		bottomPanel.add(addIndirectPointButton);
		bottomPanel.add(findSolutionButton);
		bottomPanel.setBackground(Color.LIGHT_GRAY);

		return bottomPanel;
	}
	public DrawMaze getMazeStage(){
		return stage;
	}

	AlgoChooserDialog getAlgoDialog(){
		return algoDialog;
	}
	public void addFileListener(ActionListener listener){
		fileMenu.addActionListener(listener);
	}
}
