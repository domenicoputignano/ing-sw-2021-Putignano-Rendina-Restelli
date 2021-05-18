package it.polimi.ingsw.Utils.Messages.ServerMessages.Errors;


import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

public abstract class ErrorMessage implements ServerMessage {
    protected User triggeringUser;


    protected ErrorMessage(User triggeringUser) {
        this.triggeringUser = triggeringUser;
    }


}
