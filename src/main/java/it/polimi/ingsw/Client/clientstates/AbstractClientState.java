package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Network.Client;

public abstract class AbstractClientState {

    protected Client client;

    protected AbstractClientState(Client client) {
        this.client = client;
    }
    public abstract void manageUserInteraction();

}
