package Klassen;

import java.util.ArrayList;
import java.util.Collections;
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
	 *            Alle Worte die im Kreuzwortr�tsel enthalten sein sollen
	 * @return Eine Liste mit Wort-Objekten, als m�gliche L�sung oder NULL wenn
	 *         es keine L�sung gibt
	 */
	public List<Wort> loese(List<String> woerter) {
		ArrayList<Wort> eingetragen = new ArrayList<>();
		List<Wort> result = loese(eingetragen, woerter);
		return (result == null || result.size() < woerter.size()) ? null : result;
	}

	/**
	 * Ermittelt eine optimale L�sung f�r ein R�tsel.
	 * 
	 * @param woerter
	 *            Alle W�rter die im Kreuzwortr�tsel enthalten sein sollen
	 * @return Eine Liste mit Wort-Objekten, als optimale L�sung oder NULL wenn
	 *         es keine L�sung gibt
	 */
	public List<Wort> loeseOptimal(List<String> woerter) {
		List<Wort> moeglicheLsg = loese(woerter);
		if (moeglicheLsg != null) {// berechne nur, wenn es �berhaupt eine
									// L�sung gibt
			int maximaleKompaktheit = Wort.getKompaktheitsma�(moeglicheLsg);
			ArrayList<Wort> eingetragen = new ArrayList<>();
			List<Wort> result = loeseOptimal(eingetragen, woerter, maximaleKompaktheit);
			return (result == null || result.size() < woerter.size()) ? null : result;
		}
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
	 * @return Gibt die erste L�sung zur�ck.
	 */
	private List<Wort> loese(List<Wort> eingetragen, List<String> uebrig) {
		for (String tmpU : uebrig) {
			if (eingetragen.isEmpty()) {
				// Wort horizontal anlegen
				Wort wHor = new Wort(tmpU, 0, 0, true);
				List<Wort> kE = new ArrayList<>(eingetragen);
				List<String> kU = new ArrayList<>(uebrig);
				kE.add(wHor);
				kU.remove(tmpU);
				return loese(kE, kU);
			} else {
				for (Wort tmpE : eingetragen) {
					Wort[] moegliche = tmpE.legeAn(tmpU);
					// Probiere alle M�glichkeiten das Wort anzulegen aus
					for (Wort tmpW : moegliche) {
						if (!Wort.checkLastWortForKollision(eingetragen, tmpW)) {
							// Wort kollidiert nicht mit einem der bereits
							// eingetragenen
							List<Wort> kE = new ArrayList<>(eingetragen);
							kE.add(tmpW);
							List<String> kU = new ArrayList<>(uebrig);
							kU.remove(tmpU);

							List<Wort> lsg = loese(kE, kU);
							if (lsg != null) {
								return lsg;
							}
						}
					}
				}
			}

		}
		return eingetragen;
	}

	/**
	 * Algorithmus zur Berechnung einer optimierten L�sung. Diese Methode wird
	 * rekursiv ausgef�hrt.
	 * 
	 * @param eingetragen
	 *            W�rter, die bereits im Kreuzwortr�tsel eingetragen sind
	 * @param uebrig
	 *            W�rter, die noch nicht im Kreuzwortr�tsel eingetragen sind
	 * @param maximaleKompaktheit
	 *            Obergrenze f�r das Kompaktheitsma�
	 * @return Gibt den besten gefunden Weg zur�ck.
	 */
	private List<Wort> loeseOptimal(List<Wort> eingetragen, List<String> uebrig, int maximaleKompaktheit) {
		if (uebrig.isEmpty()) {
			// keine weiteren Worte die eingttragen werden m�ssen
			return eingetragen;
		}

		// Obergrenze pr�fen
		int aktuelleKompaktheit = Wort.getKompaktheitsma�(eingetragen);
		if (aktuelleKompaktheit > maximaleKompaktheit) {
			return eingetragen;
		}

		List<Wort> besteLsg = null;
		int besteKompaktheit = maximaleKompaktheit;

		if (eingetragen.isEmpty()) {
			// Kopie der Liste von kleine nach gro� sortieren
			List<String> kU = new ArrayList<>(uebrig);
			Collections.sort(kU);
			Collections.reverse(kU);

			String kurz = kU.get(0);
			Wort wHor = new Wort(kurz, 0, 0, true);
			List<Wort> kE = new ArrayList<>(eingetragen);

			kE.add(wHor);
			kU.remove(kurz);
			List<Wort> lsgH = loeseOptimal(kE, kU, besteKompaktheit);

			return lsgH;
		} else {
			for (String tmpU : uebrig) {
				for (Wort tmpE : eingetragen) {
					Wort[] moegliche = tmpE.legeAn(tmpU);
					// Probiere alle M�glichkeiten das Wort anzulegen aus
					for (Wort tmpW : moegliche) {
						if (!Wort.checkLastWortForKollision(eingetragen, tmpW)) {
							// Wort kollidiert nicht mit einem der bereits
							// eingetragenen
							List<Wort> kE = new ArrayList<>(eingetragen);
							kE.add(tmpW);
							List<String> kU = new ArrayList<>(uebrig);
							kU.remove(tmpU);
							List<Wort> lsg = loeseOptimal(kE, kU, besteKompaktheit);

							if (kU.isEmpty()) {
								return lsg;
							}

							if (lsg != null) { // vergleiche mit der momentan
												// besten L�sung
								int kompaktheit = Wort.getKompaktheitsma�(lsg);
								if (kompaktheit < besteKompaktheit) {
									besteKompaktheit = kompaktheit;
									besteLsg = lsg;
								}
							}
						}
					}
				}
			}

		}
		return besteLsg;
	}

}
