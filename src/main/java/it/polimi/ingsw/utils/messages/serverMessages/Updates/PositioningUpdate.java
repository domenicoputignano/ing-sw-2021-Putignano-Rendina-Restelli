package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;

import java.util.List;

/**
 * Class representing an update sent after a resources placement has been correctly performed.
 * A correct placement means that for each resource an available destination from {@link it.polimi.ingsw.utils.MarbleDestination}
 * has been selected.
 */
public class PositioningUpdate extends UpdateMessage{
    /**
     * List of resources discarded if not correctly settled.
     */
    private final List<ResourceType> discardedResources;

    public PositioningUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, List<ResourceType> discardedResources) {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.discardedResources = discardedResources;
    }

    /**
     * Method called by client in order to update its reduced model and show changes to player
     * @param handler {@link Client} instance that manages the update itself.
     */
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
