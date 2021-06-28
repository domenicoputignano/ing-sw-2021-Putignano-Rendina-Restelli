package it.polimi.ingsw.client.clientstates;


import it.polimi.ingsw.network.Client;

/**
 * This class represents the generic lobby state which is reached by the client
 * after having chosen the number of players in Multiplayer Mode.
 */
public abstract class AbstractLobby extends AbstractClientState {

    protected AbstractLobby(Client client) {
        super(client);
    }
}
