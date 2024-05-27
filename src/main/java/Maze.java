import java.util.ArrayList;
import java.awt.*;

public class Maze {

    public ArrayList<MazeField> fields;
    public ArrayList<ErrorHandler> errorListeners= new ArrayList<ErrorHandler>();
    private int entryX;
    private int entryY;
    private int exitX;
    private int exitY;
    private int columnNumber;
    private int rowNumber;
    private Graphics2D g2D;
    public ArrayList<String> lines;

    public Maze(ArrayList<String> lines) {
        if (lines.isEmpty())
            return;
        this.lines = lines;
        rowNumber = lines.size();
        columnNumber = lines.get(0).length();

        fields = new ArrayList<MazeField>();
        for (int j = 0; j < lines.size(); j++) {
            String line = lines.get(j);
            for (int i = 0; i < line.length(); i++)
                fields.add(new MazeField(line.charAt(i), i, j));
        }
        setHoles();
    }

    public void draw(Graphics2D g2D) {
        for (MazeField field : fields) {
            field.draw(g2D);
        }
    }

    public int getColumnsNumber() {
        return this.columnNumber;
    }

    public Point getEntry(){

        return new Point(entryX,entryY);
    }
    public Point getExit(){
        return new Point(exitX,exitY);
    }

    public int getRowsNumber() {
        return this.rowNumber;
    }

    public MazeField getFieldAt(int column, int row) {

        if (column < 0 || column >= columnNumber || row < 0 || row >= rowNumber)
            return null;
        return fields.get(row * columnNumber + column);
    }

    public ArrayList<MazeField> getFields() {
        return fields;
    }

    public void addErrorListener(ErrorHandler handler){
        errorListeners.add(handler);
    }

    private void notifyListeners(Exception ex){ 

        for(ErrorHandler l: errorListeners)
            l.handleError(ex);
    }

    private void setHoles(){
        String line=null;
        for(int j=0;j<lines.size();j++){
			 line=lines.get(j);
             for(int i=0;i<line.length();i++)
				if(line.charAt(i)=='P'){
                    entryY=j;
                    entryX=i;
                }
                    
            
				else if(line.charAt(i)=='K'){
                    exitY=j;
                    exitX=i;
                }
        }

    }
    
}