package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Network.Client;

public abstract class AbstractWaitForGameSetup extends AbstractClientState {
    public AbstractWaitForGameSetup(Client client) {
        super(client);
    }
}
