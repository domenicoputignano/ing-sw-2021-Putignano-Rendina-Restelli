package it.polimi.ingsw.Client.clientstates.gui;

import it.polimi.ingsw.Client.clientstates.AbstractNumOfPlayersChoice;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.NumOfPlayerChoiceMessage;

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
