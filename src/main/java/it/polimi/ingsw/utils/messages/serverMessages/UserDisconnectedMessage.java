package it.polimi.ingsw.utils.messages.serverMessages;


import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.network.Client;

public class UserDisconnectedMessage implements ServerMessage {

    private final User disconnectedUser;


    public UserDisconnectedMessage(User disconnectedUser) {
        this.disconnectedUser = disconnectedUser;
    }

    @Override
    public void handleMessage(Client handler) {
        handler.getUI().render(this);
    }

    public User getDisconnectedUser() {
        return disconnectedUser;
    }
}
