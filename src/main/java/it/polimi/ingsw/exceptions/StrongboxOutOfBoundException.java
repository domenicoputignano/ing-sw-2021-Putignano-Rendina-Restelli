package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when number of occurrences related to a certain resource type becomes negative.
 */
public class StrongboxOutOfBoundException extends Exception {
    public StrongboxOutOfBoundException() {
        super("Strongbox out of bounds!");
    };
}
