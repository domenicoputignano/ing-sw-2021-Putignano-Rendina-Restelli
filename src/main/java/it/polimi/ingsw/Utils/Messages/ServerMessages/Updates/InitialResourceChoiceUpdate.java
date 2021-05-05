package it.polimi.ingsw.Utils.Messages.ServerMessages.Updates;

import it.polimi.ingsw.Model.ResourceType;
import it.polimi.ingsw.Network.Client;

import java.util.ArrayList;
import java.util.List;

public class InitialResourceChoiceUpdate extends UpdateMessage {
    private List<ResourceType> chosenResources = new ArrayList<>();


    @Override
    public void handleUpdateMessage(Client client) {

    }
}
