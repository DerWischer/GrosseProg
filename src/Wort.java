import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse repräsentiert ein Wort im Kreuzworträtsel. Wörter haben einen
 * Text, X und Y Koordinaten, sowie eine Schreibrichtung
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
	 * (horizontal) geschrieben. Wenn FALSE, dann von oben nach unten (vertikal)
	 */
	private boolean horizontal;

	/**
	 * Erzeugt ein neues Wort
	 * 
	 * @param text
	 *            das Wort
	 * @param x
	 *            X-Koordinate
	 * @param y
	 *            Y-Koordinate
	 * @param horizontal
	 *            Richtung des Worts. TRUE bedeutet von links nach rechts, FALSE
	 *            heißt von oben nach unten
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
	 * Gibt das Wort als String zurück
	 * 
	 * @return Wort als String
	 */
	public String getText() {
		return text;
	}

	/**
	 * Gibt die Schreibrichtung zurück
	 * 
	 * @return TRUE bedeutet eine horizontale, FALSE eine vertikale
	 *         Schreibrichtung
	 */
	public boolean isHorizontal() {
		return this.horizontal;
	}

	/**
	 * Berechnet den Endpunkt des Wortes (X-Wert bei horizontalem Wort, sonst
	 * Y-Wert)
	 * 
	 * @return Endpunkt
	 */
	public int getEndpunkt() {
		if (horizontal) {
			return x + text.length() - 1;
		} else {
			return y - text.length() + 1;
		}

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
		ArrayList<Wort> results = new ArrayList<>();

		if (text.contains(wort)) {
			// text enthält wort
			int startIndex = text.indexOf(wort);
			int x1 = x;
			int y1 = y;
			if (horizontal) {
				x1 += startIndex;
			} else {
				y1 -= startIndex;
			}
			Wort moeglich = new Wort(wort, x1, y1, horizontal);
			results.add(moeglich);
		} else if (wort.contains(text)) {
			// wort enthält text
			int startIndex = text.indexOf(wort);
			int x1 = x;
			int y1 = y;
			if (horizontal) {
				x1 -= startIndex;
			} else {
				y1 += startIndex;
			}
			Wort moeglich = new Wort(wort, x1, y1, horizontal);
			results.add(moeglich);
		}

		for (int wIndex = 0; wIndex < wort.length(); wIndex++) {
			char b = wort.charAt(wIndex);
			int nextIndex = text.indexOf(b);
			while (nextIndex != -1) {
				int x1 = x;
				int y1 = y;
				if (horizontal) {
					x1 += nextIndex;
					y1 += wIndex;
				} else {
					x1 -= wIndex;
					y1 -= nextIndex;
				}
				Wort moeglich = new Wort(wort, x1, y1, !horizontal);

				results.add(moeglich);

				if (wIndex == 0 && nextIndex == text.length() - 1) {
					// der erste Buchstabe von wort ist gleich dem letzten von
					// text
					moeglich = new Wort(wort, x1, y1, horizontal);

					results.add(moeglich);
				} else if (wIndex == wort.length() - 1 && nextIndex == 0) {
					// der letzte Buchstabe von wort ist gleich dem ersten von
					// text
					moeglich = new Wort(wort, x1, y1, horizontal);

					results.add(moeglich);
				}
				nextIndex = text.indexOf(b, nextIndex + 1);
			}
		}

		return results.toArray(new Wort[0]);
	}

	/**
	 * 
	 * 
	 * @param w
	 *            Wort mit eine Kollision geprüft wird
	 * @return TRUE wenn es eine Kollisiion gibt, sonst FALSE
	 */

	/**
	 * Prüft ob zwei Worte miteinander kollidieren. Zwei Worte haben eine
	 * Kollision, wenn sie sich überschneiden und an den Schnittpunkten oder dem
	 * Schnittpunkt verschiedene Zeichen haben.
	 * 
	 * @param w1
	 *            Wort das geprüft wird
	 * @param w2
	 *            Wort das geprüft wird
	 * @return ob es eine Kollision gibt
	 */
	public static boolean kollidieren(Wort w1, Wort w2) {
		if (w1.x < 0 || w1.y < 0 || w2.x < 0 || w2.y < 0) {
			// Kopiere w1 und w2
			Wort kW1 = new Wort(w1.text, w1.x, w1.y, w1.horizontal);
			Wort kW2 = new Wort(w2.text, w2.x, w2.y, w2.horizontal);
			List<Wort> list = new ArrayList<>();
			list.add(kW1);
			list.add(kW2);
			// verschiebe Kopien in positiven Bereich
			Wort.verschiebeInsPositive(list);
			w1 = kW1;
			w2 = kW2;
		}

		if (w1.horizontal != w2.horizontal) { // ungleiche Schreibrichtungen
			return Wort.kollidierenHUndV(w1, w2);
		} else { // gleiche Schreibrichtungen
			boolean verschiedeneReihenOderSpalten;
			if (w1.horizontal) {
				verschiedeneReihenOderSpalten = w1.y != w2.y; // horizontale
																// Wörter:
																// selbe Reihe?
			} else {
				verschiedeneReihenOderSpalten = w1.x != w2.x; // vertikale
																// Wörter:
																// selbe Spalte?
			}

			if (!verschiedeneReihenOderSpalten) {
				// Wörter stehen in der selben Reihe/Spalte
				if (w1.horizontal) {
					return Wort.kollidierenHUndH(w1, w2);
				} else {
					return Wort.kollidierenVUndV(w1, w2);
				}
			}
		}
		return false;
	}

	/**
	 * Hilfsmethode, zur Überpfrüfung auf Kollision von zwei horizontalen
	 * Wörtern
	 * 
	 * @param w1
	 *            Wort das überprüft wird
	 * @param w2
	 *            Wort das überprüft wird
	 * @return ob es eine Kollision gibt
	 */
	private static boolean kollidierenHUndH(Wort w1, Wort w2) {
		Wort lWort = (w1.x <= w2.x) ? w1 : w2;
		Wort rWort = (!(w1.x <= w2.x)) ? w1 : w2;

		int lxStart = lWort.x;
		int lxEnde = lWort.getEndpunkt();

		int rxStart = rWort.x;
		int rxEnde = rWort.getEndpunkt();

		if (lxStart <= rxStart && rxStart <= lxEnde) {
			// rechtes wort startet im linken
			int lStartIndex = rxStart - lxStart;
			int lEndeIndex;

			int rStartIndex = 0;
			int rEndeIndex;
			if (rxEnde <= lxEnde) {
				// rechtes wort ended im linken
				lEndeIndex = rxEnde - lxStart;
				rEndeIndex = rxEnde - rxStart;
			} else {
				// rechtes wort ended außerhalb vom linken
				lEndeIndex = lxEnde - lxStart;
				rEndeIndex = lxEnde - rxStart;
			}

			String lBereich = lWort.text.substring(lStartIndex, lEndeIndex + 1);
			String rBereich = rWort.text.substring(rStartIndex, rEndeIndex + 1);

			if (!lBereich.equals(rBereich)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Hilfsmethode, zur Überpfrüfung auf Kollision von zwei vertikalen Wörtern
	 * 
	 * @param w1
	 *            Wort das überprüft wird
	 * @param w2
	 *            Wort das überprüft wird
	 * @return ob es eine Kollision gibt
	 */
	private static boolean kollidierenVUndV(Wort w1, Wort w2) {
		Wort oWort = (w1.y >= w2.y) ? w1 : w2;
		Wort uWort = (!(w1.y >= w2.y)) ? w1 : w2;

		int oyStart = oWort.y;
		int oyEnde = oWort.getEndpunkt();

		int uyStart = uWort.y;
		int uyEnde = uWort.getEndpunkt();

		if (oyStart >= uyStart && uyStart >= oyEnde) {
			// unteres wort startet im oberen
			int oStartIndex = oyStart - uyStart;
			int oEndeIndex;

			int uStartIndex = 0;
			int uEndeIndex;
			if (uyEnde >= oyEnde) {
				// unteres wort ended im oberen
				oEndeIndex = oyStart - uyEnde;
				uEndeIndex = uyStart - uyEnde;
			} else {
				// unteres wort ended außerhalb vom oberen
				oEndeIndex = oyStart - oyEnde;
				uEndeIndex = uyStart - oyEnde;
			}

			// bestimmte Buchstaben im geschnittenen Bereich
			String lBereich = oWort.text.substring(oStartIndex, oEndeIndex + 1);
			String rBereich = uWort.text.substring(uStartIndex, uEndeIndex + 1);

			if (!lBereich.equals(rBereich)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Hilfsmethode, zur Überpfrüfung auf Kollision von einem horizontal und
	 * einem vertikal geschriebenen Wort;
	 * 
	 * @param w1
	 *            Wort das überprüft wird
	 * @param w2
	 *            Wort das überprüft wird
	 * @return ob es eine Kollision gibt
	 */
	private static boolean kollidierenHUndV(Wort w1, Wort w2) {
		Wort hWort = (w1.horizontal) ? w1 : w2;
		Wort vWort = (!w1.horizontal) ? w1 : w2;

		int xStart = hWort.x;
		int xEnde = hWort.getEndpunkt();
		int ySchranke = hWort.y;

		int yStart = vWort.y;
		int yEnde = vWort.getEndpunkt();
		int xSchranke = vWort.x;

		boolean vGeschnitten = xSchranke >= xStart && xSchranke <= xEnde;
		boolean hGeschnitten = ySchranke <= yStart && ySchranke >= yEnde;

		if (vGeschnitten && hGeschnitten) {
			int hStelle = xSchranke - xStart;
			char hBuchstabe = hWort.text.charAt(hStelle);

			int vStelle = yStart - ySchranke;
			char vBuchstabe = vWort.text.charAt(vStelle);

			if (hBuchstabe != vBuchstabe) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Berechnet den kleinsten X-Wert der übergebenen Wörter
	 * 
	 * @param woerter
	 *            Wörter für die Berechnung
	 * @return kleinster X-Wert
	 */
	public static int getMinX(List<Wort> woerter) {
		int minX = 0;
		for (int i = 0; i < woerter.size(); i++) {
			Wort tmp = woerter.get(i);

			if (minX > tmp.getX()) {
				minX = tmp.getX();
			}

		}
		return minX;
	}

	/**
	 * Berechnet den kleinsten Y-Wert der übergebenen Wörter
	 * 
	 * @param woerter
	 *            Wörter für die Berechnung
	 * @return kleinster Y-Wert
	 */
	public static int getMinY(List<Wort> woerter) {
		int minY = 0;
		for (int i = 0; i < woerter.size(); i++) {
			Wort tmp = woerter.get(i);
			int tmpMin = tmp.getY();
			if (!tmp.horizontal) {
				tmpMin = tmp.getEndpunkt();
			}
			if (minY > tmpMin) {
				minY = tmpMin;
			}

		}
		return minY;
	}

	/**
	 * Berechnet die Breite/Spannweite der übergebenen Wörter. Diese entspricht
	 * der Distanz zwischen kleinstem und größten X-Wert
	 * 
	 * @param woerter
	 *            Wörter für die Berechnung
	 * @return Breite
	 */
	public static int getBreite(List<Wort> woerter) {
		int minX = 1;
		int maxX = 1;
		for (Wort tmp : woerter) {
			int tmpMin = tmp.getX();
			int tmpMax = tmp.getX();
			if (tmp.horizontal) {
				tmpMax = tmp.getEndpunkt();
			}
			minX = (tmpMin < minX) ? tmpMin : minX;
			maxX = (tmpMax > maxX) ? tmpMax : maxX;
		}

		return Math.abs(maxX - minX) + 1;
	}

	/**
	 * Berechnet die Höhe/Spannhöhe der übergebenen Wörter. Diese entspricht der
	 * Distanz zwischen kleinstem und größten Y-Wert
	 * 
	 * @param woerter
	 *            Wörter für die Berechnung
	 * @return Höhe
	 */
	public static int getHoehe(List<Wort> woerter) {
		int minY = 1;
		int maxY = 1;
		for (Wort tmp : woerter) {
			int tmpMin = tmp.getY();
			int tmpMax = tmp.getY();
			if (!tmp.horizontal) {
				tmpMin = tmp.getEndpunkt();
			}
			minY = (tmpMin < minY) ? tmpMin : minY;
			maxY = (tmpMax > maxY) ? tmpMax : maxY;
		}
		return Math.abs(maxY - minY) + 1;
	}

	/**
	 * Berechnet das Kompaktheitsnaß: Breite * Höhe
	 * 
	 * @param woerter
	 *            Wörter für die Berechnung
	 * @return Kompaktheitsmaß
	 */
	public static int getKompaktheitsmaß(List<Wort> woerter) {
		return getHoehe(woerter) * getBreite(woerter);
	}

	/**
	 * Überprüft ob das Wort w mit einem der Wörter aus woerter kollidiert.
	 * 
	 * @param woerter
	 *            Wörter
	 * @param w
	 *            Wort
	 * @return ob es eine Kollision gibt
	 */
	public static boolean checkLastWortForKollision(List<Wort> woerter, Wort w) {
		for (Wort tmpW : woerter) {
			if (Wort.kollidieren(tmpW, w)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Verschiebt die Wörter in Positive Koordinaten, bzw. in der I. Quadranten.
	 * 
	 * @param woerter
	 *            Wörter die verschoben werden
	 */
	public static void verschiebeInsPositive(List<Wort> woerter) {
		int minX = Wort.getMinX(woerter);
		int minY = Wort.getMinY(woerter);

		// verschiebe alle wörter ins positive
		for (Wort w : woerter) {
			int tmpX = w.getX() - minX;
			int tmpY = w.getY() - minY;
			w.setX(tmpX);
			w.setY(tmpY);
		}
	}

	@Override
	public String toString() {
		return "Wort: '" + text + "' K(" + x + "|" + y + ") R=" + (horizontal ? "h" : "v");
	}
}
