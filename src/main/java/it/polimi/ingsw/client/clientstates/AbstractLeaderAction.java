package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.LeaderActionMessage;

/**
 * This class represents the generic ui state that is reached when the client wants to activate or
 * to discard a leader card during his turn.
 */
public abstract class AbstractLeaderAction extends AbstractClientState {

    /**
     * The message to send to the server containing the index of the leader card to discard or to activate.
     */
    protected LeaderActionMessage messageToSend = new LeaderActionMessage();

    protected AbstractLeaderAction(Client client) {
        super(client);
    }

    public LeaderActionMessage getMessageToSend() {
        return messageToSend;
    }

}
