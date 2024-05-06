import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View {
    private JFrame frame;

    private JPanel topPanel;
    private JCheckBox shortestPath;
    private JComboBox algorithms;

    private JPanel mazePanel;

    private JPanel bottomPanel;
    private JComboBox fileFormat;
    private JButton loadMaze;
    private JButton saveMaze;
    private JButton solveMaze;
    private JButton startPoint;
    private JButton endPoint;


    public View()
    {
        frame = new JFrame("Maze Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        frame.add(createTopPanel(), BorderLayout.NORTH);
        frame.add(createMazePanel(), BorderLayout.CENTER);
        frame.add(createBottomPanel(), BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel createTopPanel()
    {
        topPanel = new JPanel();
        topPanel.setBackground(Color.LIGHT_GRAY);
        shortestPath = new JCheckBox("Najkrótsza ścieżka");
        algorithms = new JComboBox<>(new String[]{"DFS", "BFS", "A*"});
        algorithms.setSelectedIndex(0);


        topPanel.setLayout(new FlowLayout());
        topPanel.add(new JLabel("Algorytm:"));
        topPanel.add(algorithms);
        topPanel.add(shortestPath);
        return topPanel;
    }

    private JPanel createBottomPanel()
    {
        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        fileFormat = new JComboBox<>(new String[]{"Tekstowy", "Binarny"});
        fileFormat.setSelectedIndex(0);

        loadMaze = new JButton("Wczytaj");
        saveMaze = new JButton("Zapisz");

        startPoint = new JButton("Wybierz początek");
        endPoint = new JButton("Wybierz koniec");
        solveMaze = new JButton("Rozwiąż");

        bottomPanel.add(new JLabel("Format pliku:"));
        bottomPanel.add(fileFormat);
        bottomPanel.add(loadMaze);
        bottomPanel.add(saveMaze);
        bottomPanel.add(startPoint);
        bottomPanel.add(endPoint);
        bottomPanel.add(solveMaze);


        return bottomPanel;
    }

    private JPanel createMazePanel()
    {
        mazePanel = new JPanel();
        mazePanel.add(new JLabel("Maze"));
        return mazePanel;
    }

    public void addButtonListener(ActionListener listener)
    {
        loadMaze.addActionListener(listener);
    }

}
