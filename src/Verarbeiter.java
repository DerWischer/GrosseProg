import java.util.List;

/**
 * In dieser Klasse werden die Daten verarbeitet. Sie beinhaltet die Algorithmen
 * zur berechnung von Kreuzworträtseln.
 * 
 * @author Joshua
 *
 */
public class Verarbeiter {

	/**
	 * Ermittelt eine mögliche Lösung für ein Rätsel.
	 * 
	 * @param woerter
	 *            Alle Wörter die im Kreuzworträtsel enthalten sein sollen
	 * @return Alle Wörter mit entsprechenden Positionen (X,Y) und
	 *         Schreibrichtung. Es sollte geprüft werden, ob die Anzahl der
	 *         Wörter mit der erwartetet übereinstimmt.
	 */
	public List<Wort> loese(List<String> woerter) {
		// TODO
		return null;
	}

	/**
	 * Ermittelt eine optimierte Lösung für ein Rätsel.
	 * 
	 * @param woerter
	 *            Alle Wörter die im Kreuzworträtsel enthalten sein sollen
	 * @return Alle Wörter mit entsprechenden Positionen (X,Y) und
	 *         Schreibrichtung. Es sollte geprüft werden, ob die Anzahl der
	 *         Wörter mit der erwartetet übereinstimmt.
	 */
	public List<Wort> loeseOptimal(List<String> woerter) {
		// TODO
		return null;
	}

	/**
	 * Algorithmus zur Berechnung einer Lösung. Diese Methode wird rekursiv
	 * ausgeführt.
	 * 
	 * @param eingetragen
	 *            Wörter, die bereits im Kreuzworträtsel eingetragen sind
	 * @param uebrig
	 *            Wörter, die noch nicht im Kreuzworträtsel eingetragen sind
	 * @return Gibt den zuerst gefunden Weg zurück. Wenn die Anzahl der
	 *         zurückgegebenen Worte nicht der erwarteten Anzahl entspricht.
	 */
	private List<Wort> loese(List<Wort> eingetragen, List<String> uebrig) {
		// TODO
		return null;
	}

	/**
	 * Algorithmus zur Berechnung einer optimierten Lösung. Diese Methode wird
	 * rekursiv ausgeführt.
	 * 
	 * @param eingetragen
	 *            Wörter, die bereits im Kreuzworträtsel eingetragen sind
	 * @param uebrig
	 *            Wörter, die noch nicht im Kreuzworträtsel eingetragen sind
	 * @return Gibt den besten gefunden Weg zurück. Wenn die Anzahl der
	 *         zurückgegebenen Worte nicht der erwarteten Anzahl entspricht.
	 */
	private List<Wort> loeseOptimal(List<Wort> eingetragen, List<String> uebrig) {
		// TODO
		return null;
	}

}
