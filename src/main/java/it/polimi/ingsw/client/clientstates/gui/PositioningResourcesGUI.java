package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractPositioningResources;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.PositioningMessage;

import java.util.List;

/**
 * This class represents the GUI-specific state reached when the client fails to position some resources
 * and the server sends a {@link it.polimi.ingsw.utils.messages.serverMessages.Updates.ServerAsksForPositioning}.
 */
public class PositioningResourcesGUI extends AbstractPositioningResources {

    /**
     * Initializes references to client and the list of resources to settle.
     */
    public PositioningResourcesGUI(Client client, List<ResourceType> resourcesToSettle) {
        super(client, resourcesToSettle);
    }

    /**
     * Main method of the class used to send the message to the server after the user has chosen how to place the
     * pending resources using the JavaFX GUI.
     */
    @Override
    public void manageUserInteraction() {
        client.sendMessage(messageToSend);
    }

    /**
     * Sets the message to send to the server.
     * @param messageToSend the message to send.
     */
    public void setMessageToSend(PositioningMessage messageToSend){
        this.messageToSend = messageToSend;
    }
}
