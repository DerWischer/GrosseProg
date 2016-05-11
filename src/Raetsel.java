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
	 * Enthält alle im Rätsel vorkommenden Wörter
	 */
	private List<Wort> woerter;

	private int spannweite;

	private int spannhöhe;

	/**
	 * Wird verwendet um Buchstaben zufällig zu erzeugen
	 */
	private Random random;

	/**
	 * Erzeugt ein neues Kreuzworträtsel
	 * 
	 * @param woerter
	 *            Wörte die im Rätsel vorkommen
	 */
	public Raetsel(List<Wort> woerter) {
		this.woerter = woerter;
		random = new Random();

		spannweite = Wort.getSpannweite(woerter);
		spannhöhe = Wort.getSpannhöhe(woerter);

		Wort.verschiebeInsPositive(woerter);
	}	

	/**
	 * Erzeuge das Rätsel mit Leerstellen
	 * 
	 * @return Gibt das Rätsel zeilenweise zurück
	 */
	public String[] getRasterLeer() {
		char[][] feld = erzeugeCharRaster();

		ArrayList<String> lines = new ArrayList<>();
		lines.add("Rätsel nicht versteckt");
		for (int i = 0; i < feld.length; i++) {
			String tmp = "";
			for (char c : feld[i]) {
				if (!Character.isAlphabetic(c)) {
					c = ' ';
				}
				tmp += c;
			}
			lines.add(tmp);
		}
		return lines.toArray(new String[0]);
	}

	/**
	 * Erzeuge das Rätsel mit zufällig generierten Buchstaben anstelle von
	 * Leerzeichen
	 * 
	 * @return Gibt das Rätsel zeilenweise zurück
	 */
	public String[] getRasterZufall() {
		char[][] feld = erzeugeCharRaster();

		ArrayList<String> lines = new ArrayList<>();
		lines.add("Rätsel versteckt");
		for (int i = 0; i < feld.length; i++) {
			String tmp = "";
			for (char c : feld[i]) {
				if (!Character.isAlphabetic(c)) {
					c = (char) (65 + random.nextInt(26));
				}
				tmp += c;
			}
			lines.add(tmp);
		}
		return lines.toArray(new String[0]);
	}
	
	public int getKompaktheitsmaß(){
		return Wort.getKompaktheitsmaß(woerter);
	}

	private char[][] erzeugeCharRaster() {
		char[][] feld = new char[spannhöhe][spannweite];
		for (Wort w : woerter) {
			String text = w.getText();
			if (text.equals("VERSUCH")){
				int x = 1;
			}
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
		
		char[][] finalFeld = new char[feld.length][feld[0].length];
		for (int zeile = 0; zeile < finalFeld.length; zeile++){
			char[] tmp = feld[feld.length-1-zeile];
			finalFeld[zeile] = tmp;
		}
		return finalFeld;
	}

}
