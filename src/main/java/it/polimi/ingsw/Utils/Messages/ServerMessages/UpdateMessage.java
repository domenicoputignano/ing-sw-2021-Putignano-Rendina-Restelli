package it.polimi.ingsw.Utils.Messages.ServerMessages;

import it.polimi.ingsw.Network.Client;

public interface UpdateMessage extends ServerMessage {

    public void handleUpdateMessage(Client client);

}
