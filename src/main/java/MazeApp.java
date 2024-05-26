public class MazeApp {
    public static void main(String args[]) {
        MainView view = new MainView();
        MazeReader reader = new MazeReader();
        InterfaceController interfaceController = new InterfaceController(view, reader);
    }
}
