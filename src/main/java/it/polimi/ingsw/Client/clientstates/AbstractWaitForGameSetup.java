package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Network.Client;

public abstract class AbstractWaitForGameSetup extends AbstractClientState {

    protected AbstractWaitForGameSetup(Client client) {
        super(client);
    }

}
