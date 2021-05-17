package it.polimi.ingsw.Client.clientstates;

import it.polimi.ingsw.Client.clientstates.cli.PositioningResourcesCLI;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.PositioningMessage;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPositioningResources extends AbstractClientState {

    protected PositioningMessage messageToSend = new PositioningMessage();
    protected List<ResourceType> resourcesToSettle;

    public AbstractPositioningResources(Client client, List<ResourceType> resourcesToSettle) {
        super(client);
        this.resourcesToSettle = resourcesToSettle;
    }


    @Override //TODO
    public AbstractClientState getGUIVersion() {
        return null;
    }

    @Override
    public PositioningResourcesCLI getCLIVersion() {
        return null;
    }
}
