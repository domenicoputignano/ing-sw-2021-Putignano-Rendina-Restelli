package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.utils.messages.serverMessages.ServerMessage;

/**
 * Abstract class representing a message sent from server to client in order to notify it that
 * there has been a game state change.
 */
public abstract class UpdateMessage implements ServerMessage {

    /**
     * Owner of the personal board.
     */
    protected User user;
    /**
     * Instance of an updated personal board.
     */
    protected ReducedPersonalBoard userPersonalBoard;

    public User getUser() {
        return user;
    }

    public ReducedPersonalBoard getUserPersonalBoard() {
        return userPersonalBoard;
    }


}
