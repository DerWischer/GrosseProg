package Exceptions;

import java.io.IOException;

/**
 * Zeigt an, dass eine Eingabedatei nicht gültig ist
 * @author Joshua
 *
 */
public class MalformedInputException extends IOException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MalformedInputException(){
		super();
	}

	public MalformedInputException(String message){
		super(message);
	}

	
}
