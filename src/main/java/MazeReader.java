import java.util.ArrayList;
import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner;
import java.util.Iterator;
public class MazeReader{



	public static ArrayList<String> readMaze(String path){ 

		ArrayList<String> mazeLines= new ArrayList<String>();

		String data;
		try {
			File myObj = new File(path);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				data = myReader.nextLine();

				mazeLines.add(data);
			}
			myReader.close();


		} 

		catch (FileNotFoundException e) {
			System.out.println("Wystąpił błąd");
			e.printStackTrace();
		}

		return mazeLines;
	}



}
