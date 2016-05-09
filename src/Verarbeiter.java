import java.util.List;

/**
 * In dieser Klasse werden die Daten verarbeitet. Sie beinhaltet die Algorithmen
 * zur berechnung von Kreuzwortr�tseln.
 * 
 * @author Joshua
 *
 */
public class Verarbeiter {

	/**
	 * Ermittelt eine m�gliche L�sung f�r ein R�tsel.
	 * 
	 * @param woerter
	 *            Alle W�rter die im Kreuzwortr�tsel enthalten sein sollen
	 * @return Alle W�rter mit entsprechenden Positionen (X,Y) und
	 *         Schreibrichtung. Es sollte gepr�ft werden, ob die Anzahl der
	 *         W�rter mit der erwartetet �bereinstimmt.
	 */
	public List<Wort> loese(List<String> woerter) {
		// TODO
		return null;
	}

	/**
	 * Ermittelt eine optimierte L�sung f�r ein R�tsel.
	 * 
	 * @param woerter
	 *            Alle W�rter die im Kreuzwortr�tsel enthalten sein sollen
	 * @return Alle W�rter mit entsprechenden Positionen (X,Y) und
	 *         Schreibrichtung. Es sollte gepr�ft werden, ob die Anzahl der
	 *         W�rter mit der erwartetet �bereinstimmt.
	 */
	public List<Wort> loeseOptimal(List<String> woerter) {
		// TODO
		return null;
	}

	/**
	 * Algorithmus zur Berechnung einer L�sung. Diese Methode wird rekursiv
	 * ausgef�hrt.
	 * 
	 * @param eingetragen
	 *            W�rter, die bereits im Kreuzwortr�tsel eingetragen sind
	 * @param uebrig
	 *            W�rter, die noch nicht im Kreuzwortr�tsel eingetragen sind
	 * @return Gibt den zuerst gefunden Weg zur�ck. Wenn die Anzahl der
	 *         zur�ckgegebenen Worte nicht der erwarteten Anzahl entspricht.
	 */
	private List<Wort> loese(List<Wort> eingetragen, List<String> uebrig) {
		// TODO
		return null;
	}

	/**
	 * Algorithmus zur Berechnung einer optimierten L�sung. Diese Methode wird
	 * rekursiv ausgef�hrt.
	 * 
	 * @param eingetragen
	 *            W�rter, die bereits im Kreuzwortr�tsel eingetragen sind
	 * @param uebrig
	 *            W�rter, die noch nicht im Kreuzwortr�tsel eingetragen sind
	 * @return Gibt den besten gefunden Weg zur�ck. Wenn die Anzahl der
	 *         zur�ckgegebenen Worte nicht der erwarteten Anzahl entspricht.
	 */
	private List<Wort> loeseOptimal(List<Wort> eingetragen, List<String> uebrig) {
		// TODO
		return null;
	}

}
