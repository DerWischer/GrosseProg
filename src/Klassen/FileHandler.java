package Klassen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Exceptions.EmptyFileException;
import Exceptions.MalformedInputException;
import Exceptions.WrongFileFormatException;

/**
 * Diese Klasse liest Eingabedateien ein und schreibt Ausgabedateien
 * 
 * @author Joshua
 *
 */
public class FileHandler {

	/**
	 * Kommentar der in der eingelesenen Eingabedatei steht
	 */
	private String kommentar;

	/**
	 * Ein mögliches Rätsel
	 */
	private Raetsel r1;

	/**
	 * Ein optimiertes Raetsel
	 */
	private Raetsel r2;

	/**
	 * Pfad zur eingelesenen Eingabedatei
	 */
	private String pfad;

	/**
	 * Wörter die aus der Eingabedatei gelesen wurden.
	 */
	private List<String> woerter;

	/**
	 * Liest eine Eingabedatei ein. Eingabedatein haben die Dateiendung "*.in"
	 * 
	 * @param pfad
	 *            Pfad zur Eingabedatei
	 * @return Wörter die aus der Datei gelesen wurden
	 * @throws WrongFileFormatException
	 *             Wird ausgelöst, wenn der Pfad nicht auf ".in" endet
	 * @throws FileNotFoundException
	 *             Wird ausgelöst, wenn keine Datei gefunden wird
	 * @throws EmptyFileException
	 *             Wird ausgelöst, wenn die Datei keinen Inhalt hat
	 * @throws MalformedInputException
	 *             Wird ausgelöst, wenn die Datei ungültigen Inhalt hat
	 */
	public List<String> lese(String pfad)
			throws WrongFileFormatException, FileNotFoundException, EmptyFileException, MalformedInputException {
		this.pfad = pfad;

		File eingabeDatei = new File(pfad);
		// prüfe ob die Datei existiert
		if (!eingabeDatei.exists()) {
			throw new FileNotFoundException("Die Datei wurde nicht gefunden");
		}

		// prüfe Dateiendung
		if (!pfad.endsWith(".in")) {
			throw new WrongFileFormatException();
		}

		// Lese die Datei
		List<String> woerter = lese(eingabeDatei);

		if (woerter.size() == 0) {
			throw new EmptyFileException("In der Datei sind keine Worte enthalten");
		}
		return woerter;
	}

	/**
	 * Hilfsmethode, zum einlesen von Dateien. Die Datei wird Zeilenweise
	 * gelesen und es wird geprüft, ob die Datei den Restriktionen entspricht.
	 * 
	 * @param eingabeDatei
	 *            Eingabedatei
	 * @return Liste der eingelesenen Worte
	 * @throws MalformedInputException
	 *             Wird ausgelöst, wenn die Datei ungültigen Inhalt hat
	 * @throws FileNotFoundException
	 *             Wird ausgelöst, wenn keine Datei gefunden wird
	 */
	private ArrayList<String> lese(File eingabeDatei) throws MalformedInputException, FileNotFoundException {
		woerter = new ArrayList<>();
		ArrayList<String> upperCaseWoerter = new ArrayList<>(); // enthält die
																// eingelesenen
																// Wörter in
																// Großbuchstaben
		Scanner sc = null;
		try {
			sc = new Scanner(eingabeDatei, "utf-8");
			int currentLine = 1; // Zeilenzähler
			kommentar = "";
			boolean kommentarValid = true;

			while (sc.hasNextLine()) {
				String line = sc.nextLine();

				if (line.startsWith(";")) { // Kommentarzeilen
					if (kommentarValid) {
						kommentar += line + "\n";
					} else {
						throw new MalformedInputException(
								"Kommentare düfen nur zu Beginn der Datei stehen: Aufgetreten in Zeile " + currentLine
										+ " -> " + line);
					}
				} else { // Wörter
					kommentarValid = false;

					String upperCaseLine = line.toUpperCase();
					for (int i = 0; i < upperCaseLine.length(); i++) {
						int val = (int) upperCaseLine.charAt(i);
						if (val < 65 || val > 90) { // Ascii Werte A - Z 
							throw new MalformedInputException(
									"Wörter düfen nur alhpabetische Zeichen enthalten: Aufgetreten in Zeile "
											+ currentLine + " -> " + line);
						}
					}

					if (woerter.contains(line)) {
						throw new MalformedInputException("Wörter dürfen nur einmal vorkommen. Aufgetreten in Zeile "
								+ currentLine + " -> " + line);
					}
					woerter.add(line);
					upperCaseWoerter.add(upperCaseLine);

				}
				currentLine++;
			}
			return upperCaseWoerter;
		} catch (FileNotFoundException e) {
			throw e;
		} finally {
			if (sc != null){
				sc.close();
			}
		}
	}

	/**
	 * Schreibt eine mögliche und eine optimierte Lösung in Ausgabedateien.
	 * Ausgabedatein haben die Dateiendung "*.out". Der Dateiname wird von der
	 * Eingabedatei übernommen, wobei bei der optimierten Ausgabedatei ein
	 * "_opt" an den Dateinamen angehangen wird.
	 * 
	 * @param r1
	 *            mögliches Rätsel
	 * @param r2
	 *            optimiertes Rätsel
	 */
	public void schreibe(Raetsel r1, Raetsel r2) {
		this.r1 = r1;
		this.r2 = r2;
		schreibeLoesung();
		schreibeLoesungOptimiert();
	}

	/**
	 * Hilfsmethode, die ein optimiertes Rätsel in einer Datei speicher. Der
	 * Dateipfad wird aus dem Dateipfad der Eingabedatei ermittelt.
	 */
	private void schreibeLoesung() {
		String outPfad = pfad.replace(".in", ".out");
		schreibeOutputFile(outPfad, r1);
	}

	/**
	 * Hilfsmethode, die ein optimiertes Rätsel in einer Datei speicher. Der
	 * Dateipfad wird aus dem Dateipfad der Eingabedatei ermittelt.
	 */
	private void schreibeLoesungOptimiert() {
		String outPfad = pfad.replace(".in", "_opt.out");
		schreibeOutputFile(outPfad, r2);
	}

	/**
	 * Hilfsmethode, die ein Rätsel in eine Datei schreibt. Das Rätsel wird dem
	 * Ausgabeformat entsprechend in die Datei geschrieben.
	 * 
	 * @param outPfad
	 *            Pfad der Datei, in die geschrieben wird
	 * @param r
	 *            Raetsel, dass in die Datei geschrieben wird
	 */
	private void schreibeOutputFile(String outPfad, Raetsel r) {
		ArrayList<String> lines = new ArrayList<>();
		if (kommentar.length() > 0) {
			lines.add(kommentar);
		}

		String line = "Eingelesene Wörter: ";
		for (int i = 0; i < woerter.size() - 1; i++) {
			line += woerter.get(i) + ", ";
		}
		line += woerter.get(woerter.size() - 1);
		lines.add(line);

		if (r != null) {

			lines.add(""); // leeres Raster schreiben
			for (String tmpLine : r.getRasterLeer()) {
				lines.add(tmpLine);
			}

			lines.add(""); // gefülltes Raster schreiben 			
			for (String tmpLine : r.getRasterZufall()) {
				lines.add(tmpLine);
			} 
			lines.add("");
			lines.add("Kompaktheitsmaß: " + r.getKompaktheitsmaß());
		} else {
			lines.add("");
			lines.add("Keine Lösung gefunden");
		}

		File ausgabeDatei = new File(outPfad);
		try {
			Files.write(ausgabeDatei.toPath(), lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("[ERR] '" + ausgabeDatei.getName() + "' : " + e.getClass().getSimpleName());
		}

	}

	/**
	 * Diese Methode wird verwendet, um Fehler in Ausgabedateien zu schreiben.
	 * In der Ausgabedatei steht der Kommentar der Eingabedatei und darunter die
	 * Fehlernachricht.
	 * 
	 * @param message
	 *            Fehlernachricht
	 */
	public void schreibeFehler(String message) {
		String outPfad1 = pfad.replace(".in", ".out");
		String outPfad2 = pfad.replace(".in", "_opt.out");

		ArrayList<String> lines = new ArrayList<>();
		if (kommentar.length() > 0) {
			lines.add(kommentar);
		}		
		lines.add(message);

		File ausgabeDatei1 = new File(outPfad1);		
		try {
			Files.write(ausgabeDatei1.toPath(), lines, StandardCharsets.UTF_8);			
		} catch (IOException e) {
			System.out.println("[ERR] '" + ausgabeDatei1.getName() + "' : " + e.getClass().getSimpleName());
		}
		
		File ausgabeDatei2 = new File(outPfad2);
		try {			
			Files.write(ausgabeDatei2.toPath(), lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("[ERR] '" + ausgabeDatei2.getName() + "' : " + e.getClass().getSimpleName());
		}
	}
}
