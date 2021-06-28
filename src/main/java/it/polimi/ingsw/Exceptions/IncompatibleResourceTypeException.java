package it.polimi.ingsw.Exceptions;

/**
 * This exception can thrown when the player is attempting to move resources in a way that breaks
 * {@link it.polimi.ingsw.Model.Warehouse} rules.
 */
public class IncompatibleResourceTypeException extends Exception{
    public IncompatibleResourceTypeException() { super("Wrong Resource Type!"); }
}
