package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

public class NewTurnUpdate implements ServerMessage {

    private User currentUser;

    @Override
    public void handleMessage(Client handler) {
        handler.getGame().nextTurn();
    }
}
