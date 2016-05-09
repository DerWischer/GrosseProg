import java.util.List;

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

	public FileHandler() {
		// TODO
	}

	/**
	 * Liest eine Eingabedatei ein. Eingabedatein haben die Dateiendung "*.in"
	 * 
	 * @param pfad
	 *            Pfad zur Eingabedatei
	 * @return Wörter die aus der Datei gelesen wurden
	 */
	public List<String> lese(String pfad) {
		this.pfad = pfad;
		return null;
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
		// TODO
	}

	/**
	 * Hilfsmethode, die eine mögliche Lösung schreibt
	 */
	private void schreibeLoesung() {
		// TODO
	}

	/**
	 * Hilfsmethode, die eine optimierte Lösung schreibt
	 */
	private void schreibeLoesungOptimiert() {
		// TODO
	}

}
