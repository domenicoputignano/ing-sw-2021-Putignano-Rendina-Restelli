package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

public abstract class UpdateMessage implements ServerMessage {

    protected User user;
    protected ReducedPersonalBoard userPersonalBoard;

    public User getUser() {
        return user;
    }

    public ReducedPersonalBoard getUserPersonalBoard() {
        return userPersonalBoard;
    }


}
