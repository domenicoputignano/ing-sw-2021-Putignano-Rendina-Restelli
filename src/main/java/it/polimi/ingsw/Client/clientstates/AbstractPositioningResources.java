package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Client.clientstates.cli.PositioningResourcesCLI;
import it.polimi.ingsw.Network.Client;

public abstract class AbstractPositioningResources extends AbstractClientState {
    public AbstractPositioningResources(Client client) {
        super(client);
    }


    @Override //TODO
    public AbstractClientState getGUIVersion() {
        return null;
    }

    @Override
    public PositioningResourcesCLI getCLIVersion() {
        return new PositioningResourcesCLI(client);
    }
}
