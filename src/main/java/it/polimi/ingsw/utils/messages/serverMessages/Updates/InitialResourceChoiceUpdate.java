package it.polimi.ingsw.utils.messages.serverMessages.Updates;

import it.polimi.ingsw.client.reducedmodel.ReducedPersonalBoard;
import it.polimi.ingsw.commons.User;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;


import java.util.List;

public class InitialResourceChoiceUpdate extends UpdateMessage {
    private final List<ResourceType> chosenResources;

    public InitialResourceChoiceUpdate(User user, ReducedPersonalBoard reducedPersonalBoard, List<ResourceType> chosenResources) {
        this.user = user;
        this.userPersonalBoard = reducedPersonalBoard;
        this.chosenResources = chosenResources;
    }

    public List<ResourceType> getChosenResources() {
        return chosenResources;
    }

    @Override
    public void handleMessage(Client client) {
        client.getGame().updatePersonalBoard(this);
        client.getUI().render(this);
    }

}
