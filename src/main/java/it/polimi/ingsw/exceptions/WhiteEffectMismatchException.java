package it.polimi.ingsw.exceptions;

/**
 * Exception thrown when a player has two {@link it.polimi.ingsw.commons.Effect} of convert marble type active but he hasn't set
 * any of them for at least one white marble taken from market.
 */
public class WhiteEffectMismatchException extends Exception {
    public WhiteEffectMismatchException() { super(); }
}
