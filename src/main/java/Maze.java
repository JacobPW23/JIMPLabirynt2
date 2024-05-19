import java.util.ArrayList;
import java.awt.*;

public class Maze {

    public ArrayList<MazeField> fields;
    private int columnNumber;
    private int rowNumber;
    private float xBeginning;
    private float yBeginning;
    private Graphics2D g2D;
    public ArrayList<String> lines;

    public Maze(ArrayList<String> lines) {

        this.lines = lines;
        rowNumber = lines.size();
        columnNumber = lines.get(0).length();
        if (lines.isEmpty())
            return;

        fields = new ArrayList<MazeField>();

        for (int j = 0; j < lines.size(); j++) {
            String line = lines.get(j);
            for (int i = 0; i < line.length(); i++)
                fields.add(new MazeField(line.charAt(i), i, j));


        }
        xBeginning = 0;
        yBeginning = 0;

    }

    public int getColumnsNumber() {
        return this.columnNumber;
    }

    public int getRowsNumber() {

        return this.rowNumber;
    }

    public void setBegining(float xBegining, float yBegining) {
        this.xBeginning = xBegining;
        this.yBeginning = yBegining;

        float x = xBegining;
        float y = yBegining;
        for (int i = 0; i < fields.size(); i++) {

        /*if(x%nOfcolumns==0){
            x=xBegining;
            y+=MazeField.wallSize;
        }
        */
            fields.get(i).setBeginning(x, y);
            //x+=MazeField.wallSize;

        }


    }

    public void draw(Graphics2D g) {
        for (MazeField field : fields) {
            field.draw(g);
        }
    }

    public MazeField getFieldAt(int x, int y) {

        int column = (int)((x - xBeginning) / MazeField.wallSize);
        int row = (int)((y - yBeginning) / MazeField.wallSize);
        if (column < 0 || column >= columnNumber || row < 0 || row >= rowNumber) {
            return null;
        }
        return fields.get(row * columnNumber + column);

    }

    public ArrayList<MazeField> getFields() {
        return fields;
    }

}