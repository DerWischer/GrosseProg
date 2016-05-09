/**
 * Diese Klasse repräsentiert ein Wort im Kreuzworträtsel. Wörter haben einen
 * Test, eine Richtung sowie X und Y Koordinaten
 * 
 * @author Joshua
 *
 */
public class Wort {

	/**
	 * Enthält das Wort
	 */
	private String text;

	/**
	 * X-Koordinate des Worts
	 */
	private int x;

	/**
	 * Y-Koordinate des Worts
	 */
	private int y;

	/**
	 * Richtung des Worts. Wenn TRUE, dann wird es von links nach rechts
	 * geschrieben. Sonst von oben nach unten
	 */
	private boolean horizontal;

	/**
	 * Erzeugt ein neues Wort
	 * 
	 * @param text
	 *            das Wort
	 * @param x
	 *            X-Koordinate des Worts im Rätsel
	 * @param y
	 *            Y-Koordinate des Worts im Rätsel
	 * @param horizontal
	 *            Richtung des Worts. TRUE bedeutet von links nach rechts, FALSE
	 *            heißt von oben nach unten
	 */
	public Wort(String text, int x, int y, boolean horizontal) {
		// TODO
	}

	/**
	 * Setzt die X-Koordinate
	 * 
	 * @param x
	 *            Neuer Wert der X-Koordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gibt die X-Koordinate zurück
	 * 
	 * @return X-Koordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Setzt die Y-Koordinate
	 * 
	 * @param y
	 *            Neuer Wert der Y-Koordinate
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Gibt die Y-Koordinate zurück
	 * 
	 * @return Y-Koordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Ermittelt alle Möglichkeiten den übergebenen String an dieses Wort
	 * anzulegen. Dazu wird geprüft, ob der String im Wort enthalten ist, oder
	 * ob einzelne Zeichen übereinstimmen
	 * 
	 * @param wort
	 *            Wort das angelegt werden soll
	 * @return Ein Wort-Array, wobei jedes Wort eine Möglichkeit zum anlegen
	 *         darstellt. Sofern es keine Möglichkeit gibt wird NULL
	 *         zurückgegeben
	 */
	public Wort[] legeAn(String wort) {
		// TODO
		return null;
	}

	/**
	 * Prüft ob zwei Worte miteinander kollidieren. Zwei Worte haben eine
	 * Kollision, wenn sie sich überschneiden und an den Schnittpunkten
	 * verschiedene Zeichen haben
	 * 
	 * @param w
	 *            Wort mit eine Kollision geprüft wird
	 * @return TRUE wenn es eine Kollisiion gibt, sonst FALSE
	 */
	public boolean kollidieren(Wort w) {
		// TODO
		return true;
	}

}
