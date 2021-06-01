package it.polimi.ingsw.Client.clientstates.gui;

import it.polimi.ingsw.Client.clientstates.AbstractInitialLeaderChoice;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.LeaderChoiceMessage;

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
