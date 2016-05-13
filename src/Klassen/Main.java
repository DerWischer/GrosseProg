package Klassen;
import java.io.FileNotFoundException;
import java.util.List;


import Exceptions.EmptyFileException;
import Exceptions.MalformedInputException;
import Exceptions.WrongFileFormatException;

/**
 * Diese Klasse ist der Eintrittspunkt in das Programm
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
		Verarbeiter v = new Verarbeiter();
				
		if (args.length == 0){
			System.out.println("[ERR] Es wurden keine Dateipfade angegeben");
		}
		for (String datei : args) {
			try {
				List<String> woerter = fileHandler.lese(datei);

				List<Wort> lsg = v.loese(woerter);
				List<Wort> lsgOpt = v.loeseOptimal(woerter);


				Raetsel r = null; // bleibt null, wenn keine Loesung gefunden
									// wurde

				if (lsg != null) {
					r = new Raetsel(lsg);
				}
				Raetsel rOpt = null;
				if (lsgOpt != null) {

					rOpt = new Raetsel(lsgOpt);
				}

				fileHandler.schreibe(r, rOpt);
				
				System.out.println(" [OK] '" + datei);
			} catch (WrongFileFormatException | EmptyFileException | MalformedInputException e) {
				fileHandler.schreibeFehler(e.getMessage());
				System.out.println("[ERR] '" + datei + "' : " + e.getClass().getSimpleName());
			} catch (FileNotFoundException e) {
				System.out.println("[ERR] '" + datei + "' : " + e.getClass().getSimpleName());
			}
		}
	}

}
