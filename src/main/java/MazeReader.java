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
        //if(!validLines(mazeLines))
        //return null;


        return new Maze(mazeLines);
    }

    private boolean validLines(ArrayList<String> mazeLines) {
        if (mazeLines.size() == 0 || mazeLines.get(0).length() == 0)
            return false;
        for (int j = 0; j < mazeLines.size(); j++) {
            String line = mazeLines.get(j);
            for (int i = 0; i < line.length(); i++) {
                char testedElem = line.charAt(i);
                if (testedElem != ' ' || testedElem != 'X' || testedElem != 'K' || testedElem != 'P')
                    return false;
                else if (testedElem == ' ' && (j == 0 || j == mazeLines.size() - 1))
                    return false;
            }
            if (j > 0 && mazeLines.get(j).length() != mazeLines.get(j - 1).length())
                return false;
        }
        return true;
    }
}
