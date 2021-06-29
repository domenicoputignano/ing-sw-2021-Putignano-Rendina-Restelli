package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;


import java.util.List;

/**
 * Class representing an update sent when player correctly performs initial resources choice.
 */
public class InitialResourceChoiceUpdate extends UpdateMessage {
    /**
     * List of resources chosen and placed in his depots.
     */
    private final List<ResourceType> chosenResources;

    public InitialResourceChoiceUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, List<ResourceType> chosenResources) {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.chosenResources = chosenResources;
    }

    public List<ResourceType> getChosenResources() {
        return chosenResources;
    }


    /**
     * Method called by client in order to update its reduced model and show changes to player
     * @param client {@link Client} instance that manages the update itself.
     */
    @Override
    public void handleMessage(Client client) {
        client.getGame().updatePersonalBoard(this);
        client.getUI().render(this);
    }

}
