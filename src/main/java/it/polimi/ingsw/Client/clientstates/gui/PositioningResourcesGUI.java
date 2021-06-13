package it.polimi.ingsw.Client.clientstates.gui;

import it.polimi.ingsw.Client.clientstates.AbstractPositioningResources;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.PositioningMessage;

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
