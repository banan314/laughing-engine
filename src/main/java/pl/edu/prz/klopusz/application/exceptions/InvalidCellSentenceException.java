package pl.edu.prz.klopusz.application.exceptions;

/**
 * Created by kamil on 28.06.17.
 */
public class InvalidCellSentenceException extends Exception {
    public InvalidCellSentenceException(String message) {
        super(message);
    }

    public InvalidCellSentenceException() {
    }
}
