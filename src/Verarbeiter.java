import java.util.ArrayList;
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
	 *            Alle Wörter die im Kreuzworträtsel enthalten sein sollen
	 * @return Alle Wörter mit entsprechenden Positionen (X,Y) und
	 *         Schreibrichtung. Es sollte geprüft werden, ob die Anzahl der
	 *         Wörter mit der erwartetet übereinstimmt.
	 */
	public List<Wort> loese(List<String> woerter) {
		ArrayList<Wort> eingetragen = new ArrayList<>();
		return loese(eingetragen, woerter);
	}

	/**
	 * Ermittelt eine optimierte Lösung für ein Rätsel.
	 * 
	 * @param woerter
	 *            Alle Wörter die im Kreuzworträtsel enthalten sein sollen
	 * @return Alle Wörter mit entsprechenden Positionen (X,Y) und
	 *         Schreibrichtung. Es sollte geprüft werden, ob die Anzahl der
	 *         Wörter mit der erwartetet übereinstimmt.
	 */
	public List<Wort> loeseOptimal(List<String> woerter) {
		List<Wort> lsg = loese(woerter);

		if (lsg == null){
			return null;
		}			
		
		int maximaleKompaktheit = Wort.getKompaktheitsmaß(lsg);		
		ArrayList<Wort> eingetragen = new ArrayList<>();
		return loeseOptimal(eingetragen, woerter, maximaleKompaktheit);
	}

	/**
	 * Algorithmus zur Berechnung einer Lösung. Diese Methode wird rekursiv
	 * ausgeführt.
	 * 
	 * @param eingetragen
	 *            Wörter, die bereits im Kreuzworträtsel eingetragen sind
	 * @param uebrig
	 *            Wörter, die noch nicht im Kreuzworträtsel eingetragen sind
	 * @return Gibt den zuerst gefunden Weg zurück. Wenn die Anzahl der
	 *         zurückgegebenen Worte nicht der erwarteten Anzahl entspricht.
	 */
	private List<Wort> loese(List<Wort> eingetragen, List<String> uebrig) {
		if (uebrig.isEmpty()) {
			return eingetragen;
		}

		List<Wort> besteLsg = null;
		int besteKompaktheit = Integer.MAX_VALUE;

		for (String tmpU : uebrig) {
			if (eingetragen.isEmpty()) {
				Wort wHor = new Wort(tmpU, 0, 0, true);
				List<Wort> kE = new ArrayList<>(eingetragen);
				List<String> kU = new ArrayList<>(uebrig);
				kE.add(wHor);
				kU.remove(tmpU);
				List<Wort> lsg = loese(kE, kU);
				if (lsg == null) {
					return null;
				}
				if (lsg.size() == uebrig.size()) {
					return lsg;
				} else {
					return eingetragen;
				}
			} else {
				for (Wort tmpE : eingetragen) {
					Wort[] moegliche = tmpE.legeAn(tmpU);
					for (Wort tmpW : moegliche) {
						if (!Wort.checkForKollision(eingetragen, tmpW)) {
							List<Wort> kE = new ArrayList<>(eingetragen);
							List<String> kU = new ArrayList<>(uebrig);
							kE.add(tmpW);
							kU.remove(tmpU);
							List<Wort> lsg = loese(kE, kU);
							if (kU.isEmpty()) {
								return lsg;
							}
							if (lsg != null) {
								int kompaktheit = Wort.getKompaktheitsmaß(lsg);
								if (kompaktheit < besteKompaktheit) {
									besteKompaktheit = kompaktheit;
									besteLsg = lsg;
									return besteLsg;
								}
							}
						}
					}
				}
			}

		}
		return besteLsg;
	}

	/**
	 * Algorithmus zur Berechnung einer optimierten Lösung. Diese Methode wird
	 * rekursiv ausgeführt.
	 * 
	 * @param eingetragen
	 *            Wörter, die bereits im Kreuzworträtsel eingetragen sind
	 * @param uebrig
	 *            Wörter, die noch nicht im Kreuzworträtsel eingetragen sind
	 * @return Gibt den besten gefunden Weg zurück. Wenn die Anzahl der
	 *         zurückgegebenen Worte nicht der erwarteten Anzahl entspricht.
	 */
	private List<Wort> loeseOptimal(List<Wort> eingetragen, List<String> uebrig, int maximaleKompaktheit) {
		if (uebrig.isEmpty()) {
			return eingetragen;
		}

		int aktuelleKompaktheit = Wort.getKompaktheitsmaß(eingetragen);
		if (aktuelleKompaktheit > maximaleKompaktheit) {
			return eingetragen;
		}

		List<Wort> besteLsg = null;
		int besteKompaktheit = maximaleKompaktheit;

		for (String tmpU : uebrig) {
			if (eingetragen.isEmpty()) {
				Wort wHor = new Wort(tmpU, 0, 0, true);
				List<Wort> kE = new ArrayList<>(eingetragen);
				List<String> kU = new ArrayList<>(uebrig);
				kE.add(wHor);
				kU.remove(tmpU);
				List<Wort> lsg = loeseOptimal(kE, kU, besteKompaktheit);

				if (lsg == null){
					return lsg;
				}
				if (lsg.size() == uebrig.size()) {
					return lsg;
				} else {
					return eingetragen;
				}
			} else {
				for (Wort tmpE : eingetragen) {
					Wort[] moegliche = tmpE.legeAn(tmpU);
					for (Wort tmpW : moegliche) {
						if (!Wort.checkForKollision(eingetragen, tmpW)) {
							List<Wort> kE = new ArrayList<>(eingetragen);
							List<String> kU = new ArrayList<>(uebrig);
							kE.add(tmpW);
							kU.remove(tmpU);
							List<Wort> lsg = loeseOptimal(kE, kU, besteKompaktheit);
							if (kU.isEmpty()) {
								return lsg;
							}
							if (lsg != null) {
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
