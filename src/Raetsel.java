import java.util.List;
import java.util.Random;

/** 
 * Diese Klasse repr�sentiert ein Kreuzwortr�tsel
 * @author Joshua
 *
 */
public class Raetsel {

	/**
	 * Enth�lt alle im R�tsel vorkommenden W�rter
	 */
	private List<Wort> woerter;
	
	/**
	 * Wird verwendet um Buchstaben zuf�llig zu erzeugen
	 */
	private Random random;
	
	/**
	 * Erzeugt ein neues Kreuzwortr�tsel
	 * @param woerter W�rte die im R�tsel vorkommen
	 */
	public Raetsel(List<Wort> woerter){
		this.woerter = woerter;
		random = new Random();
	}
	
	/**
	 * Erzeuge das R�tsel mit Leerstellen
	 * @return Gibt das R�tsel zeilenweise zur�ck
	 */
	public String[] getRasterLeer(){
		// TODO
		return null;
	}
	
	/**
	 * Erzeuge das R�tsel mit zuf�llig generierten Buchstaben anstelle von Leerzeichen
	 * @return Gibt das R�tsel zeilenweise zur�ck
	 */
	public String[] getRasterZufall(){
		// TODO
		return null;
	}
	
}
