package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.client.reducedmodel.ReducedMarketTray;
import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;

import java.util.List;

/**
 * Class representing second possible update resulting from a {@link it.polimi.ingsw.model.TakeResourcesFromMarket} action.
 * This is sent when everything
 */
public class TakeResourcesFromMarketUpdate extends UpdateMessage {

    /**
     * Update instance of market tray
     */
    private final ReducedMarketTray resultingMarketTray;
    /**
     * Resources gained from action performing.
     */
    private final List<ResourceType> earnedResources;
    /**
     * Number of faith points gained.
     */
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

    /**
     * Method called by client in order to update its reduced model and show changes to player
     * @param handler {@link Client} instance that manages the update itself.
     */
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
