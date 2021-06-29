package it.polimi.ingsw.utils.messages.serverMessages;


import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

/**
 * Class representing a message sent by server in order to notify players currently involved in a game
 * that one of them has just disconnected.
 */
public class UserDisconnectedMessage implements ServerMessage {

    /**
     * User of the player disconnected.
     */
    private final User disconnectedUser;


    public UserDisconnectedMessage(User disconnectedUser) {
        this.disconnectedUser = disconnectedUser;
    }

    /**
     * Method called by client in order to show message itself.
     * @param handler {@link Client} instance that manages the update itself.
     */
    @Override
    public void handleMessage(Client handler) {
        handler.getUI().render(this);
    }

    public User getDisconnectedUser() {
        return disconnectedUser;
    }
}
