package it.polimi.ingsw.utils.messages.clientMessages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.RemoteView;
import it.polimi.ingsw.utils.MarbleDestination;
import it.polimi.ingsw.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class containing information about how to perform positioning of pending resources
 * related a {@link it.polimi.ingsw.model.TakeResourcesFromMarket}.
 */
public class PositioningMessage implements TurnControllerHandleable {
    /**
     * List containing pairs that link each a resource type with its destination in player warehouse.
     */
    private List<Pair<ResourceType, MarbleDestination>> whereToPutResources = new ArrayList<>();

    public List<Pair<ResourceType, MarbleDestination>> getWhereToPutResources() {
        return whereToPutResources;
    }

    public void setWhereToPutResources(List<Pair<ResourceType, MarbleDestination>> whereToPutResources) {
        this.whereToPutResources = whereToPutResources;
    }

    /**
     * Boolean method that checks if the message is valid.
     */
    public boolean isValidMessage()
    {
        if(whereToPutResources.stream().anyMatch(x -> x.getKey() == null || x.getValue() == null)) return false;
        return true;
    }


    public List<ResourceType> getResourcesToPut()
    {
        return whereToPutResources.stream().map(Pair::getKey).collect(Collectors.toList());
    }

    /**
     * Calls right method of turn controller in order to process the message itself and perform the action required by player.
     * @param turnController turn controller instance that process the message.
     * @param sender remote view that forwards the message.
     */
    public void handleMessage(TurnController turnController, RemoteView sender) {
        turnController.handlePositioningMessage(this,sender);
    }

    /**
     * This method is called in order to forward message itself to turn controller.
     * @param gameController game controller instance that will process the message.
     * @param sender remote view that forwards the message.
     */
    public void handleMessage(GameController gameController, RemoteView sender) {
        handleMessage(gameController.getTurnController(), sender);
    }

}
