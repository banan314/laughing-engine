package pl.edu.prz.klopusz.application.exceptions;

/**
 * Created by kamil on 15.06.17.
 */
public class InvalidPlayerException extends Exception {
    public InvalidPlayerException() {}

    public InvalidPlayerException(String name) {
        super(name);
    }
}
