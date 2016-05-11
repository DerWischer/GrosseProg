import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Diese Klasse repr�sentiert ein Kreuzwortr�tsel
 * 
 * @author Joshua
 *
 */
public class Raetsel {

	/**
	 * Enth�lt alle im R�tsel vorkommenden W�rter
	 */
	private List<Wort> woerter;

	private int spannweite;

	private int spannh�he;

	/**
	 * Wird verwendet um Buchstaben zuf�llig zu erzeugen
	 */
	private Random random;

	/**
	 * Erzeugt ein neues Kreuzwortr�tsel
	 * 
	 * @param woerter
	 *            W�rte die im R�tsel vorkommen
	 */
	public Raetsel(List<Wort> woerter) {
		this.woerter = woerter;
		random = new Random();

		spannweite = Wort.getSpannweite(woerter);
		spannh�he = Wort.getSpannh�he(woerter);

		Wort.verschiebeInsPositive(woerter);
	}	

	/**
	 * Erzeuge das R�tsel mit Leerstellen
	 * 
	 * @return Gibt das R�tsel zeilenweise zur�ck
	 */
	public String[] getRasterLeer() {
		char[][] feld = erzeugeCharRaster();

		ArrayList<String> lines = new ArrayList<>();
		lines.add("R�tsel nicht versteckt");
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
	 * Erzeuge das R�tsel mit zuf�llig generierten Buchstaben anstelle von
	 * Leerzeichen
	 * 
	 * @return Gibt das R�tsel zeilenweise zur�ck
	 */
	public String[] getRasterZufall() {
		char[][] feld = erzeugeCharRaster();

		ArrayList<String> lines = new ArrayList<>();
		lines.add("R�tsel versteckt");
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
	
	public int getKompaktheitsma�(){
		return Wort.getKompaktheitsma�(woerter);
	}

	private char[][] erzeugeCharRaster() {
		char[][] feld = new char[spannh�he][spannweite];
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
