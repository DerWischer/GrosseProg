package Klassen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * In dieser Klasse werden die Daten verarbeitet. Sie beinhaltet die Algorithmen
 * zur berechnung von Kreuzworträtseln.
 * 
 * @author Joshua
 *
 */
public class Verarbeiter {

	/**
	 * Ermittelt eine mögliche Lösung für ein Rätsel.
	 * 
	 * @param woerter
	 *            Alle Worte die im Kreuzworträtsel enthalten sein sollen
	 * @return Eine Liste mit Wort-Objekten, als mögliche Lösung oder NULL wenn
	 *         es keine Lösung gibt
	 */
	public List<Wort> loese(List<String> woerter) {
		ArrayList<Wort> eingetragen = new ArrayList<>();
		List<Wort> result = loese(eingetragen, woerter);
		return (result == null || result.size() < woerter.size()) ? null : result;
	}

	/**
	 * Ermittelt eine optimale Lösung für ein Rätsel.
	 * 
	 * @param woerter
	 *            Alle Wörter die im Kreuzworträtsel enthalten sein sollen
	 * @return Eine Liste mit Wort-Objekten, als optimale Lösung oder NULL wenn
	 *         es keine Lösung gibt
	 */
	public List<Wort> loeseOptimal(List<String> woerter) {
		List<Wort> moeglicheLsg = loese(woerter);
		if (moeglicheLsg != null) {// berechne nur, wenn es überhaupt eine
									// Lösung gibt
			int maximaleKompaktheit = Wort.getKompaktheitsmaß(moeglicheLsg);
			ArrayList<Wort> eingetragen = new ArrayList<>();
			List<Wort> result = loeseOptimal(eingetragen, woerter, maximaleKompaktheit);
			return (result == null || result.size() < woerter.size()) ? null : result;
		}
		return null;
	}

	/**
	 * Algorithmus zur Berechnung einer Lösung. Diese Methode wird rekursiv
	 * ausgeführt.
	 * 
	 * @param eingetragen
	 *            Wörter, die bereits im Kreuzworträtsel eingetragen sind
	 * @param uebrig
	 *            Wörter, die noch nicht im Kreuzworträtsel eingetragen sind
	 * @return Gibt die erste Lösung zurück.
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
					// Probiere alle Möglichkeiten das Wort anzulegen aus
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
	 * Algorithmus zur Berechnung einer optimierten Lösung. Diese Methode wird
	 * rekursiv ausgeführt.
	 * 
	 * @param eingetragen
	 *            Wörter, die bereits im Kreuzworträtsel eingetragen sind
	 * @param uebrig
	 *            Wörter, die noch nicht im Kreuzworträtsel eingetragen sind
	 * @param maximaleKompaktheit
	 *            Obergrenze für das Kompaktheitsmaß
	 * @return Gibt den besten gefunden Weg zurück.
	 */
	private List<Wort> loeseOptimal(List<Wort> eingetragen, List<String> uebrig, int maximaleKompaktheit) {
		if (uebrig.isEmpty()) {
			// keine weiteren Worte die eingttragen werden müssen
			return eingetragen;
		}

		// Obergrenze prüfen
		int aktuelleKompaktheit = Wort.getKompaktheitsmaß(eingetragen);
		if (aktuelleKompaktheit > maximaleKompaktheit) {
			return eingetragen;
		}

		List<Wort> besteLsg = null;
		int besteKompaktheit = maximaleKompaktheit;

		if (eingetragen.isEmpty()) {
			// Kopie der Liste von kleine nach groß sortieren
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
					// Probiere alle Möglichkeiten das Wort anzulegen aus
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
												// besten Lösung
								int kompaktheit = Wort.getKompaktheitsmaß(lsg);
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
