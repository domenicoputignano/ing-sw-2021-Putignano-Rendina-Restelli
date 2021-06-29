package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.CliStatesController;
import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;
import java.util.Map;

/**
 * Class containing specific information about an activate production action successfully ended.
 */
public class ActivateProductionUpdate extends UpdateMessage {
    /**
     * Map containing for each resource, how many occurrences has been payed to activate production powers.
     */
    private final Map<ResourceType, Integer> payedResources;
    /**
     * Map containing for each resource, how many occurrences has been gained from production powers.
     */
    private final Map<ResourceType, Integer> receivedResources;
    private final int faithPoints;

    public ActivateProductionUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, Map<ResourceType, Integer> payed, Map<ResourceType,Integer> received, int faithPoints) {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.payedResources = payed;
        this.receivedResources = received;
        this.faithPoints = faithPoints;
    }

    /**
     * Method called by client in order to update its reduced model and show changes to player
     * @param client {@link Client} instance that manages the update itself.
     */
    @Override
    public void handleMessage(Client client) {
        client.getGame().performUpdate(this);
        client.getUI().render(this);
        if(client.getUI().isReceiverAction(user)) client.getUI().setNormalActionDone(true);
        CliStatesController.updateCliState(this, client.getUI());

    }

    public Map<ResourceType, Integer> getPayedResources() {
        return payedResources;
    }

    public Map<ResourceType, Integer> getReceivedResources() {
        return receivedResources;
    }

    public int getFaithPoints() {
        return faithPoints;
    }
}
