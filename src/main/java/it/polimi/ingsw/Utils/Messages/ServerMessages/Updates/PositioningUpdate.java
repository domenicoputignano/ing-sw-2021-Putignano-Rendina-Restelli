package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.CliStatesController;
import it.polimi.ingsw.Client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Commons.ResourceType;
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
    public void handleMessage(Client handler) {
        if(handler.getUI().isReceiverAction(user)) handler.getUI().setNormalActionDone(true);
        handler.getGame().updatePersonalBoard(this);
        handler.getUI().render(this);
        CliStatesController.updateCliState(this, handler.getUI());
    }

    public List<ResourceType> getDiscardedResources() {
        return discardedResources;
    }
}
