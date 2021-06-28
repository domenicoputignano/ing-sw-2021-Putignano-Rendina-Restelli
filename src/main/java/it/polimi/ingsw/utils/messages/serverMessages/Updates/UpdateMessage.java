package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.utils.messages.serverMessages.ServerMessage;

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
