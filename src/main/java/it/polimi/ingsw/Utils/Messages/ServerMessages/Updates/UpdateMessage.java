package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

public abstract class UpdateMessage implements ServerMessage {

    private User user;
    private ReducedPersonalBoard userPersonalBoard;

    public abstract void handleUpdateMessage(Client client);

}
