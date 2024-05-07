import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
public class MainView extends JPanel{
	private JPanel basePanel;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu solutionMenu;
	private JMenu algorithmMenu;
	private JMenuItem openFileItem;
	private JMenuItem saveAsCompressedItem;
	private JMenuItem saveSolutionItem;
	private JMenuItem setAlgoItem;
	private JPanel menuPanel;
	private JPanel activitiesPanel;
	private MazeStage stage;
	private JButton findSolutionButton;
	private JButton pointBeginingButton;
	private JButton pointEndButton;
	private JButton addIndirectPointButton;
	private JPanel bottomPanel;
	private JTextField xTextField;
	private JTextField yTextField;
	private JLabel yLabel;
	private JLabel xLabel;
	private AlgoChooserDialog algoDialog;

	class AlgoChooserDialog extends JDialog{
		private JPanel content;
		public AlgoChooserDialog(JPanel parent){
			//nie wiem jak ustawić rodzica, może być potrzebny JFrame, który jest w MazeApp, można go np. umieścić w polu tej klasy głównej
			super((Frame)null);
			setSize(100,50);
			content=new JPanel();
			content.add(new JLabel("Hello Algo"));
			getRootPane().setContentPane(content);

		}






	}

	public MainView(){
		setLayout(new BorderLayout(0, 0));

		basePanel = new JPanel();
		add(basePanel, BorderLayout.NORTH);
		basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));

		initMenuPanel();
		initActivitiesPanel();
		initBottomPanel();
		stage = new MazeStage();
		add(stage, BorderLayout.CENTER);
		algoDialog= new AlgoChooserDialog(this);


	}
	private void initMenuPanel(){
		menuBar = new JMenuBar();


		fileMenu = new JMenu("Plik");
		fileMenu.setHorizontalAlignment(SwingConstants.LEFT);
		menuBar.add(fileMenu);

		openFileItem = new JMenuItem("Otwórz plik z labiryntem");
		openFileItem.setHorizontalAlignment(SwingConstants.LEFT);
		fileMenu.add(openFileItem);



		saveAsCompressedItem = new JMenuItem("Zapisz labirynt jako plik skompresowany");
		saveAsCompressedItem.setHorizontalAlignment(SwingConstants.LEFT);
		fileMenu.add(saveAsCompressedItem);

		solutionMenu = new JMenu("Rozwiązanie");
		menuBar.add(solutionMenu);

		saveSolutionItem = new JMenuItem("Zapisz rozwiązanie");
		solutionMenu.add(saveSolutionItem);

		menuPanel = new JPanel();
		basePanel.add(menuPanel);
		menuPanel.setLayout(new GridLayout(0, 1, 0, 0));
		menuPanel.add(menuBar);

		algorithmMenu = new JMenu("Algorytm");
		menuBar.add(algorithmMenu);

		setAlgoItem = new JMenuItem("Ustaw algorytm rozwiązujący");
		algorithmMenu.add(setAlgoItem);

	}
	private void initActivitiesPanel(){

		activitiesPanel = new JPanel();
		basePanel.add(activitiesPanel);
		activitiesPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));



		pointBeginingButton = new JButton("Wskaż początek");
		activitiesPanel.add(pointBeginingButton);

		pointEndButton = new JButton("Wskaż koniec");
		activitiesPanel.add(pointEndButton);

		addIndirectPointButton = new JButton("Dodaj punkt pośredni");
		//activitiesPanel.add(addIndirectPointButton);

		findSolutionButton = new JButton("Znajdź rozwiązanie");
		activitiesPanel.add(findSolutionButton);



	}

	private void initBottomPanel(){
		bottomPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) bottomPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(bottomPanel, BorderLayout.SOUTH);

		xLabel = new JLabel("X:");
		bottomPanel.add(xLabel);

		xTextField = new JTextField("0");
		xTextField.setHorizontalAlignment(SwingConstants.CENTER);
		bottomPanel.add(xTextField);
		xTextField.setColumns(5);

		yLabel = new JLabel("Y:");
		bottomPanel.add(yLabel);

		yTextField = new JTextField("0");
		yTextField.setHorizontalAlignment(SwingConstants.CENTER);
		bottomPanel.add(yTextField);
		yTextField.setColumns(5);

	}
	public MazeStage getMazeStage(){

		return stage;
	}

	AlgoChooserDialog getAlgoDialog(){
		
		return algoDialog;
	}
	public void addOpenFileListener(ActionListener listener){

		openFileItem.addActionListener(listener);


	}

	public void addSaveCompressedListener(ActionListener listener){

		saveAsCompressedItem.addActionListener(listener);

	}

	public void addSaveSolutionListener(ActionListener listener){

		saveSolutionItem.addActionListener(listener);

	}
	public void addAlgoChooseListener(ActionListener listener){

		setAlgoItem.addActionListener(listener);

	}
}
