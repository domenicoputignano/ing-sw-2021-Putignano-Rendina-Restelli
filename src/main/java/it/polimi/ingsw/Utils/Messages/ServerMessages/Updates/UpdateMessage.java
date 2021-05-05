package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

public interface UpdateMessage extends ServerMessage {

    public void handleUpdateMessage(Client client);

}
