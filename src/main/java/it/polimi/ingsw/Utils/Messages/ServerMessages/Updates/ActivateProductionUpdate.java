package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Model.ResourceType;
import it.polimi.ingsw.Network.Client;

import java.util.EnumMap;
import java.util.Map;

public class ActivateProductionUpdate extends UpdateMessage {
    private Map<ResourceType, Integer> payedResources = new EnumMap<ResourceType, Integer>(ResourceType.class);
    private Map<ResourceType, Integer> receivedResources = new EnumMap<ResourceType, Integer>(ResourceType.class);

    public ActivateProductionUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, Map<ResourceType, Integer> payed, Map<ResourceType,Integer> received)
    {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.payedResources = payed;
        this.receivedResources = received;
    }

    @Override
    public void handleUpdateMessage(Client client) {

    }
}
