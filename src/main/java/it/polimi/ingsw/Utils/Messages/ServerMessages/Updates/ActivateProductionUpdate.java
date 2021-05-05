package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Model.ResourceType;
import it.polimi.ingsw.Network.Client;

import java.util.EnumMap;
import java.util.Map;

public class ActivateProductionUpdate extends UpdateMessage {
    private Map<ResourceType, Integer> payedResources = new EnumMap<ResourceType, Integer>(ResourceType.class);
    private Map<ResourceType, Integer> receivedResources = new EnumMap<ResourceType, Integer>(ResourceType.class);

    @Override
    public void handleUpdateMessage(Client client) {

    }
}
