package it.polimi.ingsw.exceptions;

/**
 * This exception can be thrown while processing a message that requires resources analysis. In particular way, it detects
 * a mismatch between overall resources selected player side to perform an action and those required calculated by model.
 */
public class ResourceMismatchException extends Exception{
    public ResourceMismatchException() {super();}
}
