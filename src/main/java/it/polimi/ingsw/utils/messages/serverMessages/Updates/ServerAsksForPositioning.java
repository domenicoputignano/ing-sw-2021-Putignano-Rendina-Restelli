package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.client.reducedmodel.ReducedMarketTray;
import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;
import java.util.List;

public class ServerAsksForPositioning extends UpdateMessage {
    private final ReducedMarketTray resultingMarketTray;
    private List<ResourceType> resourcesToSettle;

    public ServerAsksForPositioning(User user, ReducedPersonalBoard reducedPersonalBoard, ReducedMarketTray resultingMarketTray, List<ResourceType> pendingResources)
    {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.resultingMarketTray = resultingMarketTray;
        this.resourcesToSettle = pendingResources;
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

    public List<ResourceType> getResourcesToSettle() {
        return resourcesToSettle;
    }
}
