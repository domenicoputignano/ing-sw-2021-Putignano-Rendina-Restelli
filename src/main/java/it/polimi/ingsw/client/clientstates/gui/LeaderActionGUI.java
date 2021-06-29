package it.polimi.ingsw.client.clientstates.gui;

import it.polimi.ingsw.client.clientstates.AbstractLeaderAction;
import it.polimi.ingsw.network.Client;

/**
 * This class represents the GUI-specific state that is reached when the client wants to activate or
 * to discard a leader card during his turn.
 */
public class LeaderActionGUI extends AbstractLeaderAction {

    /**
     * Initializes references to client.
     */
    public LeaderActionGUI(Client client) {
        super(client);
    }

    /**
     * Sets whether the user wants to discard the leader card in the message to send.
     * @param toDiscard whether the user wants to discard the leader card.
     */
    public void setLeaderAction(boolean toDiscard) {
        messageToSend.setToDiscard(toDiscard);
    }

    /**
     * Sets the index of the leader card to activate/discard in the message to send.
     * @param leaderIndex the index of the leader card to activate/discard.
     */
    public void setLeaderIndex(int leaderIndex) {
        messageToSend.setIndex(leaderIndex);
    }

    /**
     * Main method of the class used to send the message to the server after the user has chosen how to perform this
     * action using the JavaFX GUI.
     */
    @Override
    public void manageUserInteraction() {
        client.sendMessage(messageToSend);
    }
}
