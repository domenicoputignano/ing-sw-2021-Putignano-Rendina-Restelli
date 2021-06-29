package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractGameModeChoice;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.GameModeChoiceMessage;

/**
 * This class represents the GUI-specific game mode choice state which is reached by the client
 * after the username choice.
 */
public class GameModeChoiceGUI extends AbstractGameModeChoice {

    /**
     * Initializes reference to client and the message to send.
     */
    public GameModeChoiceGUI(Client client, String choice) {
        super(client);
        messageToSend = new GameModeChoiceMessage(choice);
    }

    /**
     * Main method of the class used to send the message to the server after the user has chosen the game mode
     * using the JavaFX GUI.
     */
    @Override
    public void manageUserInteraction() {
        client.sendMessage(messageToSend);
    }
}