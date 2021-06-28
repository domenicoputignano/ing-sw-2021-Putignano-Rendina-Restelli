package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractInitialLeaderChoice;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.LeaderChoiceMessage;

public class InitialLeaderChoiceGUI extends AbstractInitialLeaderChoice {

    public InitialLeaderChoiceGUI(Client client, int firstChoice, int secondChoice) {
        super(client);
        messageToSend = new LeaderChoiceMessage(firstChoice, secondChoice);
    }

    @Override
    public void manageUserInteraction() {
        client.sendMessage(messageToSend);
    }
}
