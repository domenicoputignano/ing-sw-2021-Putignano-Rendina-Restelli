package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractNumOfPlayersChoice;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.NumOfPlayerChoiceMessage;

public class NumOfPlayerChoiceGUI extends AbstractNumOfPlayersChoice {
    @Override
    public void manageUserInteraction() {
        client.sendMessage(messageToSend);
    }

    public NumOfPlayerChoiceGUI(Client client, int numOfPlayers) {
        super(client);
        messageToSend = new NumOfPlayerChoiceMessage(numOfPlayers);
    }
}
