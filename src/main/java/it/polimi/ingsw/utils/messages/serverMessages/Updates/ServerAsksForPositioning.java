package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.client.reducedmodel.ReducedMarketTray;
import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;
import java.util.List;

/**
 * Class representing one of possible update resulting from a {@link it.polimi.ingsw.model.TakeResourcesFromMarket}.
 * This is sent when a player hasn't settle his resources according to game rule, so model requires further action from him.
 */
public class ServerAsksForPositioning extends UpdateMessage {
    /**
     * Updated instance of market tray
     */
    private final ReducedMarketTray resultingMarketTray;
    /**
     * Resources to be positioned.
     */
    private List<ResourceType> resourcesToSettle;

    public ServerAsksForPositioning(User user, ReducedPersonalBoard reducedPersonalBoard, ReducedMarketTray resultingMarketTray, List<ResourceType> pendingResources)
    {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.resultingMarketTray = resultingMarketTray;
        this.resourcesToSettle = pendingResources;
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

    public List<ResourceType> getResourcesToSettle() {
        return resourcesToSettle;
    }
}
