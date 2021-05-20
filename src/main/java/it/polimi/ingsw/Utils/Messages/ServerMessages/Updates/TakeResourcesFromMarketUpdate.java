package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.ClientStatesController;
import it.polimi.ingsw.Client.reducedmodel.ReducedMarketTray;
import it.polimi.ingsw.Client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Network.Client;

import java.util.ArrayList;
import java.util.List;

public class TakeResourcesFromMarketUpdate extends UpdateMessage {

    private ReducedMarketTray resultingMarketTray;
    private List<ResourceType> earnedResources = new ArrayList<>();
    private int faithPoints;

    public TakeResourcesFromMarketUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, ReducedMarketTray reducedMarketTray, List<ResourceType> resources
    , int faithPoints)
    {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.resultingMarketTray = reducedMarketTray;
        this.earnedResources = resources;
        this.faithPoints = faithPoints;
    }
    @Override
    public void handleMessage(Client client) {
        client.getGame().performUpdate(this);
        client.getUI().render(this);
        ClientStatesController.updateClientState(this, client.getUI());
    }

    public ReducedMarketTray getResultingMarketTray() {
        return resultingMarketTray;
    }

    public List<ResourceType> getEarnedResources() {
        return earnedResources;
    }

    public int getFaithPoints() {
        return faithPoints;
    }
}
