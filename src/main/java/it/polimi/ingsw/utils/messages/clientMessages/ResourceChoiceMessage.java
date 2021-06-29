package it.polimi.ingsw.utils.messages.clientMessages;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.RemoteView;
import it.polimi.ingsw.utils.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing information in order to perform initial resources choice.
 */
public class ResourceChoiceMessage implements GameControllerHandleable {

    /**
     * List of {@link Pair} that link a resource type with index of normal depot that will handle it.
     */
    private final List<Pair<ResourceType,Integer>> chosenResources = new ArrayList<>();

    public ResourceChoiceMessage(List<Pair<ResourceType,Integer>> chosenResources) {
        this.chosenResources.addAll(chosenResources);
    }

    /**
     * Calls right {@link GameController} method assigned to this message handling.
     * @param gameController controller that will handle the message.
     * @param sender remote view that has forwarded the message.
     */
    public void handleMessage(GameController gameController, RemoteView sender) {
        gameController.handleResourceChoiceMessage(this, sender);
    }


    /**
     * Method that make consistency check over message itself.
     * @return true if the message is valid.
     */
    public boolean isValidMessage() {
        if(chosenResources.size() > 2 || chosenResources.size() == 0) return false;
        if(chosenResources.stream().anyMatch(x -> x.getValue() < 1 || x.getValue() > 3 || x.getKey() == null)) return false;
        return true;
    }

    public List<Pair<ResourceType, Integer>> getChosenResources() {
        return chosenResources;
    }


}
