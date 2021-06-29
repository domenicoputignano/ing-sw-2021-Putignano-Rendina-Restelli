package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractNumOfPlayersChoice;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.NumOfPlayerChoiceMessage;

/**
 * This class represents the GUI-specific number of players choice state which is reached by the client
 * after the game mode choice in {@link it.polimi.ingsw.model.MultiPlayerMode}.
 */
public class NumOfPlayerChoiceGUI extends AbstractNumOfPlayersChoice {

    /**
     * Main method of the class used to send the message to the server after the user has chosen the number of
     * players using the JavaFX GUI.
     */
    @Override
    public void manageUserInteraction() {
        client.sendMessage(messageToSend);
    }

    /**
     * Initializes references to client and the message to send.
     */
    public NumOfPlayerChoiceGUI(Client client, int numOfPlayers) {
        super(client);
        messageToSend = new NumOfPlayerChoiceMessage(numOfPlayers);
    }
}
