package it.polimi.ingsw.utils.messages.serverMessages.Errors;


import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.utils.messages.serverMessages.ServerMessage;

public abstract class ErrorMessage implements ServerMessage {
    protected User triggeringUser;


    protected ErrorMessage(User triggeringUser) {
        this.triggeringUser = triggeringUser;
    }

    public User getTriggeringUser() {
        return triggeringUser;
    }
}
