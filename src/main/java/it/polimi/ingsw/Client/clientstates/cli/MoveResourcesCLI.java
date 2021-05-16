package it.polimi.ingsw.Client.clientstates.cli;

import it.polimi.ingsw.Client.clientstates.AbstractMoveResources;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ServerMessages.ServerMessage;

public class MoveResourcesCLI extends AbstractMoveResources {

    public MoveResourcesCLI(Client client) {
        super(client);
    }

    @Override
    public void render(ServerMessage message) {

    }

    @Override
    public void manageUserInteraction() {

    }
}
