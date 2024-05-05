import javax.swing.*;
import java.awt.*;
public class MazePanel extends JPanel{
	private JPanel basePanel;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu solutionMenu;
	private JMenu algorithmMenu;
	private JMenuItem openPlainItem;
	private JMenuItem openCompressedItem;
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
	public MazePanel(){
		setLayout(new BorderLayout(0, 0));

		basePanel = new JPanel();
		add(basePanel, BorderLayout.NORTH);
		basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));

		menuBar = new JMenuBar();


		fileMenu = new JMenu("Plik");
		fileMenu.setHorizontalAlignment(SwingConstants.LEFT);
		menuBar.add(fileMenu);

		openPlainItem = new JMenuItem("Otwórz plik prosty");
		openPlainItem.setHorizontalAlignment(SwingConstants.LEFT);
		fileMenu.add(openPlainItem);

		openCompressedItem = new JMenuItem("Otwórz plik skompresowany");
		openCompressedItem.setHorizontalAlignment(SwingConstants.LEFT);
		fileMenu.add(openCompressedItem);

		saveAsCompressedItem = new JMenuItem("Zapisz jako plik skompresowany");
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

		activitiesPanel = new JPanel();
		basePanel.add(activitiesPanel);
		activitiesPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		findSolutionButton = new JButton("Znajdź rozwiązanie");
		activitiesPanel.add(findSolutionButton);

		pointBeginingButton = new JButton("Wskaż początek");
		activitiesPanel.add(pointBeginingButton);

		pointEndButton = new JButton("Wskaż koniec");
		activitiesPanel.add(pointEndButton);

		addIndirectPointButton = new JButton("Dodaj punkt pośredni");
		activitiesPanel.add(addIndirectPointButton);

		stage = new MazeStage();
		add(stage, BorderLayout.CENTER);



	}



}
