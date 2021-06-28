package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when someone fails while searching for a depot associated to a particular resource type.
 **/
public class DepotNotFoundException extends Exception {

    public DepotNotFoundException() { super("Depot not found!"); }
}
