package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.PositioningMessage;

import java.util.List;

/**
 * This class represents the generic ui state reached when the client fails to position some resources
 * and the server sends a {@link it.polimi.ingsw.utils.messages.serverMessages.Updates.ServerAsksForPositioning}.
 */
public abstract class AbstractPositioningResources extends AbstractClientState {

    /**
     * The message to send to the server containing how to position the resources.
     */
    protected PositioningMessage messageToSend = new PositioningMessage();
    /**
     * The list of the resources to settle.
     */
    protected List<ResourceType> resourcesToSettle;

    public AbstractPositioningResources(Client client, List<ResourceType> resourcesToSettle) {
        super(client);
        this.resourcesToSettle = resourcesToSettle;
    }

}
