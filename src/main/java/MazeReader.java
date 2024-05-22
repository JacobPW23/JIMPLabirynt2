import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class MazeReader {

    ArrayList<String> mazeLines;

    public Maze readMaze(String path) {

        ArrayList<String> mazeLines = new ArrayList<String>();

        String data;
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {

                data = myReader.nextLine();

                mazeLines.add(data);
            }
            myReader.close();


        } catch (FileNotFoundException e) {
            System.out.println("Wystąpił błąd: brak pliku lub brak dostępu");
            return null;

        }
        if(!isFileValid(mazeLines)){
            System.out.println("Wystąpił błąd: plik jest niepoprawny");
            throw new IllegalArgumentException("Invalid file");
        }


        return new Maze(mazeLines);
    }

    private boolean isFileValid(ArrayList<String> mazeLines) {
        if (mazeLines.isEmpty() || mazeLines.get(0).isEmpty())
            return false;
        int lineLength = mazeLines.get(0).length();
        for (int j = 0; j < mazeLines.size(); j++) {
            String line = mazeLines.get(j);
            if(line.length()!=lineLength)
                return false;
            for (int i = 0; i < line.length(); i++) {
                char testedElem = line.charAt(i);
                if (testedElem != ' ' && testedElem != 'X' && testedElem != 'K' && testedElem != 'P')
                    return false;
            }
        }
        return true;
    }
}
