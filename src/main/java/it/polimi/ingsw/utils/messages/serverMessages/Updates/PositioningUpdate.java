package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;

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
