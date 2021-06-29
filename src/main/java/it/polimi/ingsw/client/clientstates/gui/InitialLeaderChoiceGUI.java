package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractInitialLeaderChoice;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.LeaderChoiceMessage;

/**
 * This class represents the GUI-specific state that is reached when the client has to
 * choose which leader cards to discard during the initial choices phase of the game.
 */
public class InitialLeaderChoiceGUI extends AbstractInitialLeaderChoice {

    /**
     * Initializes references to client and the message to send.
     */
    public InitialLeaderChoiceGUI(Client client, int firstChoice, int secondChoice) {
        super(client);
        messageToSend = new LeaderChoiceMessage(firstChoice, secondChoice);
    }

    /**
     * Main method of the class used to send the message to the server after the user has chosen which leader cards he wants
     * to discard using the JavaFX GUI.
     */
    @Override
    public void manageUserInteraction() {
        client.sendMessage(messageToSend);
    }
}
