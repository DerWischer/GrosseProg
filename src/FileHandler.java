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
	 * Kommentar, der in Eingabedateien steht
	 */
	private String kommentar;

	/**
	 * Ein m�gliches R�tsel
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
	 * W�rter die aus der Eingabedatei gelesen wurden.
	 */
	private List<String> woerter;

	/**
	 * Liest eine Eingabedatei ein. Eingabedatein haben die Dateiendung "*.in"
	 * 
	 * @param pfad
	 *            Pfad zur Eingabedatei
	 * @return W�rter die aus der Datei gelesen wurden
	 * @throws WrongFileFormatException
	 *             Wird ausgel�st, wenn der Pfad nicht auf ".in" endet
	 * @throws FileNotFoundException
	 *             Wird ausgel�st, wenn keine Datei gefunden wird
	 * @throws EmptyFileException
	 *             Wird ausgel�st, wenn die Datei keinen Inhalt hat
	 * @throws MalformedInputException
	 *             Wird ausgel�st, wenn die Datei ung�ltigen Inhalt hat
	 */
	public List<String> lese(String pfad)
			throws WrongFileFormatException, FileNotFoundException, EmptyFileException, MalformedInputException {
		this.pfad = pfad;

		File eingabeDatei = new File(pfad);
		// pr�fe ob die Datei existiert
		if (!eingabeDatei.exists()) {
			throw new FileNotFoundException("Die Datei wurde nicht gefunden");
		}

		// pr�fe Dateiendung
		if (!pfad.endsWith(".in")) {
			throw new WrongFileFormatException();
		}

		List<String> woerter = lese(eingabeDatei);

		if (woerter.size() == 0) {
			throw new EmptyFileException("In der Datei sind keine Worte enthalten");
		}

		this.woerter = woerter;
		return woerter;
	}

	/**
	 * Hilfsmethode, zum einlesen von Dateien. Die Datei wird Zeilenweise
	 * gelesen und es wird gepr�ft, ob die Datei den Restriktionen entspricht.
	 * 
	 * @param eingabeDatei
	 * @return
	 * @throws MalformedInputException
	 *             Wird ausgel�st, wenn die Datei ung�ltigen Inhalt hat
	 * @throws FileNotFoundException
	 *             Wird ausgel�st, wenn keine Datei gefunden wird
	 */
	private ArrayList<String> lese(File eingabeDatei) throws MalformedInputException, FileNotFoundException {
		ArrayList<String> woerter = new ArrayList<>();
		Scanner sc = null;
		try {
			sc = new Scanner(eingabeDatei, "utf-8");
			int currentLine = 1;
			kommentar = "";
			boolean kommentarValid = true;
			while (sc.hasNextLine()) {
				String line = sc.nextLine().toUpperCase();
				if (line.startsWith(";")) {
					if (kommentarValid) {
						kommentar += line + "\n";
					} else {
						throw new MalformedInputException(
								"Kommentare d�fen nur zu Beginn der Datei stehen: Aufgetreten in Zeile " + currentLine
										+ " -> " + line);
					}
				} else {
					kommentarValid = false;
					for (int i = 0; i < line.length(); i++) {
						int val = (int) line.charAt(i);
						if (val < 65 || val > 90) {

							throw new MalformedInputException(
									"W�rter d�fen nur alhpabetische Zeichen enthalten: Aufgetreten in Zeile "
											+ currentLine + " -> " + line);
						}
					}

					if (woerter.contains(line.toUpperCase())) {
						throw new MalformedInputException("W�rter d�rfen nur einmal vorkommen. Aufgetreten in Zeile "
								+ currentLine + " -> " + line);
					}
					woerter.add(line.toUpperCase());
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
	 * Schreibt eine m�gliche und eine optimierte L�sung in Ausgabedateien.
	 * Ausgabedatein haben die Dateiendung "*.out". Der Dateiname wird von der
	 * Eingabedatei �bernommen, wobei bei der optimierten Ausgabedatei ein
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
	 * Hilfsmethode, die ein optimiertes R�tsel in einer Datei speicher. Der
	 * Dateipfad wird aus dem Dateipfad der Eingabedatei ermittelt.
	 */
	private void schreibeLoesung() {
		String outPfad = pfad.replace(".in", ".out");
		schreibeOutputFile(outPfad, r1);
	}

	/**
	 * Hilfsmethode, die ein optimiertes R�tsel in einer Datei speicher. Der
	 * Dateipfad wird aus dem Dateipfad der Eingabedatei ermittelt.
	 */
	private void schreibeLoesungOptimiert() {
		String outPfad = pfad.replace(".in", "_opt.out");
		schreibeOutputFile(outPfad, r2);
	}

	/**
	 * Hilfsmethode, die ein R�tsel in eine Datei schreibt. Das R�tsel wird dem
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

		lines.add("Eingelesene W�rter: ");
		String line = "";
		for (int i = 0; i < woerter.size() - 1; i++) {
			line += woerter.get(i) + ", ";
		}
		lines.add(line);

		lines.add(woerter.get(woerter.size() - 1));

		if (r != null) {

			lines.add("");
			for (String tmpLine : r.getRasterLeer()) {
				lines.add(tmpLine);
			}

			lines.add("");
			// lines.add("R�tsel versteckt");
			for (String tmpLine : r.getRasterZufall()) {
				lines.add(tmpLine);
			}

			// TODO Kompaktheitsma� angeben!!!
			lines.add("");
			lines.add("Kompaktheitsma�: " + r.getKompaktheitsma�());
		} else {
			lines.add("");
			lines.add("Keine L�sung gefunden");
		}

		File ausgabeDatei = new File(outPfad);
		try {
			Files.write(ausgabeDatei.toPath(), lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		lines.add("");
		lines.add(message);

		File ausgabeDatei1 = new File(outPfad1);
		File ausgabeDatei2 = new File(outPfad2);
		try {
			Files.write(ausgabeDatei1.toPath(), lines, StandardCharsets.UTF_8);
			Files.write(ausgabeDatei2.toPath(), lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
