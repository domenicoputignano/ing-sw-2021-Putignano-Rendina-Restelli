package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Client.ReducedPersonalBoard;
import it.polimi.ingsw.Commons.User;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Network.Client;


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
