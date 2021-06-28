package it.polimi.ingsw.Exceptions;

/**
 * This exception suggests that there is no resource type linked to a certain {@link it.polimi.ingsw.Model.MarketTray.Marble}.
 */
public class NoSuchResourceTypeException extends Exception {
    public NoSuchResourceTypeException() {
        super("No Such ResourceType!");
    }
}
