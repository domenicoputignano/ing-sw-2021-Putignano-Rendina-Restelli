package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Model.ResourceType;
import it.polimi.ingsw.Network.Client;

import java.util.List;

public class PositioningUpdate extends UpdateMessage{
    private final List<ResourceType> discardedResources;

    public PositioningUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, List<ResourceType> discardedResources) {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.discardedResources = discardedResources;
    }


    @Override
    public void handleMessage(Client client) {

    }
}
