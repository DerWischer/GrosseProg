import java.util.List;
import java.util.Random;

/** 
 * Diese Klasse repräsentiert ein Kreuzworträtsel
 * @author Joshua
 *
 */
public class Raetsel {

	/**
	 * Enthält alle im Rätsel vorkommenden Wörter
	 */
	private List<Wort> woerter;
	
	/**
	 * Wird verwendet um Buchstaben zufällig zu erzeugen
	 */
	private Random random;
	
	/**
	 * Erzeugt ein neues Kreuzworträtsel
	 * @param woerter Wörte die im Rätsel vorkommen
	 */
	public Raetsel(List<Wort> woerter){
		this.woerter = woerter;
		random = new Random();
	}
	
	/**
	 * Erzeuge das Rätsel mit Leerstellen
	 * @return Gibt das Rätsel zeilenweise zurück
	 */
	public String[] getRasterLeer(){
		// TODO
		return null;
	}
	
	/**
	 * Erzeuge das Rätsel mit zufällig generierten Buchstaben anstelle von Leerzeichen
	 * @return Gibt das Rätsel zeilenweise zurück
	 */
	public String[] getRasterZufall(){
		// TODO
		return null;
	}
	
}
