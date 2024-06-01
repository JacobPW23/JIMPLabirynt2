import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.spi.BreakIteratorProvider;

class InterfaceController implements ErrorHandler, CLIListener {
    private MainView view;
    private MazeReader reader;
    private MazeSolver solver;
    private CLIManager commandManager;

    class MazeMotionAdapter extends MouseAdapter {
        private MainView view;

        public MazeMotionAdapter(MainView view) {
            this.view = view;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            view.setCurrentCoordinates(e.getX(), e.getY());
            updatePointer(e.getX(), e.getY());
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            int offsetX = view.getMazeStage().getDrawingXBeginning();
            int offsetY = view.getMazeStage().getDrawingYBeginning();

            int column = (x - offsetX) / MazeField.wallSize;
            int row = (y - offsetY) / MazeField.wallSize;

            Maze maze = view.getMazeStage().getMaze();

            if (column >= 0 && column < maze.getColumnsNumber() && row >= 0 && row < maze.getRowsNumber()) {
                if (view.getMazeStage().getPointingMode() == MazePanel.START_POINTING_MODE) {
                    maze.setStartPoint(column, row);
                } else if (view.getMazeStage().getPointingMode() == MazePanel.END_POINTING_MODE) {
                    maze.setEndPoint(column, row);
                }
                view.updateMaze(maze);
                view.unlockPointButtons();
            } else {
                System.out.println("Clicked outside maze boundaries");
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            view.setNoCoordinates();
            clearPointer();
        }

        public void updatePointer(int x, int y) {
            if (view.getMazeStage().getPointingMode() != MazePanel.NO_POINTING_MODE) {
                view.getMazeStage().highlightAt(x, y);
            }
        }

        public void clearPointer() {
            view.getMazeStage().clearHighlighted();
        }
    }

    class MazeKeyAdapter extends KeyAdapter {
        private MainView view;

        public MazeKeyAdapter(MainView view) {
            this.view = view;
        }

        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == 27) {
                view.getMazeStage().setPointingMode(MazePanel.NO_POINTING_MODE);
                view.getMazeStage().clearHighlighted();
                view.unlockPointButtons();
            }
        }
    }

    public InterfaceController(MainView view) {
        this.view = view;

        reader = new MazeReader();
        reader.addErrorListener(this);
        view.addOpenFileListener(e -> openFile());
        view.addStartPointListener(e -> setStartPoint());
        view.addEndPointListener(e -> setEndPoint());
        view.addFindSolutionListener(e -> findSolution());
        view.addAlgoChangeListener(e -> updateAlgoDescription());
        view.addOnMazeMouseMovedListener(new MazeMotionAdapter(view));
        view.addOnMazeMouseExitedListener(new MazeMotionAdapter(view));
        view.addOnMazeMouseClickedListener(new MazeMotionAdapter(view));
        view.addPointingModeEscapeListener(new MazeKeyAdapter(view));
        commandManager = new CLIManager();

        try {
            commandManager.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        commandManager.start();
        commandManager.addListener(this);
    }

    private void openFile() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setFileFilter(new FileNameExtensionFilter("Text and Binary Files", "bin", "txt"));
        fileDialog.setCurrentDirectory(new File("src/main/resources"));
        int val = fileDialog.showOpenDialog(this.view);
        if (val == JFileChooser.APPROVE_OPTION) {
            loadMaze(fileDialog.getSelectedFile().getAbsolutePath());
        }
    }

    private void setStartPoint() {
        view.getMazeStage().clearPath();
        view.getMazeStage().setPointingMode(MazePanel.START_POINTING_MODE);
        view.lockPointButtons();
        view.requestFocus();
    }

    private void setEndPoint() {
        view.getMazeStage().clearPath();
        view.getMazeStage().setPointingMode(MazePanel.END_POINTING_MODE);
        view.lockPointButtons();
        view.requestFocus();
    }

    private void findSolution() {
        if (view.getMazeStage().getMaze() != null) {
            MazeGraph graph = view.getMazeStage().getMaze().getGraph();

            for (Node node : graph.getNodes()) {
                node.setVisited(false);
            }

            solver = new MazeSolver(graph);

            if (solver.solve()) {
                view.getMazeStage().getMaze().setSolutionPath(solver.getSolutionStack());
                view.updateMaze(view.getMazeStage().getMaze());
            } else {
                view.displayError(new Exception("No solution found"));
            }
        }
    }

    public void updateAlgoDescription() {
        view.setAlgoDescription();
    }

    @Override
    public void handleError(Exception ex) {
        view.displayError(ex);
    }

    @Override
    public void onCommandEntered(String path) {
        loadMaze(path);
        findSolution();
    }

    private void loadMaze(String path) {
        try {
            view.clearError();
            Maze maze = reader.readMaze(path);
            view.getMazeStage().setMaze(maze);
            maze.buildGraph();
            maze.defaultBounds(maze.getGraph());
            view.updateMaze(maze);
            view.getStageContainer().revalidate();
            view.getStageContainer().repaint();
        } catch (Exception ex) {
            view.displayError(new Exception("Failed to load file"));
        }
    }
}

