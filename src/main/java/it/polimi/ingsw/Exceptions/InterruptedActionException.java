package it.polimi.ingsw.Exceptions;

public class InterruptedActionException extends Exception {

    public InterruptedActionException() {
        super("Normal action stopped because not executable by this player");
    }

}
