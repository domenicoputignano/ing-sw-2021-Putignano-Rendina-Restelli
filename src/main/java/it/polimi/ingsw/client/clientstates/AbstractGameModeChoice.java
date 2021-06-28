package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.messages.clientMessages.GameModeChoiceMessage;

/**
 * This class represents the generic game mode choice state which is reached by the client
 * after the username choice.
 */
public abstract class AbstractGameModeChoice extends AbstractClientState {

    /**
     * The message to send to the server containing the game mode chosen.
     */
    protected GameModeChoiceMessage messageToSend;

    public AbstractGameModeChoice(Client client) {
        super(client);
    }

}
