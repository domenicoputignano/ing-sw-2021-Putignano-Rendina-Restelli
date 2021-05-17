package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Client.clientstates.cli.MoveResourcesCLI;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.MoveResourcesMessage;

public abstract class AbstractMoveResources extends AbstractClientState {

    protected MoveResourcesMessage messageToSend = new MoveResourcesMessage();

    public AbstractMoveResources(Client client) {
        super(client);
    }


    @Override //TODO
    public AbstractClientState getGUIVersion() {
        return null;
    }

    @Override
    public MoveResourcesCLI getCLIVersion() {
        return new MoveResourcesCLI(client);
    }
}
