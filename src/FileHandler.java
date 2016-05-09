import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
	 * Kommentar, der in Eingabedateien steht
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
	 * Pfad zur Eingabedatei
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
		// prüfe Dateiendung
		if (!pfad.endsWith(".in")) {
			throw new WrongFileFormatException("Die Dateiendung entspricht nicht der einer Eingabedatei");
		}

		File eingabeDatei = new File(pfad);
		// prüfe ob die Datei existiert
		if (!eingabeDatei.exists()) {
			throw new FileNotFoundException("Die Datei wurde nicht gefunden");
		}

		List<String> woerter = lese(eingabeDatei);

		if (woerter.size() == 0) {
			throw new EmptyFileException("In der Datei sind keine Worte enthalten");
		}

		this.pfad = pfad;
		return woerter;
	}

	/**
	 * Hilfsmethode, zum einlesen von Dateien. Die Datei wird Zeilenweise
	 * gelesen und es wird geprüft, ob die Datei den Restriktionen entspricht.
	 * 
	 * @param eingabeDatei
	 * @return
	 * @throws MalformedInputException
	 *             Wird ausgelöst, wenn die Datei ungültigen Inhalt hat
	 * @throws FileNotFoundException
	 *             Wird ausgelöst, wenn keine Datei gefunden wird
	 */
	private ArrayList<String> lese(File eingabeDatei) throws MalformedInputException, FileNotFoundException {
		ArrayList<String> woerter = new ArrayList<>();
		Scanner sc = null;
		try {
			sc = new Scanner(eingabeDatei);

			int currentLine = 1;
			kommentar = "";
			boolean kommentarValid = true;
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if (line.startsWith(";")) {
					if (kommentarValid) {
						kommentar += "\n" + line;
					} else {
						throw new MalformedInputException(
								"Kommentare düfen nur zu Beginn der Datei stehen: Aufgetreten in Zeile " + currentLine);
					}
				} else {
					kommentarValid = false;
					for (int i = 0; i < line.length(); i++) {
						if (!Character.isAlphabetic(line.charAt(i))) {
							throw new MalformedInputException(
									"Wörter düfen nur alhpabetische Zeichen enthalten: Aufgetreten in Zeile "
											+ currentLine);
						}
					}

					if (woerter.contains(line)) {
						throw new MalformedInputException(
								"Wörter dürfen nur einmal vorkommen. Aufgetreten in Zeile " + currentLine);
					}
					woerter.add(line);
				}
				currentLine++;
			}
			return woerter;
		} catch (FileNotFoundException e) {
			throw e;
		} finally {
			if (sc != null)
				sc.close();
		}
	}

	/**
	 * Schreibt eine mögliche und eine optimierte Lösung in Ausgabedateien.
	 * Ausgabedatein haben die Dateiendung "*.out". Der Dateiname wird von der
	 * Eingabedatei übernommen, wobei bei der optimierten Ausgabedatei ein
	 * "_opt" an den Dateinamen angehangen wird.
	 * 
	 * @param r1
	 * @param r2
	 */
	public void schreibe(Raetsel r1, Raetsel r2) {
		this.r1 = r1;
		this.r2 = r2;
		schreibeLoesung();	
		schreibeLoesungOptimiert();
	}	

	/**
	 * Hilfsmethode, die ein optimiertes Rätsel in einer Datei speicher. Der Dateipfad wird aus dem Dateipfad der Eingabedatei ermittelt.
	 */
	private void schreibeLoesung() {
		String outPfad = pfad.replace(".in", ".out");
		schreibeOutputFile(outPfad, r1);
	}

	/**
	 * Hilfsmethode, die ein optimiertes Rätsel in einer Datei speicher. Der Dateipfad wird aus dem Dateipfad der Eingabedatei ermittelt.
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
		StringBuilder outText = new StringBuilder();
		outText.append(kommentar + "\n");

		outText.append("Eingelesene Wörter: ");
		for (int i = 0; i < woerter.size() - 1; i++) {
			outText.append(woerter.get(i) + ", ");
		}
		outText.append(woerter.get(woerter.size() - 1) + "\n");

		outText.append("\nRätsel nicht versteckt\n");
		for (String line : r.getRasterLeer()) {
			outText.append(line + "\n");
		}

		outText.append("\nRätsel versteckt\n");
		for (String line : r.getRasterZufall()) {
			outText.append(line + "\n");
		}

		// TODO Kompaktheitsmaß angeben!!!
		outText.append("\nKompaktheitsmaß: " + "???");

		File ausgabeDatei = new File(outPfad);
		FileWriter fw;
		try {
			fw = new FileWriter(ausgabeDatei);
			fw.write(outText.toString());
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
