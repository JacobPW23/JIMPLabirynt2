import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class InterfaceController implements ErrorHandler,CLIListener{
    private MainView view;
    private MazeReader reader;
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
        view.addAlgoChangeListener(e -> updateAlgoDescription());
        view.addOnMazeMouseMovedListener(new MazeMotionAdapter(view));
        view.addOnMazeMouseExitedListener(new MazeMotionAdapter(view));
        view.addPointingModeEscapeListener(new MazeKeyAdapter(view));
        commandManager= new CLIManager();
        
        try{
            commandManager.join();
        }
        catch (Exception ex){
            
        }
        commandManager.start();
        commandManager.addListener(this);
    }

    private void openFile() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setFileFilter(new FileNameExtensionFilter("Pliki tekstowe, binarne", "bin", "txt"));
        fileDialog.setCurrentDirectory(new File("src/main/resources"));
        int val = fileDialog.showOpenDialog(this.view);

       
            if (val == JFileChooser.APPROVE_OPTION) {
                loadMaze(fileDialog.getSelectedFile().getAbsolutePath());
            }
         
    }

    private void setStartPoint() {
        view.getMazeStage().setPointingMode(MazePanel.START_POINTING_MODE);
        view.lockPointButtons();
        view.requestFocus();
    }

    private void setEndPoint() {
        view.getMazeStage().setPointingMode(MazePanel.END_POINTING_MODE);
        view.lockPointButtons();
        view.requestFocus();
    }

    public void updateAlgoDescription() {
        view.setAlgoDescription();
    }

    @Override
    public void handleError(Exception ex){
        view.displayError(ex);
    }

    @Override
    public void onCommandEntered(String path){
        loadMaze(path);
    }

    private void loadMaze(String path){
        try{

        
                view.getMazeStage().setMaze(reader.readMaze(path));
                view.getStageContainer().revalidate();
                view.getStageContainer().repaint();
                view.clearError();
        }
        catch (Exception ex) {
            
            view.displayError(new Exception("Nie wczytano pliku"));
        }
    }
}
