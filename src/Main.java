import java.io.FileNotFoundException;
import java.util.List;

import Exceptions.EmptyFileException;
import Exceptions.MalformedInputException;
import Exceptions.WrongFileFormatException;

/**
 * Diese Klasse enthält ist der Eintrittspunkt in das Programm
 * 
 * @author Joshua
 *
 */
public class Main {

	/**
	 * Mit diese Methode wird das Programm gestartet. Alle Dateipfade, die im
	 * Parameter args enthalten sind werden eingelesen und verarbeitet.
	 * 
	 * @param args
	 *            Dateipfade zu den einzulesenden Dateien
	 */
	public static void main(String[] args) {
		FileHandler fileHandler = new FileHandler();
		String pfad = "files/fehler/fehlerfall3.in";
		pfad = "files/fehler/fehlerfall4.in";
		try {
			List<String> worte = fileHandler.lese(pfad);
			for (String w: worte)
				System.out.print(w + " ");
		} catch (WrongFileFormatException | FileNotFoundException | EmptyFileException | MalformedInputException e) {
			System.out.println(e.getMessage());
		}
	}

}
