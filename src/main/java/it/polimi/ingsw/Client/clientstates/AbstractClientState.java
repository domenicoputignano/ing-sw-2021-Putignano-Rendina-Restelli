package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

public abstract class AbstractClientState {

    protected Client client;

    public abstract void render(ServerMessage message);
    public abstract void manageUserInteraction();

}
