package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractPositioningResources;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

public class PositioningResourcesCLI extends AbstractPositioningResources {
    public PositioningResourcesCLI(Client client) {
        super(client);
    }

    @Override
    public void render(ServerMessage message) {

    }

    @Override
    public void manageUserInteraction() {

    }
}
