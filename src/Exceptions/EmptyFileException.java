package Exceptions;

import java.io.IOException;

/**
 * Zeigt an, dass eine Datei keinen Inhalt hat
 * @author Joshua
 *
 */
public class EmptyFileException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Erzeugt eine Exception
	 */
	public EmptyFileException() {
		super();
	}

	/**
	 * Erzeugt eine Exception
	 * @param message Fehlernachricht
	 */
	public EmptyFileException(String message) {
		super(message);
	}

}
