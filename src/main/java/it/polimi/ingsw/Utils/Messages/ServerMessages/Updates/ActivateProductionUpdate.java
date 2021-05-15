package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Network.Client;
import java.util.Map;

public class ActivateProductionUpdate extends UpdateMessage {
    private Map<ResourceType, Integer> payedResources;
    private Map<ResourceType, Integer> receivedResources;

    public ActivateProductionUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, Map<ResourceType, Integer> payed, Map<ResourceType,Integer> received) {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.payedResources = payed;
        this.receivedResources = received;
    }

    @Override
    public void handleMessage(Client client) {
        client.getGame().performUpdate(this);
    }

    public Map<ResourceType, Integer> getPayedResources() {
        return payedResources;
    }

    public Map<ResourceType, Integer> getReceivedResources() {
        return receivedResources;
    }
}