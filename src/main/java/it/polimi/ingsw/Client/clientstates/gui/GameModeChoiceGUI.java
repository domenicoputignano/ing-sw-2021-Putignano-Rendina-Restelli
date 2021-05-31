package it.polimi.ingsw.Client.clientstates.gui;

import it.polimi.ingsw.Client.clientstates.AbstractGameModeChoice;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Utils.Messages.ClientMessages.GameModeChoiceMessage;

public class GameModeChoiceGUI extends AbstractGameModeChoice {
    @Override
    public void manageUserInteraction() {
        client.sendMessage(messageToSend);
    }

    public GameModeChoiceGUI(Client client, String choice) {
        super(client);
        messageToSend = new GameModeChoiceMessage(choice);
    }
}