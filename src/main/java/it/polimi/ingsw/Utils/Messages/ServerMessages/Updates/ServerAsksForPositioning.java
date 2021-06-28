package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.CliStatesController;
import it.polimi.ingsw.Client.reducedmodel.ReducedMarketTray;
import it.polimi.ingsw.Client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Network.Client;
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
