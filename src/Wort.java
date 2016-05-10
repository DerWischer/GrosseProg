import java.util.ArrayList;
import java.util.List;

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
		this.text = text;
		this.x = x;
		this.y = y;
		this.horizontal = horizontal;
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
	 * Gibt das Wort als String zur�ck
	 * 
	 * @return Wort als String
	 */
	public String getText() {
		return text;
	}

	public boolean isHorizontal() {
		return this.horizontal;
	}

	/**
	 * Gibt den Endpunkt zur�ck. Dies entspricht bei horizontalen W�rter der
	 * letzten X-Koordinate. Bei vertikalen analog der Y-Koordinate.
	 * 
	 * @return X oder Y Wert
	 */
	public int getEndpunkt() {
		if (horizontal) {
			return x + text.length() - 1;
		} else {
			return y - text.length() + 1;
		}

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
		ArrayList<Wort> results = new ArrayList<>();

		// pr�fe ob Worte sich enthalten					
		if (text.contains(wort)){
			int stelle = text.indexOf(wort);
			Wort moeglich;
			if (horizontal){
				moeglich = new Wort(wort, x + stelle, y, horizontal);
			}				
			else {
				moeglich = new Wort(wort, x, y - stelle, horizontal);
			}
			results.add(moeglich);
		} else if (wort.contains(text)){
			int stelle = wort.indexOf(text);
			Wort moeglich;
			if (horizontal){
				moeglich = new Wort(wort, x - stelle, y, horizontal);
			}				
			else {
				moeglich = new Wort(wort, x, y + stelle, horizontal);
			}
			results.add(moeglich);
		}			
								
		for (int wIndex = 0; wIndex < wort.length(); wIndex++) {
			char tmpC = wort.charAt(wIndex);
			int matchIndex = text.indexOf(tmpC);
			while (matchIndex != -1) {

				// berechne Koordinaten (unterscheide dabei die richtung des
				// wortes)
				int x1, y1;
				if (horizontal) {
					x1 = this.x + matchIndex;
					y1 = this.y + wIndex;
				} else {
					x1 = this.x - wIndex;
					y1 = this.y - matchIndex;
				}

				// erzeihe neues m�gliches wort, mit invertierter richtung
				Wort moeglichesWort = new Wort(wort, x1, y1, !horizontal);
				results.add(moeglichesWort);

				// letzten Buchstaben von neuen Wort an den ersten von Text
				// anlegen
				if (wIndex == wort.length() - 1 && matchIndex == 0) {
					if (horizontal) { // hor
						moeglichesWort = new Wort(wort, this.x - wort.length() + 1, this.y, true);
						results.add(moeglichesWort);
					} else { // vert
						moeglichesWort = new Wort(wort, this.x, this.y + wort.length() - 1, false);
						results.add(moeglichesWort);
					}
				}

				// ersten Buchstaben von neuen Wort an den letzten von Text
				// anlegen
				if (wIndex == 0 && matchIndex == text.length() - 1) {
					if (horizontal) { // hor
						moeglichesWort = new Wort(wort, this.x + matchIndex, this.y, true);
						results.add(moeglichesWort);
					} else { // vert
						moeglichesWort = new Wort(wort, this.x, this.y - matchIndex, false);
						results.add(moeglichesWort);
					}
				}

				// n�chsten Index berechnen
				matchIndex = text.indexOf(tmpC, matchIndex + 1);
			}
		}

		return results.toArray(new Wort[0]);
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
		if (horizontal && w.horizontal) {
			if (this.y != w.y) { // verschiedene h�hen
				return false;
			}
			

			// pr�fe ob worte sich enthalten
			Wort langesWort = null;
			Wort kurzesWort = null;
			if (text.contains(w.text)){
				langesWort = this;
				kurzesWort = w;
			} else if (w.text.contains(text)){
				langesWort = w;
				kurzesWort = this;
			}			
			if (langesWort != null && kurzesWort != null){
				int stelle = langesWort.text.indexOf(kurzesWort.text);
				if (langesWort.x + stelle == kurzesWort.x){
					return false;
				} else {
					return true;
				}
			} else if (this.x == w.x) {
				return true;
			}
			
			Wort links = (this.x < w.x) ? this : w;
			Wort rechts = (this.x > w.x) ? this : w;

			int distanz = Math.abs(rechts.x - links.x) - links.text.length();
			if (distanz >= 0) {
				return false;
			} else {				
				String subStringLinks ="";
				String subStringRechts = "";
				
				int geschnitten = Math.abs(distanz);
				if (geschnitten > rechts.text.length()){
					return true;
				}
				subStringLinks = links.text.substring(links.text.length() - geschnitten, links.text.length());				
				subStringRechts = rechts.text.substring(0, geschnitten);
				
				// pr�fe ob die geschnittenen Buchstaben gleich sind
				if (subStringLinks.equals(subStringRechts)) { 
					return false;
				} else {
					return true;
				}
				
			}
		} else if (!horizontal && !w.horizontal)

		{
			if (this.x != w.x) { // verschiedene stellen
				return false;
			}

			// pr�fe ob worte sich enthalten
						Wort langesWort = null;
						Wort kurzesWort = null;
						if (text.contains(w.text)){
							langesWort = this;
							kurzesWort = w;
						} else if (w.text.contains(text)){
							langesWort = w;
							kurzesWort = this;
						}			
						if (langesWort != null && kurzesWort != null){
							int stelle = langesWort.text.indexOf(kurzesWort.text);
							if (langesWort.y - stelle == kurzesWort.y){
								return false;
							} else {
								return true;
							}
						} else if (this.y == w.y) {
							return true;
						}
			
			

			Wort unten = (this.y < w.y) ? this : w;
			Wort oben = (this.y > w.y) ? this : w;

			int distanz = Math.abs(oben.y - unten.y) - oben.text.length();
			if (distanz >= 0) {
				return false;
			} else {
				int geschnitten = Math.abs(distanz);
				if (geschnitten > unten.text.length()){
					return true;
				}
				String subStringOben = oben.text.substring(oben.text.length() - geschnitten, oben.text.length());
				String subStringUnten = unten.text.substring(0, geschnitten);
				if (subStringOben.equals(subStringUnten)) {
					return false;
				} else {
					return true;
				}
			}
		} else {
			Wort hWort = (this.horizontal) ? this : w;
			Wort vWort = (!this.horizontal) ? this : w;

			int hSchranke = hWort.y;
			int vSchranke = vWort.x;

			int hAnfang = hWort.x;
			int hEnde = hAnfang + hWort.text.length() - 1;
			int vAnfang = vWort.y;
			int vEnde = vAnfang - vWort.text.length() + 1;

			boolean schneidetHorizontal = vSchranke >= hAnfang && vSchranke <= hEnde;
			boolean schneidetVertikal = hSchranke <= vAnfang && hSchranke >= vEnde;

			if (schneidetHorizontal && schneidetVertikal) {
				// Schnittpunkt bestimmen
				int hIndex = Math.abs(vSchranke - hWort.x);
				int vIndex = Math.abs(hSchranke - vWort.y);
				// Buchstabe an Schnittpunkt
				char hBuchstabe = hWort.text.charAt(hIndex);
				char vBuchstabe = vWort.text.charAt(vIndex);

				return hBuchstabe != vBuchstabe;
			} else { // kein Schnittpunkt m�glich
				return false;
			}
		}
	}

	public static int getMinX(List<Wort> woerter) {
		int minX = 0;
		for (int i = 0; i < woerter.size(); i++) {
			Wort tmp = woerter.get(i);
			if (tmp.isHorizontal()) {
				if (minX > tmp.getX()) {
					minX = tmp.getX();
				}
			}
		}
		return minX;
	}

	public static int getMinY(List<Wort> woerter) {
		int minY = 0;
		for (int i = 0; i < woerter.size(); i++) {
			Wort tmp = woerter.get(i);
			if (!tmp.isHorizontal()) {
				if (minY > tmp.getEndpunkt()) {
					minY = tmp.getEndpunkt();
				}
			}
		}
		return minY;
	}

	public static int getSpannweite(List<Wort> woerter) {
		int minX = 1;
		int maxX = 1;
		for (Wort tmp: woerter) {			
			if (tmp.isHorizontal()) {
				if (minX > tmp.getX()) {
					minX = tmp.getX();
				}
				if (maxX < tmp.getEndpunkt()) {
					maxX = tmp.getEndpunkt();
				}
			}
		}

		return Math.abs(maxX - minX) + 1;
	}

	public static int getSpannh�he(List<Wort> woerter) {
		int minY = 1;
		int maxY = 1;		
		for (Wort tmp: woerter) {			
			if (!tmp.isHorizontal()) {
				if (maxY < tmp.getY()) {
					maxY = tmp.getY();
				}
				if (minY > tmp.getEndpunkt()) {
					minY = tmp.getEndpunkt();
				}
			}
		}
		return Math.abs(maxY - minY) + 1;
	}

	public static int getKompaktheitsma�(List<Wort> woerter) {
		return getSpannh�he(woerter) * getSpannweite(woerter);
	}

	public static boolean checkForKollision(List<Wort> woerter, Wort w) {
		for (Wort tmpW : woerter) {
			if (tmpW.kollidieren(w)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Wort: '" + text + "' K(" + x + "|" + y + ") R=" + (horizontal ? "h" : "v");
	}
}
