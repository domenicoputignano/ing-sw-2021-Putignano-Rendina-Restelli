package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.PositioningMessage;

import java.util.List;

public abstract class AbstractPositioningResources extends AbstractClientState {

    protected PositioningMessage messageToSend = new PositioningMessage();
    protected List<ResourceType> resourcesToSettle;

    public AbstractPositioningResources(Client client, List<ResourceType> resourcesToSettle) {
        super(client);
        this.resourcesToSettle = resourcesToSettle;
    }

}
