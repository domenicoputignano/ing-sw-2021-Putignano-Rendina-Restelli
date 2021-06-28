package it.polimi.ingsw.Exceptions;

/**
 * Exception thrown when an error during the payment occurs. This can happen for different causes (for example if the player
 * has selected number of occurrences from a certain {@link it.polimi.ingsw.Utils.ResourceSource} they cannot be retrieved).
 */
public class PaymentErrorException extends Exception {
    public PaymentErrorException() {super();}
}
