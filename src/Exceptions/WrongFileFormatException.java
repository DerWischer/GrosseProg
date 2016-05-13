package Exceptions;

import java.io.IOException;

/**
 * Zeigt an, dass ein falsches Dateiformat vorliegt
 * 
 * @author Joshua
 *
 */
public class WrongFileFormatException extends IOException {

	private static final long serialVersionUID = 1L;

	/**
	 * Erzeugt eine Exception
	 */
	public WrongFileFormatException() {
		super();
	}

	/**
	 * Erzeugt eine Exception
	 * @param message Fehlernachricht
	 */
	public WrongFileFormatException(String message) {
		super(message);
	}

}
