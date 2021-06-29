package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractMoveResources;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.MoveResourcesMessage;

/**
 * This class represents the GUI-specific state that is reached when the client wants to move resources in his depots.
 */
public class MoveResourcesGUI extends AbstractMoveResources {

    /**
     * Initializes reference to client.
     */
    public MoveResourcesGUI(Client client) {
        super(client);
    }

    /**
     * Main method of the class used to send the message to the server after the user has chosen how to perform this
     * action using the JavaFX GUI.
     */
    @Override
    public void manageUserInteraction() {
        this.client.sendMessage(messageToSend);
    }

    /**
     * Sets the message to send to the server.
     * @param message the message to send.
     */
    public void setMessage(MoveResourcesMessage message)
    {
        this.messageToSend = message;
    }
}
