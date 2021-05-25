package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Network.Client;
import java.util.Map;

public class ActivateProductionUpdate extends UpdateMessage {
    private final Map<ResourceType, Integer> payedResources;
    private final Map<ResourceType, Integer> receivedResources;
    private final int faithPoints;

    public ActivateProductionUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, Map<ResourceType, Integer> payed, Map<ResourceType,Integer> received, int faithPoints) {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.payedResources = payed;
        this.receivedResources = received;
        this.faithPoints = faithPoints;
    }

    @Override
    public void handleMessage(Client client) {
        client.getGame().performUpdate(this);
        if(client.getUI().isReceiverAction(user)) client.getUI().setNormalActionDone(true);
    }

    public Map<ResourceType, Integer> getPayedResources() {
        return payedResources;
    }

    public Map<ResourceType, Integer> getReceivedResources() {
        return receivedResources;
    }

    public int getFaithPoints() {
        return faithPoints;
    }
}
