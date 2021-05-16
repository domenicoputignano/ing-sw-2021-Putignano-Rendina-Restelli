package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

public abstract class AbstractClientState {

    protected Client client;

    protected AbstractClientState(Client client) {
        this.client = client;
    }
    public abstract AbstractClientState getGUIVersion();
    public abstract AbstractClientState getCLIVersion();
    public abstract void render(ServerMessage message);
    public abstract void manageUserInteraction();

}
