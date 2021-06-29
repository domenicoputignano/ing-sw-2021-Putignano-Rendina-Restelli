package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractActivateProduction;
import it.polimi.ingsw.network.Client;

/**
 * This class represents the GUI-specific state that is reached when the player in turn
 * wants to activate productions during his turn.
 */
public class ActivateProductionGUI extends AbstractActivateProduction {

    /**
     * Main method of the class used to send the message to the server after the user has chosen how to perform this
     * action using the JavaFX GUI.
     */
    @Override
    public void manageUserInteraction() {
        client.sendMessage(messageToSend);
    }

    /**
     * Initializes references to client.
     */
    public ActivateProductionGUI(Client client) {
        super(client);
    }
}
