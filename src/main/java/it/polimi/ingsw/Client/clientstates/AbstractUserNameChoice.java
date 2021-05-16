package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Network.Client;

public abstract class AbstractUserNameChoice extends AbstractClientState {

    protected AbstractUserNameChoice(Client client) {
        super(client);
    }

    @Override //TODO
    public AbstractClientState getGUIVersion() {
        return null;
    }

    @Override
    public AbstractClientState getCLIVersion() {
        return null;
    }
}
