package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when number of occurrences in a depot is greater than its size or less than zero.
 */
public class DepotOutOfBoundsException extends Exception
{
    public DepotOutOfBoundsException()
    {
        super("Depot out of Bounds");
    }
}
