package it.polimi.ingsw.exceptions;

/**
 * This exception is used in command line interface to find out if the player wants or needs to come back to action choice.
 */
public class BackToMenuException extends Exception {
    public BackToMenuException() {
        super("Player has chosen to come back to menu ");
    }
}
