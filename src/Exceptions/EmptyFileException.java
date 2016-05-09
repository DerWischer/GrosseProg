package Exceptions;

import java.io.IOException;

/**
 * Zeigt an, dass eine Datei keinen Inhalt hat
 * @author Joshua
 *
 */
public class EmptyFileException extends IOException {

	public EmptyFileException() {
		super();
	}

	public EmptyFileException(String message) {
		super(message);
	}

}
