import java.util.ArrayList;
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
	 *            Alle W�rter die im Kreuzwortr�tsel enthalten sein sollen
	 * @return Alle W�rter mit entsprechenden Positionen (X,Y) und
	 *         Schreibrichtung. Es sollte gepr�ft werden, ob die Anzahl der
	 *         W�rter mit der erwartetet �bereinstimmt.
	 */
	public List<Wort> loese(List<String> woerter) {
		ArrayList<Wort> eingetragen = new ArrayList<>();
		return loese(eingetragen, woerter);
	}

	/**
	 * Ermittelt eine optimierte L�sung f�r ein R�tsel.
	 * 
	 * @param woerter
	 *            Alle W�rter die im Kreuzwortr�tsel enthalten sein sollen
	 * @return Alle W�rter mit entsprechenden Positionen (X,Y) und
	 *         Schreibrichtung. Es sollte gepr�ft werden, ob die Anzahl der
	 *         W�rter mit der erwartetet �bereinstimmt.
	 */
	public List<Wort> loeseOptimal(List<String> woerter) {
		List<Wort> lsg = loese(woerter);

		if (lsg == null){
			return null;
		}			
		
		int maximaleKompaktheit = Wort.getKompaktheitsma�(lsg);		
		ArrayList<Wort> eingetragen = new ArrayList<>();
		return loeseOptimal(eingetragen, woerter, maximaleKompaktheit);
	}

	/**
	 * Algorithmus zur Berechnung einer L�sung. Diese Methode wird rekursiv
	 * ausgef�hrt.
	 * 
	 * @param eingetragen
	 *            W�rter, die bereits im Kreuzwortr�tsel eingetragen sind
	 * @param uebrig
	 *            W�rter, die noch nicht im Kreuzwortr�tsel eingetragen sind
	 * @return Gibt den zuerst gefunden Weg zur�ck. Wenn die Anzahl der
	 *         zur�ckgegebenen Worte nicht der erwarteten Anzahl entspricht.
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
								int kompaktheit = Wort.getKompaktheitsma�(lsg);
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
	 * Algorithmus zur Berechnung einer optimierten L�sung. Diese Methode wird
	 * rekursiv ausgef�hrt.
	 * 
	 * @param eingetragen
	 *            W�rter, die bereits im Kreuzwortr�tsel eingetragen sind
	 * @param uebrig
	 *            W�rter, die noch nicht im Kreuzwortr�tsel eingetragen sind
	 * @return Gibt den besten gefunden Weg zur�ck. Wenn die Anzahl der
	 *         zur�ckgegebenen Worte nicht der erwarteten Anzahl entspricht.
	 */
	private List<Wort> loeseOptimal(List<Wort> eingetragen, List<String> uebrig, int maximaleKompaktheit) {
		if (uebrig.isEmpty()) {
			return eingetragen;
		}

		int aktuelleKompaktheit = Wort.getKompaktheitsma�(eingetragen);
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
