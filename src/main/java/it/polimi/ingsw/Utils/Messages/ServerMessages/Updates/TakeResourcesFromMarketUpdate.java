package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.ClientStatesController;
import it.polimi.ingsw.Client.reducedmodel.ReducedMarketTray;
import it.polimi.ingsw.Client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Network.Client;

import java.util.List;

public class TakeResourcesFromMarketUpdate extends UpdateMessage {

    private final ReducedMarketTray resultingMarketTray;
    private final List<ResourceType> earnedResources;
    private final int faithPoints;

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
    public void handleMessage(Client handler) {
        handler.getGame().performUpdate(this);
        handler.getUI().render(this);
        handler.getUI().setNormalActionDone(true);
        ClientStatesController.updateClientState(this, handler.getUI());
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
