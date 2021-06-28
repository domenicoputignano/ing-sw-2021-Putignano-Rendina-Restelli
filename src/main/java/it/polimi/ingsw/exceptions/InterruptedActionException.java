package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when a player is attempting to perform an action that he couldn't afford with resources currently in
 * his {@link it.polimi.ingsw.model.Warehouse}
 */
public class InterruptedActionException extends Exception {

    public InterruptedActionException() {
        super("Normal action stopped because not executable by this player");
    }

}
