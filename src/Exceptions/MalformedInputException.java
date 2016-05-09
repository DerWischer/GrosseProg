package Exceptions;

import java.io.IOException;

/**
 * Zeigt an, dass eine Eingabedatei nicht g�ltig ist
 * @author Joshua
 *
 */
public class MalformedInputException extends IOException {
	
	public MalformedInputException(){
		super();
	}

	public MalformedInputException(String message){
		super(message);
	}

	
}
