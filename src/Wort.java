/**
 * Diese Klasse repr�sentiert ein Wort im Kreuzwortr�tsel. W�rter haben einen
 * Test, eine Richtung sowie X und Y Koordinaten
 * 
 * @author Joshua
 *
 */
public class Wort {

	/**
	 * Enth�lt das Wort
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
	 *            X-Koordinate des Worts im R�tsel
	 * @param y
	 *            Y-Koordinate des Worts im R�tsel
	 * @param horizontal
	 *            Richtung des Worts. TRUE bedeutet von links nach rechts, FALSE
	 *            hei�t von oben nach unten
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
	 * Gibt die X-Koordinate zur�ck
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
	 * Gibt die Y-Koordinate zur�ck
	 * 
	 * @return Y-Koordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Ermittelt alle M�glichkeiten den �bergebenen String an dieses Wort
	 * anzulegen. Dazu wird gepr�ft, ob der String im Wort enthalten ist, oder
	 * ob einzelne Zeichen �bereinstimmen
	 * 
	 * @param wort
	 *            Wort das angelegt werden soll
	 * @return Ein Wort-Array, wobei jedes Wort eine M�glichkeit zum anlegen
	 *         darstellt. Sofern es keine M�glichkeit gibt wird NULL
	 *         zur�ckgegeben
	 */
	public Wort[] legeAn(String wort) {
		// TODO
		return null;
	}

	/**
	 * Pr�ft ob zwei Worte miteinander kollidieren. Zwei Worte haben eine
	 * Kollision, wenn sie sich �berschneiden und an den Schnittpunkten
	 * verschiedene Zeichen haben
	 * 
	 * @param w
	 *            Wort mit eine Kollision gepr�ft wird
	 * @return TRUE wenn es eine Kollisiion gibt, sonst FALSE
	 */
	public boolean kollidieren(Wort w) {
		// TODO
		return true;
	}

}
