import java.util.ArrayList;
import java.awt.*;

public class Maze {

    public ArrayList<MazeField> fields;
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
    }

    public void draw(Graphics2D g2D) {
        for (MazeField field : fields) {
            field.draw(g2D);
        }
    }

    public int getColumnsNumber() {
        return this.columnNumber;
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
}