import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.ItemListener;

public class MainView extends JFrame {

    public final static String SHORTEST_PATH = "Algorytm: Najkrótsza ścieżka";
    public final static String ANY_PATH = "Algorytm: Dowolna ścieżka";
    private JPanel topPanel;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu solutionMenu;
    private JMenuItem openFileItem;
    private JMenuItem saveAsCompressedItem;
    private JMenuItem saveSolutionItem;
    private JComboBox setAlgorithm;

    private JPanel centerPanel;
    private MazePanel stage;
    private JPanel basePanel;
    private JPanel bottomPanel;
    private JButton findSolutionButton;
    private JButton startPointButton;
    private JButton endPointButton;
    private JButton addIndirectPointButton;
    private JLabel xCoordinateLabel;
    private JLabel yCoordinateLabel;
    private JLabel pathModeLabel;

    private JScrollPane stageContainer;


    public MainView() {

        super("Maze Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());


        add(initTopPanel(), BorderLayout.NORTH);
        add(initCenterPanel(), BorderLayout.CENTER);

        add(initBottomPanel(), BorderLayout.SOUTH);
        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);

    }

    private JPanel initTopPanel() {
        basePanel = new JPanel();
        basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));
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

        basePanel.add(topPanel);
        JPanel activitiesPanel = new JPanel();
        activitiesPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        startPointButton = new JButton("Wskaż początek");
        endPointButton = new JButton("Wskaż koniec");
        addIndirectPointButton = new JButton("Dodaj punkt pośredni");
        findSolutionButton = new JButton("Znajdź rozwiązanie");

        activitiesPanel.add(startPointButton);
        activitiesPanel.add(endPointButton);
        //activitiesPanel.add(addIndirectPointButton);
        activitiesPanel.add(findSolutionButton);
        activitiesPanel.setBackground(Color.LIGHT_GRAY);
        basePanel.add(activitiesPanel);
        return basePanel;

    }

    private JScrollPane initCenterPanel() {

        stageContainer = new JScrollPane();
        //stageContainer.setBorder(null);
        stageContainer.getVerticalScrollBar().setUnitIncrement(16);

        stageContainer.setAlignmentX(SwingConstants.CENTER);
        stage = new MazePanel();
        stageContainer.setViewportView(stage);
        stageContainer.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        add(stageContainer, BorderLayout.CENTER);


        return stageContainer;
    }

    private JPanel initBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pathModeLabel = new JLabel("Algorytm: Dowolna ścieżka");
        pathModeLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
        bottomPanel.add(pathModeLabel);
        xCoordinateLabel = new JLabel("X: -");
        yCoordinateLabel = new JLabel("Y: -");
        xCoordinateLabel.setPreferredSize(new Dimension(60, 14));
        yCoordinateLabel.setPreferredSize(new Dimension(60, 14));
        bottomPanel.add(xCoordinateLabel);
        bottomPanel.add(yCoordinateLabel);

        return bottomPanel;
    }

    public MazePanel getMazeStage() {
        return stage;
    }

    public JScrollPane getStageContainer() {
        return stageContainer;

    }

    public JLabel getXLabel() {
        return xCoordinateLabel;
    }

    public JLabel getYLabel() {
        return yCoordinateLabel;
    }

    public void setCurrentCoordinates(float y, float x) {
        int row = (int) (y - stage.getDrawingYBeginning()) / 10;
        int column = (int) (x - stage.getDrawingXBeginning()) / 10;

        Dimension size = stage.getMazeSize();
        if (row >= 0 && row < size.getHeight() && column >= 0 && column < size.getWidth()) {
            xCoordinateLabel.setText("X: " + column);
            yCoordinateLabel.setText("Y: " + row);

        } else {
            setNoCoordinates();
        }

    }

    public void setNoCoordinates() {
        xCoordinateLabel.setText("X: -");
        yCoordinateLabel.setText("Y: -");
    }

    public void lockPointButtons() {
        startPointButton.setEnabled(false);
        endPointButton.setEnabled(false);
        findSolutionButton.setEnabled(false);
    }


    public void unlockPointButtons() {
        startPointButton.setEnabled(true);
        endPointButton.setEnabled(true);
        findSolutionButton.setEnabled(true);

    }

    public void setAlgoDescription() {
        String description = "";
        switch (setAlgorithm.getSelectedIndex()) {
            case 0:
                description = ANY_PATH;
                break;
            case 1:
                description = ANY_PATH;
                break;
            case 2:
                description = SHORTEST_PATH;
                break;
        }
        pathModeLabel.setText(description);
    }

    public void addOpenFileListener(ActionListener listener) {
        openFileItem.addActionListener(listener);
    }

    public void addStartPointListener(ActionListener listener) {
        startPointButton.addActionListener(listener);
    }

    public void addOnMazeMouseMovedListener(MouseAdapter listener) {

        stage.addMouseMotionListener(listener);
    }

    public void addOnMazeMouseExitedListener(MouseListener listener) {

        stage.addMouseListener(listener);
    }

    public void addPointingModeEscapeListener(KeyListener listener) {

        addKeyListener(listener);
    }

    public void addEndPointListener(ActionListener listener) {

        endPointButton.addActionListener(listener);
    }

    public void addAlgoChangeListener(ItemListener listener) {
        setAlgorithm.addItemListener(listener);
    }
}

