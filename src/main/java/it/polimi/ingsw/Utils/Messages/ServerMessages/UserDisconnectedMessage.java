package it.polimi.ingsw.Utils.Messages.ServerMessages;


import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;

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
