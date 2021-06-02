package it.polimi.ingsw.Client.clientstates.gui;

import it.polimi.ingsw.Client.clientstates.AbstractInitialResourceChoice;
import it.polimi.ingsw.Commons.ResourceType;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.ResourceChoiceMessage;
import it.polimi.ingsw.Utils.Pair;

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
