package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractPositioningResources;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.PositioningMessage;

import java.util.List;

public class PositioningResourcesGUI extends AbstractPositioningResources {
    public PositioningResourcesGUI(Client client, List<ResourceType> resourcesToSettle) {
        super(client, resourcesToSettle);
    }

    @Override
    public void manageUserInteraction() {
        client.sendMessage(messageToSend);
    }

    public void setMessageToSend(PositioningMessage messageToSend){
        this.messageToSend = messageToSend;
    }
}
