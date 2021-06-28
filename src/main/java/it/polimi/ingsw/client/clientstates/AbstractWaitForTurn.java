package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.network.Client;

/**
 * This class represents the generic wait for turn state that is reached by all the clients
 * who aren't in turn.
 */
public abstract class AbstractWaitForTurn extends AbstractClientState {

    protected AbstractWaitForTurn(Client client) { super(client); }

}
