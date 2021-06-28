package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.network.Client;

public abstract class AbstractClientState {

    protected Client client;

    protected AbstractClientState(Client client) {
        this.client = client;
    }
    public abstract void manageUserInteraction();

}
