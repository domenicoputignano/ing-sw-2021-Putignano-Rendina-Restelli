package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.client.reducedmodel.ReducedMarketTray;
import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;

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
        if(handler.getUI().isReceiverAction(user)) handler.getUI().setNormalActionDone(true);
        CliStatesController.updateCliState(this, handler.getUI());
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
