package it.polimi.ingsw.exceptions;

/**
 * This exception suggests that there is no resource type linked to a certain {@link it.polimi.ingsw.model.marketTray.Marble}.
 */
public class NoSuchResourceTypeException extends Exception {
    public NoSuchResourceTypeException() {
        super("No Such ResourceType!");
    }
}
