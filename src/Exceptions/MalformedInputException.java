package Exceptions;

import java.io.IOException;

/**
 * Zeigt an, dass eine Eingabedatei nicht g�ltig ist
 * @author Joshua
 *
 */
public class MalformedInputException extends IOException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Erzeugt eine Exception
	 */
	public MalformedInputException(){
		super();
	}

	/**
	 * Erzeugt eine Exception
	 * @param message Fehlernachricht
	 */
	public MalformedInputException(String message){
		super(message);
	}

	
}
