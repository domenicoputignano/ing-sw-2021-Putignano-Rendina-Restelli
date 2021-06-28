package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when model detects that player hasn't enough resources to perform an action that requires them.
 */
public class NotEnoughResourcesException extends Exception{
    public NotEnoughResourcesException() {super();}
}
