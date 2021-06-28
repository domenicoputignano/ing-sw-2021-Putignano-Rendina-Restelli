package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.LeaderChoiceMessage;

/**
 * This class represents the generic ui state that is reached when the client has to
 * choose which leader cards to discard during the initial choices phase of the game.
 */
public abstract class AbstractInitialLeaderChoice extends AbstractClientState {

    /**
     * The index of the first leader card to discard.
     */
    protected int leaderCard1Index;
    /**
     * The index of the second leader card to discard.
     */
    protected int leaderCard2Index;
    /**
     * The message to send to the server containing the indexes of the leader cards to discard.
     */
    protected LeaderChoiceMessage messageToSend;

    public AbstractInitialLeaderChoice(Client client) {
        super(client);
    }

}
