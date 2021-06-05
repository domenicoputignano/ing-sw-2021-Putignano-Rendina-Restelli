package it.polimi.ingsw.Exceptions;

public class BackToMenuException extends Exception {
    public BackToMenuException() {
        super("Player has chosen to come back to menu ");
    }
}
