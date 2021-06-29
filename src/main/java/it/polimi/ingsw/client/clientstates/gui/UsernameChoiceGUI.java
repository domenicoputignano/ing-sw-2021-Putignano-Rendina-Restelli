package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractUserNameChoice;
import it.polimi.ingsw.network.Client;

/**
 * This class represents the GUI-specific username choice state which is reached by the client
 * at the launch of the game.
 */
public class UsernameChoiceGUI extends AbstractUserNameChoice {

    /**
     * Main method of the class used to send the message to the server after the user has chosen the username
     * using the JavaFX GUI.
     */
    @Override
    public void manageUserInteraction() {
        client.sendMessage(messageToSend);
    }

    /**
     * Initializes reference to client and the message to send.
     */
    public UsernameChoiceGUI(Client client,String username) {
        super(client);
        messageToSend.setNickname(username);
    }
}
