package it.polimi.ingsw.utils.messages.serverMessages.Errors;


import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.utils.messages.serverMessages.ServerMessage;

/**
 * This abstract class represents a message concerned to an error found during game play.
 * It has a reference to user that triggers the error.
 */
public abstract class ErrorMessage implements ServerMessage {
    protected User triggeringUser;


    protected ErrorMessage(User triggeringUser) {
        this.triggeringUser = triggeringUser;
    }

    public User getTriggeringUser() {
        return triggeringUser;
    }
}
