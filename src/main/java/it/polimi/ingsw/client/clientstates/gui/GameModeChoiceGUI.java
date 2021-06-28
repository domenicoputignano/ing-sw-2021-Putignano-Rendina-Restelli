package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractGameModeChoice;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.GameModeChoiceMessage;

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