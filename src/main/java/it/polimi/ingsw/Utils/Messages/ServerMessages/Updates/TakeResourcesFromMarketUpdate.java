package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.ReducedMarble;
import it.polimi.ingsw.Client.ReducedMarketTray;
import it.polimi.ingsw.Client.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Model.ResourceType;
import it.polimi.ingsw.Network.Client;

import java.util.ArrayList;
import java.util.List;

public class TakeResourcesFromMarketUpdate extends UpdateMessage {

    private ReducedMarketTray resultingMarketTray;
    private List<ResourceType> earnedResources = new ArrayList<>();

    public TakeResourcesFromMarketUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, ReducedMarketTray reducedMarketTray, List<ResourceType> resources)
    {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.resultingMarketTray = reducedMarketTray;
        this.earnedResources = resources;
    }
    @Override
    public void handleUpdateMessage(Client client) {

    }
}
