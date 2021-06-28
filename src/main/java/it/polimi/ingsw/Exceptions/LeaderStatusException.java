package it.polimi.ingsw.Exceptions;

/**
 * Exception thrown when a player is attempting to perform an action over a leader card that has been
 * already activated.
 */
public class LeaderStatusException extends Exception{
    public LeaderStatusException() {super();}
}
