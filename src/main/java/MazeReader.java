import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;


public class MazeReader {

	private ArrayList<String> mazeLines;
	private ArrayList<ErrorHandler> errorListeners= new ArrayList<ErrorHandler>();
	private int fileId;
	private int solutionId;

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
			//System.out.println("Wystąpił błąd: brak pliku lub brak dostępu");
			notifyListeners(new Exception("Brak pliku lub brak dostępu"));
			return null;

		}
		if(!isFileValid(mazeLines)){
			//System.out.println("Wystąpił błąd: plik jest niepoprawny");
			notifyListeners(new Exception("Plik jest niepoprawny"));
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

	public void addErrorListener(ErrorHandler handler){

		errorListeners.add(handler);
	}

	public void notifyListeners(Exception ex){

		for(ErrorHandler l: errorListeners)
			l.handleError(ex);

	}
	private  void reverseArray(byte [] array){
		int l=array.length;
		for(int i=0;i<l/2;i++){
			byte tmp=array[i];
			array[i]=array[l-i-1];
			array[l-i-1]=tmp;
		}
	}
	public  int byteArrayToInt(byte[] bytes,int n){
		int value = 0;

		reverseArray(bytes);
		//int i=n<bytes.length? bytes.length-n-1:0;
		for (int i=bytes.length-n;i<bytes.length;i++)
			value = (value << 8) + (bytes[i] & 0xFF);

		return value;
	}
	public String replaceAt(String s, int i,char newValue){
		char charTable []= s.toCharArray();
		charTable[i]=newValue;
		s=String.valueOf(charTable);
		return s;

	}
	public Maze readCompressedMaze(String Path){
		byte [] buffer= new byte [4];
		int rows=0;
		int columns=0; 
		int entryX=0;
		int entryY=0;
		int exitX=0,exitY=0;
		int counter=0,solutionOffset=0;
		int tmp=0;
		int separator=0,wall=0,path=0;
		int currentColumn=0;
		int currentRow=0;
		mazeLines= new ArrayList<String>();
		try{
			DataInputStream reader = new DataInputStream(new FileInputStream(new File("maze.bin")));

			//header
			if(reader.read(buffer,0,4)!=4){

				notifyListeners(new Exception("Uszkodzony nagłówek"));
				return null;
			}
			else fileId=byteArrayToInt(buffer,4);

			if(reader.read(buffer,0,1)!=1){

				notifyListeners(new Exception("Uszkodzony nagłówek"));
				return null;
			}

			if(reader.read(buffer,0,2)!=2) {

				notifyListeners(new Exception("Uszkodzony nagłówek"));
				return null;
			}
			else columns=byteArrayToInt(buffer,2);


			if(reader.read(buffer,0,2)!=2){

				notifyListeners(new Exception("Uszkodzony nagłówek"));
				return null;
			}
			else rows=byteArrayToInt(buffer,2);

			if(reader.read(buffer,0,2)!=2){

				notifyListeners(new Exception("Uszkodzony nagłówek"));
				return null;
			}
			else entryX=byteArrayToInt(buffer,2)-1;

			if(reader.read(buffer,0,2)!=2){

				notifyListeners(new Exception("Uszkodzony nagłówek"));
				return null;
			}
			else entryY=byteArrayToInt(buffer,2)-1;

			if(reader.read(buffer,0,2)!=2){

				notifyListeners(new Exception("Uszkodzony nagłówek"));
				return null;
			}
			else exitX=byteArrayToInt(buffer,2)-1;

			if(reader.read(buffer,0,2)!=2){

				notifyListeners(new Exception("Uszkodzony nagłówek"));
				return null;
			}
			else exitY=byteArrayToInt(buffer,2)-1;

			if(reader.skipBytes(12)!=12)
				notifyListeners(new Exception("Uszkodzony nagłówek"));
			if(reader.read(buffer,0,4)!=4){

				notifyListeners(new Exception("Uszkodzony nagłówek"));
				return null;
			}
			else counter=byteArrayToInt(buffer,4);

			if(reader.read(buffer,0,4)!=4){

				notifyListeners(new Exception("Uszkodzony nagłówek"));
				return null;
			}
			else solutionOffset=byteArrayToInt(buffer,4);

			if(reader.read(buffer,0,1)!=1){

				notifyListeners(new Exception("Uszkodzony nagłówek"));
				return null;
			}
			else separator=byteArrayToInt(buffer,1);

			if(reader.read(buffer,0,1)!=1){

				notifyListeners(new Exception("Uszkodzony nagłówek"));
				return null;
			}
			else wall=byteArrayToInt(buffer,1);

			if(reader.read(buffer,0,1)!=1){

				notifyListeners(new Exception("Uszkodzony nagłówek"));
				return null;
			}
			else path=byteArrayToInt(buffer,1);

			// maze fields
			String currentLine="";
			int fieldCounter=0;
			int fieldQuantity=-1;
			char fieldChar=0;
			int lineCount=0;

			for(int i=0; i<counter;i++){

				if(reader.read(buffer,0,1)!=1){

					notifyListeners(new Exception("Uszkodzona sekcja słów"));
					return null;
				}


				if(reader.read(buffer,0,1)!=1){

					notifyListeners(new Exception("Uszkodzona sekcja słów"));
					return null;
				}
				fieldChar=(char)byteArrayToInt(buffer,1);
				if(reader.read(buffer,0,1)!=1){

					notifyListeners(new Exception("Uszkodzona sekcja słów"));
					return null;
				}



				fieldQuantity=byteArrayToInt(buffer,1);
				for(int q=0;q<=fieldQuantity;q++){
					currentLine+=fieldChar;
					fieldCounter++;
					currentColumn++;
				}



				if(fieldCounter%columns==0){

					mazeLines.add(currentLine);
					currentLine="";
					currentColumn=0;
					currentRow++;
				}



			}

			//adding start & end

			mazeLines.set(entryY,replaceAt(mazeLines.get(entryY),entryX,'P'));
			mazeLines.set(exitY,replaceAt(mazeLines.get(exitY),exitX,'K'));

			

			


			if(reader.read(buffer,0,4)!=4){
                notifyListeners(new Exception("Brak końca pliku"));
					return null;
            }
            if(buffer[0]=='C' && buffer[1]=='B' && buffer[2]=='R' && buffer[3]=='R'){
				//reader has reached EOF- it means no solution section
				if(!isFileValid(mazeLines)){

					notifyListeners(new Exception("Plik jest niepoprawny"));
					return null;

				}

				return new Maze(mazeLines);
			}
            

			solutionId=byteArrayToInt(buffer,4);





			int solutionStepsNumber;

			if(reader.read(buffer,0,1)!=1){

				notifyListeners(new Exception("Uszkodzona sekcja rozwiązania"));

			}
			solutionStepsNumber=byteArrayToInt(buffer,1);

			//solution steps
			char directionField;
			int step;
			for(int j=0;j<=solutionStepsNumber;j++){

                    if(reader.read(buffer,0,1)!=1){

				        notifyListeners(new Exception("Uszkodzona sekcja rozwiązania"));
                    }
                    directionField=(char)byteArrayToInt(buffer,1);

                    if(reader.read(buffer,0,1)!=1){

				        notifyListeners(new Exception("Uszkodzona sekcja rozwiązania"));
                    }
                    step=byteArrayToInt(buffer,1);
                    //Above instructions do not exclude maze loading, but only path becacuse of path's optional character

                    //here it should be put in some solution container

			}
                    
			

			if(!isFileValid(mazeLines)){

				notifyListeners(new Exception("Plik jest niepoprawny"));
				return null;

			}


		}
		catch(Exception ex){

		}
		return new Maze(mazeLines);
	}

}
