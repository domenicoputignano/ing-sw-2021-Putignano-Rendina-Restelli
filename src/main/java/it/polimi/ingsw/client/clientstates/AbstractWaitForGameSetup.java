package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.network.Client;

public abstract class AbstractWaitForGameSetup extends AbstractClientState {

    protected AbstractWaitForGameSetup(Client client) {
        super(client);
    }

}
