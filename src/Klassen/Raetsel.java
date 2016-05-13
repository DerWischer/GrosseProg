package Klassen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Diese Klasse repräsentiert ein Kreuzworträtsel
 * 
 * @author Joshua
 *
 */
public class Raetsel {

	/**
	 * Enthaelt alle im Raetsel vorkommenden Woerter
	 */
	private List<Wort> woerter;

	/**
	 * Breite des Raetsels
	 */
	private int spannweite;

	/**
	 * Höhe des Raetsels
	 */
	private int spannhoehe;

	/**
	 * Wird verwendet um Buchstaben zufaellig zu erzeugen
	 */
	private Random random;

	/**
	 * Erzeugt ein neues Kreuzwortraetsel
	 * 
	 * @param woerter
	 *            Worte die im Raetsel vorkommen
	 */
	public Raetsel(List<Wort> woerter) {
		this.woerter = woerter;
		random = new Random();

		spannweite = Wort.getBreite(woerter);
		spannhoehe = Wort.getHoehe(woerter);

		Wort.verschiebeInsPositive(woerter);
	}

	/**
	 * Erzeuge das Raetsel mit Leerstellen
	 * 
	 * @return Gibt das Raetsel zeilenweise zurück
	 */
	public String[] getRasterLeer() {
		char[][] feld = erzeugeCharRaster();

		ArrayList<String> lines = new ArrayList<>();
		lines.add("Raetsel nicht versteckt");
		for (int i = 0; i < feld.length; i++) {
			String tmp = "";
			for (char c : feld[i]) {
				if (!Character.isAlphabetic(c)) {
					c = ' '; // Leerzeichen einsetzen
				}
				tmp += c;
			}
			lines.add(tmp);
		}
		return lines.toArray(new String[0]);
	}

	/**
	 * Erzeuge das Raetsel mit zufaellig generierten Buchstaben anstelle von
	 * Leerzeichen
	 * 
	 * @return Gibt das Raetsel zeilenweise zurück
	 */
	public String[] getRasterZufall() {
		char[][] feld = erzeugeCharRaster();

		ArrayList<String> lines = new ArrayList<>();
		lines.add("Raetsel versteckt");
		for (int i = 0; i < feld.length; i++) {
			String tmp = "";
			for (char c : feld[i]) {
				if (!Character.isAlphabetic(c)) {
					// zufälligen Buchstaben generieren
					c = (char) (65 + random.nextInt(26));
				}
				tmp += c;
			}
			lines.add(tmp);
		}
		return lines.toArray(new String[0]);
	}

	/**
	 * Berechnet das Kompaktheitsmaß des Raetsel
	 * 
	 * @return Kompaktheitsmaß
	 */
	public int getKompaktheitsmaß() {
		return Wort.getKompaktheitsmaß(woerter);
	}

	/**
	 * Hilfsmethode, die aus den Worten des Rätsels ein zweidimensionales
	 * Char-Feld erzeugt
	 * 
	 * @return Raster des Rätsels
	 */
	private char[][] erzeugeCharRaster() {
		char[][] feld = new char[spannhoehe][spannweite];
		for (Wort w : woerter) {
			// nacheinander jedes Wort (Zeichen für Zeichen)in das Feld
			// schreiben
			String text = w.getText();
			int charAtIndex = 0;
			if (w.isHorizontal()) {
				int zeile = w.getY();
				for (int spalte = w.getX(); spalte <= w.getEndpunkt(); spalte++, charAtIndex++) {
					feld[zeile][spalte] = text.charAt(charAtIndex);
				}
			} else {
				int spalte = w.getX();
				for (int zeile = w.getY(); zeile >= w.getEndpunkt(); zeile--, charAtIndex++) {
					feld[zeile][spalte] = text.charAt(charAtIndex);
				}
			}
		}

		/*
		 * Das Feld ist nicht im Richtigen Format. Ein Raster mit z.B den Worten Milch 
		 * und Lupe sähe momentan so aus:
		 * 
		 *   E
		 *   P
		 *   U
		 * MILCH		 
		 *   
		 * Deswegen müssen die Zeilen verschoben werden. Die letzte Zeile muss nachher 
		 * die erste sein.
		 */
		char[][] finalFeld = new char[feld.length][feld[0].length];
		for (int zeile = 0; zeile < finalFeld.length; zeile++) {
			char[] tmp = feld[feld.length - 1 - zeile];
			finalFeld[zeile] = tmp;
		}
		return finalFeld;
	}

}
