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
		List<Wort> lsg = loese(woerter);
		if (lsg != null) {
			int maximaleKompaktheit = Wort.getKompaktheitsmaß(lsg);
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
						if (!Wort.checkLastWortForKollision(eingetragen, tmpW)) {
							List<Wort> kE = new ArrayList<>(eingetragen);
							kE.add(tmpW);
							List<String> kU = new ArrayList<>(uebrig);
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
				List<Wort> lsgH = loeseOptimal(kE, kU, besteKompaktheit);

				Wort wVer = new Wort(tmpU, 0, 0, false);
				kE.remove(wHor);
				kE.add(wVer);
				List<Wort> lsgV = loeseOptimal(kE, kU, besteKompaktheit);

				if (lsgH == null && lsgV == null) {
					return null;
				} else if (lsgH != null && lsgV == null) {
					return lsgH;
				} else if (lsgH == null && lsgV != null) {
					return lsgV;
				} else {
					int hKompaktheitsgrad = Integer.MAX_VALUE;
					if (lsgH.size() == uebrig.size()) {
						hKompaktheitsgrad = Wort.getKompaktheitsmaß(lsgH);
					}

					int vKompaktheitsgrad = Integer.MAX_VALUE;
					if (lsgV.size() == uebrig.size()) {
						vKompaktheitsgrad = Wort.getKompaktheitsmaß(lsgV);
					}

					return (hKompaktheitsgrad <= vKompaktheitsgrad) ? lsgH : lsgV;
				}
			} else {
				for (Wort tmpE : eingetragen) {
					Wort[] moegliche = tmpE.legeAn(tmpU);
					for (Wort tmpW : moegliche) {
						if (!Wort.checkLastWortForKollision(eingetragen, tmpW)) {
							List<Wort> kE = new ArrayList<>(eingetragen);
							kE.add(tmpW);
							List<String> kU = new ArrayList<>(uebrig);
							kU.remove(tmpU);
							List<Wort> lsg = loeseOptimal(kE, kU, besteKompaktheit);
							if (kU.isEmpty()) {
								return lsg;
							}
							if (lsg != null) {
								int kompaktheit = Wort.getKompaktheitsmaß(lsg);
								if (kompaktheit <= besteKompaktheit) {
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
