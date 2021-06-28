package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractInitialResourceChoice;
import it.polimi.ingsw.commons.ResourceType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.ResourceChoiceMessage;
import it.polimi.ingsw.utils.Pair;

import java.util.List;

public class InitialResourceChoiceGUI extends AbstractInitialResourceChoice {

    private List<Pair<ResourceType, Integer>> chosenResources;

    @Override
    public void manageUserInteraction() {
        messageToSend = new ResourceChoiceMessage(chosenResources);
        client.sendMessage(messageToSend);
    }

    public InitialResourceChoiceGUI(Client client, List<Pair<ResourceType, Integer>> chosenResources) {
        super(client);
        this.chosenResources = chosenResources;
    }

}
