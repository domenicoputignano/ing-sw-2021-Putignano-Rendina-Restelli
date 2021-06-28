package it.polimi.ingsw.exceptions;

/**
 * This exception can thrown when the player is attempting to move resources in a way that breaks
 * {@link it.polimi.ingsw.model.Warehouse} rules.
 */
public class IncompatibleResourceTypeException extends Exception{
    public IncompatibleResourceTypeException() { super("Wrong Resource Type!"); }
}
