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
	private JButton startPointButton;
	private JButton endPointButton;
	private JButton addIndirectPointButton;

	private AlgoChooserDialog algoDialog;
	private JScrollPane stageContainer;

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

		

		/*basePanel = new JPanel();
		add(basePanel, BorderLayout.NORTH);
		basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));
		*/
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
		setAlgorithm = new JComboBox<String>(new String[]{"DFS", "BFS", "A*"});
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
	private JScrollPane initCenterPanel(){
		stageContainer=new JScrollPane();
		//stageContainer.setBorder(null);
		stageContainer.getVerticalScrollBar().setUnitIncrement(16);
		
		stageContainer.setAlignmentX(SwingConstants.CENTER);
		stage = new DrawMaze();
		stageContainer.setViewportView(stage);
		stageContainer.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
		add(stageContainer, BorderLayout.CENTER);
		

		return stageContainer;
	}

	private JPanel initBottomPanel(){
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		startPointButton = new JButton("Wskaż początek");
		endPointButton = new JButton("Wskaż koniec");
		addIndirectPointButton = new JButton("Dodaj punkt pośredni");
		findSolutionButton = new JButton("Znajdź rozwiązanie");

		bottomPanel.add(startPointButton);
		bottomPanel.add(endPointButton);
		bottomPanel.add(addIndirectPointButton);
		bottomPanel.add(findSolutionButton);
		bottomPanel.setBackground(Color.LIGHT_GRAY);

		return bottomPanel;
	}
	public DrawMaze getMazeStage(){
		return stage;
	}
	public JScrollPane getStageContainer(){
		return stageContainer;

	}

	AlgoChooserDialog getAlgoDialog(){
		return algoDialog;
	}

	public void addOpenFileListener(ActionListener listener){
		openFileItem.addActionListener(listener);
	}

	public void addStartPointListener(ActionListener listener){
		startPointButton.addActionListener(listener);
	}
}

